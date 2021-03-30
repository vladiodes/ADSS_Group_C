package Presentation;

import Business.Controllers.TransportsFacade;
import Business.Objects.ItemContract;

import java.text.SimpleDateFormat;
import java.util.*;

public class TransportsMain {
    private static TransportsFacade API = new TransportsFacade();
    public static int ICID = 0;

    public static void main(String args[]) {
        while (true) {
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
            if (option == 13)
                break;
            switch (option) {
                case 1:
                    try {
                        System.out.println("Please enter the name of the new driver");
                        String name = in.next();
                        System.out.println("Please enter the id of the new driver");
                        int id = in.nextInt();
                        System.out.println("Please enter the license of the new driver");
                        int license = in.nextInt();
                        API.addDriver(name, id, license);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Please enter the address of the site");
                        String ad = in.next();
                        System.out.println("Please enter the contact phone number of the site (only numbers no -)");
                        int num = in.nextInt();
                        System.out.println("Please enter the contact name of the site");
                        String c = in.next();
                        System.out.println("Please enter the section the site is in");
                        String sec = in.next();
                        API.addSite(ad, num, c, API.getSection(sec));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Please enter the plate number of the truck");
                        int plate = in.nextInt();
                        System.out.println("Please enter the model of the truck");
                        String model = in.next();
                        System.out.println("Please enter max weight for the truck (only numbers!):");
                        int maxweight = in.nextInt();
                        System.out.println("Please enter the type of truck");
                        String type = in.next();
                        System.out.println("Please enter the weight of the truck without load:");
                        int factoryweight = in.nextInt();
                        API.addTruck(plate, model, maxweight, type, factoryweight);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    try {
                        System.out.println("Please enter the date of the transport");
                        String date = in.next();
                        Date transDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                        Date today = new Date();
                        while (transDate.before(today)) {
                            System.out.println("Please enter a valid date");
                            date = in.next();
                            transDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                        }
                        System.out.println("Please enter the weight of the transport");
                        int weight = in.nextInt();
                        System.out.println("Please enter the id of the driver");
                        int driverID = in.nextInt();
                        System.out.println("Please enter the plate number of the truck");
                        int plateNum = in.nextInt();
                        System.out.println("Please enter where the transport is going from");
                        String source = in.next();
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
                                    if (in.next().equals("y"))
                                        break;
                                    System.out.println("which item contract would you like to edit?");
                                    for (int i = 0; i < contracts.size(); i++)
                                        System.out.println(i + ". " + contracts.get(i));
                                    int tempoption = in.nextInt();
                                    contracts.get(tempoption).setPassed(false);
                                    contracts.addAll(makeItemContract());
                                    System.out.println("Please enter the new weight of the transportation");
                                    weight = in.nextInt();
                                } else {
                                    System.out.println(e.getMessage());
                                    break;
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        System.out.println("Please enter the name of the new section");
                        String section = in.next();
                        API.addSection(section);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    try {
                        System.out.println(API.getAllTrucks());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 7:
                    try {
                        System.out.println(API.getAllDrivers());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    try {
                        System.out.println(API.getAllSites());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9:
                    try {
                        System.out.println(API.getAllSections());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 10:
                    try {
                        System.out.println(API.getAllTransports());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 11:
                    try {
                        System.out.println("Please enter the id of the driver");
                        int id = in.nextInt();
                        System.out.println(API.getTransportsOfDriver(id));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 12:
                    try {
                        System.out.println("Please type the Date in the following format - dd/MM/yyyy");
                        String sDate1 = in.next();
                        System.out.println(API.getTransportsByDate(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1)));
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Please enter a valid number");
                    break;
            }
        }
    }

    public static ArrayList<ItemContract> makeItemContract() throws Exception {
        ArrayList<ItemContract> contracts = new ArrayList<ItemContract>();
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Please enter the destination address");
            String dest = in.next();
            System.out.println("Please follow the instructions to add items:");
            HashMap<String, Integer> items = new HashMap<String, Integer>();
            while (true) {
                System.out.println("What item do you want?");
                String item = in.next();
                System.out.println("How many " + item + " do you want?");
                int quantity = in.nextInt();
                items.put(item, quantity);
                System.out.println("Do you want to add another item? y/n");
                if (in.next().equals("n"))
                    break;
            }
            contracts.add(new ItemContract(ICID++, API.getSite(dest), items, true));
            System.out.println("Another item contract? y/n");
            if (in.next().equals("n"))
                break;
        }
        return contracts;
    }
}
