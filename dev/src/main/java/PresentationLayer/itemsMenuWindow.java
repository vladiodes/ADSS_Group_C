package PresentationLayer;

import BusinessLayer.Facade.InventoryFacade;
import BusinessLayer.Facade.Response;
import DTO.*;
import Misc.TypeOfEmployee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class itemsMenuWindow extends menuWindow {
    private InventoryFacade inventoryFacade;
    private boolean shouldTerminate;
    private boolean scenario;
    private TypeOfEmployee employee;


    public itemsMenuWindow(TypeOfEmployee employee) {
        super("Items menu");
        this.employee=employee;
        inventoryFacade = InventoryFacade.getInstance();
        scenario = true;
        shouldTerminate = false;
        createMenu();
    }

    public void start() {
        System.out.println("Welcome To items Menu , We Are currently Removing faulty items...");
        removeFaultyItems(); // Remove all faulty items whenever entering the menu of the items
        while (!shouldTerminate) {
            if(employee.equals(TypeOfEmployee.BranchManager))
                branchManagerFunctionality();
            else if(employee.equals(TypeOfEmployee.Storage))
                storageManagerFunctionality();

            }
        closeWindow();
    }

    private void storageManagerFunctionality() {
        switch (printMenu()) {
            case 1:
                addItem();
                break;
            case 2:
                updateItem();
                break;
            case 3:
                addCategory();
                break;
            case 4:
                updateCategory();
                break;
            case 5:
                findItemByLocation();
                break;
            case 6:
                changeAlertTime();
                break;
            case 7:
                addCategoryDiscount();
                break;
            case 8:
                addItemDiscount();
                break;
            case 9:
                addSale();
                break;
            case 10:
                weeklyReport();
                break;
            case 11:
                faultyItems();
                break;
            case 12:
                expItems();
                break;
            case 13:
                minAmountItems();
                break;
            case 14:
                salesReport();
                break;
            case 15:
                deleteItem();
                break;
            case 16:
                addSpecificItem();
                break;
            case 17:
                terminate();
                break;
        }
    }

    private void branchManagerFunctionality(){
        switch (printMenu()){
            case 1:
                weeklyReport();
                break;
            case 2:
                faultyItems();
                break;
            case 3:
                expItems();
                break;
            case 4:
                minAmountItems();
                break;
            case 5:
                salesReport();
                break;
            case 6:
                terminate();
                break;
        }
    }

    private void removeFaultyItems() {
        Response<Boolean> res = inventoryFacade.removeFaultyItems();
        utills.printMessageOrSuccess(res, "Successfully Removed faulty items \n");
    }

    private void addSpecificItem() {
        int itemID = utills.getNonNegativeNumber("please enter general item id\n");
        System.out.println("please enter producer:\n");
        String producer = scanner.nextLine();
        int storageAmount = utills.getNonNegativeNumber("please enter storage amount:\n");
        int shelfAmount = utills.getNonNegativeNumber("please enter shelf amount:\n");

        LocalDate expDate = utills.getDateFromUser("Please enter expiration date");


        Response<Boolean> response = inventoryFacade.addSpecificItem(itemID, storageAmount, shelfAmount, expDate,producer);
        utills.printMessageOrSuccess(response, "Successfully added item of id " + itemID + "\n");
    }

    private void closeWindow() {
        shouldTerminate = false;
    }

    private void terminate() {
        shouldTerminate = true;
    }

    @Override
    protected void createMenu() {
        menu = new HashMap<>();
        if(employee.equals(TypeOfEmployee.BranchManager)){
            menu.put(1, "Get weekly inventory report");
            menu.put(2, "Show faulty items");
            menu.put(3, "Show exp items");
            menu.put(4, "Show minimum amount items");
            menu.put(5, "Show Sales Report");
            menu.put(6, "Back to main menu");
        }
        else {
            menu.put(1, "Add item");
            menu.put(2, "Update item");
            menu.put(3, "Add category");
            menu.put(4, "Update category");
            menu.put(5, "Find item by location");
            menu.put(6, "Change alert time");
            menu.put(7, "Add category discount");
            menu.put(8, "Add item discount");
            menu.put(9, "Add sale");
            menu.put(10, "Get weekly inventory report");
            menu.put(11, "Show faulty items");
            menu.put(12, "Show exp items");
            menu.put(13, "Show minimum amount items");
            menu.put(14, "Show Sales Report");
            menu.put(15, "Delete item");
            menu.put(16, "Add Specific Item");
            menu.put(17, "Back to main menu");
        }
    }

    public static void printReport(Response<? extends Object> response) {
        if (response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(response.getValue());
    }

    private void addItem() {
        int categoryID = utills.getNonNegativeNumber("please enter category id\n");
        System.out.println("please enter item name: ");
        String name = scanner.nextLine();
        int location = utills.getNonNegativeNumber("please enter item location:\n");
        System.out.println("please enter producer:\n");
        String producer = scanner.nextLine();
        int storageAmount = utills.getNonNegativeNumber("please enter storage amount:\n");
        int shelfAmount = utills.getNonNegativeNumber("please enter shelf amount:\n");
        int minAmount = utills.getNonNegativeNumber("please enter minimum amount:\n");
        LocalDate expDate = utills.getDateFromUser("Please enter expiration date");
        System.out.println("please enter selling price : ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine();


        Response<ItemDTO> response = inventoryFacade.addItem(categoryID, location, name, producer, storageAmount, shelfAmount, minAmount, expDate, sellingPrice);
        utills.printMessageOrSuccess(response, "Successfully added item\n" + response.getValue());
    }

    private void updateItem() {
        System.out.println("please enter item name : ");
        String name = scanner.nextLine();
        int itemID = utills.getNonNegativeNumber("Please enter id of an item\n");
        int location = utills.getNonNegativeNumber("please enter item location\n");

        System.out.println("please enter producer:\n");
        String producer = scanner.nextLine();

        int minAmount = utills.getNonNegativeNumber("please enter minimum amount\n");

        System.out.println("please enter selling price : ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine();
        Response<Boolean> response = inventoryFacade.updateItem(itemID, name,  minAmount, sellingPrice,location,producer);
        utills.printMessageOrSuccess(response, "Successfully update item \n");
    }

    private void addCategory() {
        System.out.println("please enter category name : ");
        String name = scanner.nextLine();
        int fatherID = utills.getNonNegativeNumber("please enter father-category ID or 0 if none\n");
        Response<CategoryDTO> response = inventoryFacade.addCategory(name, fatherID);
        utills.printMessageOrSuccess(response, "Successfully added category\n" + response.getValue());
    }

    private void updateCategory() {
        System.out.println("please enter category name : ");
        String name = scanner.nextLine();
        int categoryID = utills.getNonNegativeNumber("please enter category ID\n");

        Response<Boolean> response = inventoryFacade.updateCategory(categoryID, name);
        utills.printMessageOrSuccess(response, "Successfully update category\n");
    }

    private void findItemByLocation() {
        Response<ItemDTO> response = inventoryFacade.getItemByLocation(utills.getNonNegativeNumber("please enter item location\n"));
        utills.printMessageOrSuccess(response, "Successfully finding item:\n" + response.getValue());
    }

    private void changeAlertTime() {

        Response<Boolean> response = inventoryFacade.changeAlertTime(utills.getNonNegativeNumber("please enter item id\n")
                , utills.getNonNegativeNumber("please enter new days amount of alert\n"));
        utills.printMessageOrSuccess(response, "Successfully changing item alert time\n");

    }

    private void addCategoryDiscount() {
        int categoryID = utills.getNonNegativeNumber("please enter category id\n");
        System.out.println("please enter discount % : ");
        double discount = scanner.nextDouble();
        scanner.nextLine();
        Response<Boolean> response = inventoryFacade.addCategoryDiscount(categoryID, discount);
        utills.printMessageOrSuccess(response, "Successfully added discount\n");

    }

    private void addItemDiscount() {
        int itemID = utills.getNonNegativeNumber("please enter item id\n");

        System.out.println("please enter discount % : ");
        double discount = scanner.nextDouble();
        scanner.nextLine();
        Response<Boolean> response = inventoryFacade.addItemDiscount(itemID, discount);
        utills.printMessageOrSuccess(response, "Successfully added discount\n");
    }

    private void addSale() {
        Response<SaleDTO> response = inventoryFacade.addSale(utills.getNonNegativeNumber("please enter item id\n")
                , utills.getNonNegativeNumber("please enter quantity\n"));
        utills.printMessageOrSuccess(response, "Successfully added sale\n");
    }

    private void weeklyReport() {
        ArrayList<Integer> categoriesList = new ArrayList<>();
        System.out.println("please enter categories: (enter -1 to stop)");
        while (true) {
            int nextChoice = scanner.nextInt();
            scanner.nextLine();
            if (nextChoice != -1) {
                categoriesList.add(nextChoice);
            } else
                break;
        }
        Response<amountReportDTO> response = inventoryFacade.getWeeklyReport(categoriesList);
        printReport(response);
    }

    private void faultyItems() {
        Response<defactReportDTO> response = inventoryFacade.showFaultyItems();
        printReport(response);
    }

    private void expItems() {
        Response<defactReportDTO> response = inventoryFacade.showExpItems();
        printReport(response);
    }

    private void minAmountItems() {
        Response<amountReportDTO> response = inventoryFacade.showMinAmountItems();
        printReport(response);
    }

    private void salesReport() {

        ArrayList<Integer> categoriesList = new ArrayList<>();
        System.out.println("please enter categories: (enter -1 to stop)");
        while (true) {
            int nextChoice = scanner.nextInt();
            scanner.nextLine();
            if (nextChoice != -1) {
                categoriesList.add(nextChoice);
            } else
                break;
        }

        LocalDate startDate = utills.getDateFromUser("Please enter start date");

        LocalDate endDate = utills.getDateFromUser("Please enter end date");

        Response<SaleReportDTO> response = inventoryFacade.showSalesReport(startDate, endDate, categoriesList);
        printReport(response);
    }

    private void deleteItem() {
        int itemID = utills.getNonNegativeNumber("Enter item id\n");
        Response<Boolean> response = inventoryFacade.deleteItem(itemID);
        utills.printMessageOrSuccess(response, "Successfully deleted item\n");
    }

}