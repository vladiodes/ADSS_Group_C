package Presentation;

import Business.Controllers.TransportsEmployeesFacade;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import Business.Objects.ItemContract;

import java.text.SimpleDateFormat;
import java.util.*;

public class TransportsMain {
    public static int ICID = 0;
    private static Scanner in;
    private static Boolean DataInitialized = false;

    public static void start(TransportsEmployeesFacade facade) {
        TransportsEmployeesFacade API = facade;
        in = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Hello, write the number of the operation you would like to do");
                System.out.println("1. Add site");
                System.out.println("2. Add truck");
                System.out.println("3. Add transport");
                System.out.println("4. Add section");
                System.out.println("5. Get all trucks");
                System.out.println("6. Get all sites");
                System.out.println("7. Get all sections");
                System.out.println("8. Get all transports");
                System.out.println("9. Get transport of driver");
                System.out.println("10. Get transport by date");
                System.out.println("11. Quit");
                if (!DataInitialized)
                    System.out.println("12. Initialize data from instructions manual");
                Scanner in = new Scanner(System.in);
                int option = in.nextInt();
                in.nextLine();
                if (option == 11)
                    break;
                switch (option) {
                    case 1:
                        AddSite(API);
                        break;
                    case 2:
                        AddTruck(API);
                        break;
                    case 3:
                        AddTransport(API);
                        break;
                    case 4:
                        AddSection(API);
                        break;
                    case 5:
                        GetAllTrucks(API);
                        break;
                    case 6:
                        GetAllSites(API);
                        break;
                    case 7:
                        GetAllSections(API);
                        break;
                    case 8:
                        GetAllTransports(API);
                        break;
                    case 9:
                        GetTransportsByDriver(API);
                        break;
                    case 10:
                        GetTransportsByDate(API);
                        break;
                    case 12:
                        InitializeData(API);
                        break;
                    default:
                        System.out.println("Please enter a valid number");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    public static ArrayList<ItemContract> makeItemContract(TransportsEmployeesFacade API) throws Exception {
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

    public static void AddSite(TransportsEmployeesFacade API) {
        try {
            System.out.println("Please enter the address of the site");
            String ad = in.nextLine();
            System.out.println("Please enter the contact phone number of the site (only numbers no -)");
            String num = in.nextLine();
            System.out.println("Please enter the contact name of the site");
            String c = in.nextLine();
            System.out.println("Please enter the section the site is in");
            String sec = in.nextLine();
            API.addSite(ad, num, c, API.getSection(sec));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void AddTruck(TransportsEmployeesFacade API) {
        try {
            System.out.println("Please enter the plate number of the truck");
            String plate = in.nextLine();
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

    public static void AddTransport(TransportsEmployeesFacade API) {
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
            TypeOfShift typeOfShift = null;
            while (typeOfShift == null) {
                System.out.println("Please Enter a valid Type Of Shift The transporation is at:");
                String type = in.nextLine();
                typeOfShift = parseTypeOfShift(type);
            }
            System.out.println("Please enter the weight of the transport");
            int weight = in.nextInt();
            in.nextLine();
            System.out.println("Please enter the id of the driver");
            String driverID = in.nextLine();
            System.out.println("Please enter the plate number of the truck");
            String plateNum = in.nextLine();
            System.out.println("Please enter where the transport is going from");
            String source = in.nextLine();

            System.out.println("Please follow the instructions to add item contracts to the current transportation:");
            ArrayList<ItemContract> contracts = makeItemContract(API);
            while (true) {
                try {
                    API.addTransport(transDate, weight, driverID, plateNum, contracts, source, typeOfShift);
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
                        contracts.addAll(makeItemContract(API));
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

    public static void AddSection(TransportsEmployeesFacade API) {
        try {
            System.out.println("Please enter the name of the new section");
            String section = in.nextLine();
            API.addSection(section);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllTrucks(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllTrucks());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllSites(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllSites());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllSections(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllSections());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetAllTransports(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllTransports());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetTransportsByDriver(TransportsEmployeesFacade API) {
        try {
            System.out.println("Please enter the id of the driver");
            String id = in.nextLine();
            System.out.println(API.getTransportsOfDriver(id));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void GetTransportsByDate(TransportsEmployeesFacade API) {
        try {
            System.out.println("Please type the Date in the following format - dd/MM/yyyy");
            String sDate1 = in.nextLine();
            System.out.println(API.getTransportsByDate(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void InitializeData(TransportsEmployeesFacade API) {
        if (DataInitialized) {
            return;
        }
        try {
            API.addSection("North");
            API.addSection("Center");
            API.addSection("South");
            API.addSite("Nahariyya", "052123123", "Ilay", "North");
            API.addSite("Tel-Aviv", "052555555", "Hadar", "Center");
            API.addTruck("3212345", "Honda Ridgeline", 5000, "Pickup Truck", 1500);
            API.addTruck("6942021", "Tesla", 10000, "Smart Car", 7500);
            DataInitialized = true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static TypeOfShift parseTypeOfShift(String type) {
        TypeOfShift typeOfShift;
        try {
            typeOfShift = TypeOfShift.valueOf(type);
        } catch (Exception e) {
            typeOfShift = null;
        }
        return typeOfShift;
    }
}
