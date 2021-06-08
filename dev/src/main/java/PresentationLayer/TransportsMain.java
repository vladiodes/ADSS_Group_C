package PresentationLayer;

import BusinessLayer.Facade.TransportsEmployeesFacade;
import Misc.Pair;
import Misc.TypeOfShift;
import Misc.utills;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class TransportsMain extends menuWindow {
    public int ICID = 0;
    private Scanner in;
    private TransportsEmployeesFacade API;
    private boolean shouldTerminate = false;

    public TransportsMain(TransportsEmployeesFacade API) {
        super("Logistics Manager");
        this.API = API;
        in = utills.scanner;
        createMenu();
    }

    @Override
    protected void createMenu() {
        menu = new HashMap<>();
        menu.put(1, "Add site");
        menu.put(2, "Add truck");
        menu.put(3, "Add transport");
        menu.put(4, "Add section");
        menu.put(5, "Get all trucks");
        menu.put(6, "Get all sites");
        menu.put(7, "Get all sections");
        menu.put(8, "Get all transports");
        menu.put(9, "Go back");
    }

    @Override
    public void start() {
        printDescription();
        while (!shouldTerminate) {
            switch (printMenu()) {
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
                    terminate();
                    break;
            }
        }
        closeWindow();
    }

    private void closeWindow() {
        shouldTerminate = false;
    }

    private void terminate() {
        shouldTerminate = true;
    }

    public void AddSite(TransportsEmployeesFacade API) {
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
            System.out.println("Site added successfuly.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void AddTruck(TransportsEmployeesFacade API) {
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
            System.out.println("Truck added");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void AddTransport(TransportsEmployeesFacade API) {
        try {
            LocalDate transDate = utills.getDateFromUser("Please enter the date of the transport:");
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

            System.out.println("Please enter the ID for the order you would like to transport");
            ArrayList<Pair<Integer, Integer>> orderIDs = new ArrayList<>();
            while (true) {
                System.out.println();
                orderIDs.add(new Pair(utills.getNonNegativeNumber("Please enter the supplier ID"),utills.getNonNegativeNumber("Please enter the order ID")));
                System.out.println("Do you want to add another item? y/n");
                if (in.nextLine().equals("n"))
                    break;
            }
            while (true) {
                try {
                    API.addTransport(transDate, weight, driverID, plateNum, orderIDs, source, typeOfShift);
                    System.out.println("The transport was successfuly recorded!");
                    break;
                } catch (Exception e) {
                    if (e.getMessage().equals("Truck weight exceeded.")) {
                        System.out.println("Truck weight exceeded, would you like to discard the transport? y/n");
                        if (in.nextLine().equals("y"))
                            break;
                        System.out.println("which order would you like to erase from the transport?");
                        for (int i = 0; i < orderIDs.size(); i++)
                            System.out.println(i + ". " + orderIDs.get(i));
                        int tempoption = in.nextInt();
                        in.nextLine();
                        orderIDs.remove(tempoption); //////// change the shipment status??
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

    public void AddSection(TransportsEmployeesFacade API) {
        try {
            System.out.println("Please enter the name of the new section");
            String section = in.nextLine();
            API.addSection(section);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GetAllTrucks(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllTrucks());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GetAllSites(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllSites());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GetAllSections(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllSections());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void GetAllTransports(TransportsEmployeesFacade API) {
        try {
            System.out.println(API.getAllTransports());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private TypeOfShift parseTypeOfShift(String type) {
        TypeOfShift typeOfShift;
        try {
            typeOfShift = TypeOfShift.valueOf(type);
        } catch (Exception e) {
            typeOfShift = null;
        }
        return typeOfShift;
    }
}
