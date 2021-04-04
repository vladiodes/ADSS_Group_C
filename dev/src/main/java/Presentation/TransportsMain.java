package Presentation;

import Business.Controllers.TransportsFacade;
import Business.Objects.ItemContract;

import java.text.SimpleDateFormat;
import java.util.*;

public class TransportsMain {
    private static TransportsFacade API = new TransportsFacade();
    public static int ICID = 0;
    private static Scanner in;

    public static void main(String args[]) {
        in = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Hello, write the number of the operation you would like to do?");
                System.out.println("1. Add driver");
                System.out.println("2. Add site");
                System.out.println("3. Add truck");
                System.out.println("4. Add transport");
                System.out.println("5. Add section");
                System.out.println("6. Get all trucks");
                System.out.println("7. Get all drivers");
                System.out.println("8. Get all sites");
                System.out.println("9. Get all sections");
                System.out.println("10. Get all transports");
                System.out.println("11. Get transport of driver");
                System.out.println("12. Get transport by date");
                System.out.println("13. Quit");
                Scanner in = new Scanner(System.in);
                int option = in.nextInt();
                in.nextLine();
                if (option == 13)
                    break;
                switch (option) {
                    case 1:
                        AddDriver();
                        break;
                    case 2:
                        AddSite();
                        break;
                    case 3:
                        AddTruck();
                        break;
                    case 4:
                        AddTransport();
                        break;
                    case 5:
                        AddSection();
                        break;
                    case 6:
                        GetAllTrucks();
                        break;
                    case 7:
                        GetAllDrivers();
                        break;
                    case 8:
                        GetAllSites();
                        break;
                    case 9:
                        GetAllSections();
                        break;
                    case 10:
                        GetAllTransports();
                        break;
                    case 11:
                        GetTransportsByDriver();
                        break;
                    case 12:
                        GetTransportsByDate();
                        break;
                    default:
                        System.out.println("Please enter a valid number");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            }
        }
        in.close();
    }

    public static ArrayList<ItemContract> makeItemContract() throws Exception {
        ArrayList<ItemContract> contracts = new ArrayList<ItemContract>();
        while (true) {
            System.out.println("Please enter the destination address");
            String dest = in.nextLine();
            System.out.println("Please follow the instructions to add items:");
            HashMap<String, Integer> items = new HashMap<String, Integer>();
            while (true) {
                System.out.println("What item do you want?");
                String item = in.nextLine();
                System.out.println("How many " + item + " do you want?");
                int quantity = in.nextInt();
                in.nextLine();
                items.put(item, quantity);
                System.out.println("Do you want to add another item? y/n");
                if (in.nextLine().equals("n"))
                    break;
            }
            contracts.add(new ItemContract(ICID++, API.getSite(dest), items, true));
            System.out.println("Another item contract? y/n");
            if (in.nextLine().equals("n"))
                break;
        }
        return contracts;
    }

    public static void AddDriver() {

        try {
            System.out.println("Please enter the name of the new driver");
            String name = in.nextLine();
            System.out.println("Please enter the id of the new driver");
            int id = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the license of the new driver");
            int license = in.nextInt();
            in.nextLine();
            API.addDriver(name, id, license);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AddSite() {
        try {
            System.out.println("Please enter the address of the site");
            String ad = in.nextLine();
            System.out.println("Please enter the contact phone number of the site (only numbers no -)");
            int num = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the contact name of the site");
            String c = in.nextLine();
            System.out.println("Please enter the section the site is in");
            String sec = in.nextLine();
            API.addSite(ad, num, c, API.getSection(sec));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AddTruck() {
        try {
            System.out.println("Please enter the plate number of the truck");
            int plate = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the model of the truck");
            String model = in.nextLine();
            System.out.println("Please enter max weight for the truck (only numbers!)");
            int maxweight = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the type of truck");
            String type = in.nextLine();
            System.out.println("Please enter the weight of the truck without load");
            int factoryweight = in.nextInt();
            in.nextLine();
            API.addTruck(plate, model, maxweight, type, factoryweight);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AddTransport() {
        try {
            System.out.println("Please enter the date of the transport in the following format: dd/MM/yyyy");
            String date = in.nextLine();
            Date transDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            Date today = new Date();
            while (transDate.before(today)) {
                System.out.println("Please enter a valid date");
                date = in.nextLine();
                transDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            }
            System.out.println("Please enter the weight of the transport");
            int weight = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the id of the driver");
            int driverID = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the plate number of the truck");
            int plateNum = in.nextInt();
            in.nextLine();
            System.out.println("Please enter where the transport is going from");
            String source = in.nextLine();
            System.out.println("Please follow the instructions to add item contracts to the current transportation:");
            ArrayList<ItemContract> contracts = makeItemContract();
            while (true) {
                try {
                    API.addTransport(transDate, weight, driverID, plateNum, contracts, source);
                    System.out.println("The transport was successfuly recorded!");
                    break;
                } catch (Exception e) {
                    if (e.getMessage().equals("Truck weight exceeded.")) {
                        System.out.println("Truck weight exceeded, would you like to discard the transport? y/n");
                        if (in.nextLine().equals("y"))
                            break;
                        System.out.println("which item contract would you like to edit?");
                        for (int i = 0; i < contracts.size(); i++)
                            System.out.println(i + ". " + contracts.get(i));
                        int tempoption = in.nextInt();
                        in.nextLine();
                        contracts.get(tempoption).setPassed(false);
                        contracts.addAll(makeItemContract());
                        System.out.println("Please enter the new weight of the transportation");
                        weight = in.nextInt();
                        in.nextLine();
                    } else {
                        System.out.println(e.getMessage());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AddSection() {
        try {
            System.out.println("Please enter the name of the new section");
            String section = in.nextLine();
            API.addSection(section);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllTrucks() {
        try {
            System.out.println(API.getAllTrucks());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllDrivers() {
        try {
            System.out.println(API.getAllDrivers());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllSites() {
        try {
            System.out.println(API.getAllSites());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllSections() {
        try {
            System.out.println(API.getAllSections());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllTransports() {
        try {
            System.out.println(API.getAllTransports());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetTransportsByDriver() {
        try {
            System.out.println("Please enter the id of the driver");
            int id = in.nextInt();
            in.nextLine();
            System.out.println(API.getTransportsOfDriver(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetTransportsByDate() {
        try {
            System.out.println("Please type the Date in the following format - dd/MM/yyyy");
            String sDate1 = in.nextLine();
            System.out.println(API.getTransportsByDate(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
