package PresentationLayer;

import BusinessLayer.Facade.InventoryFacade;
import BusinessLayer.Facade.Response;
import DTO.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;


public class itemsMenuWindow extends menuWindow {
    private InventoryFacade inventoryFacade;
    private boolean shouldTerminate;
    private boolean scenario;

    public itemsMenuWindow() {
        super("Items menu");
        inventoryFacade = InventoryFacade.getInstance();
        scenario = true;
        shouldTerminate = false;
        createMenu();
    }

    public void start() {
        while (!shouldTerminate) {
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
                    if (scenario) {
                        //scenario1();
                        scenario = false;
                    } else
                        System.out.println("scenario can run only one time");
                    break;
                case 17:
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

    @Override
    protected void createMenu() {
        menu = new HashMap<>();
        menu.put(1, "add item");
        menu.put(2, "update item");
        menu.put(3, "add category");
        menu.put(4, "update category");
        menu.put(5, "find item by location");
        menu.put(6, "change alert time");
        menu.put(7, "add category discount");
        menu.put(8, "add item discount");
        menu.put(9, "add sale");
        menu.put(10, "get weekly inventory report");
        menu.put(11, "show faulty items");
        menu.put(12, "show exp items");
        menu.put(13, "show minimum amount items");
        menu.put(14, "Show Sales Report");
        menu.put(15, "delete item");
        menu.put(16, "run scenario");
        menu.put(17, "back to main menu");
    }

    public static void printReport(Response<? extends Object> response) {
        if (response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(response.getValue().toString());
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


        System.out.println("please enter expiration date: ");
        LocalDate expDate = getDateFromUser();
        System.out.println("please enter buying price: ");
        double buyingPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("please enter selling price : ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine();


        Response<ItemDTO> response = inventoryFacade.addItem(categoryID, location, name, producer, storageAmount, shelfAmount, minAmount, expDate, buyingPrice, sellingPrice);
        utills.printMessageOrSuccess(response, "Successfully added item of id " + response.getValue().getID() + "\n");
    }

    private void updateItem() {
        System.out.println("please enter item name : ");
        String name = scanner.nextLine();
        int itemID = utills.getNonNegativeNumber("Please enter id of an item\n");
        int location = utills.getNonNegativeNumber("please enter item location\n");

        System.out.println("please enter producer:\n");
        String producer = scanner.nextLine();
        int storageAmount = utills.getNonNegativeNumber("please enter storage amount\n");
        int shelfAmount = utills.getNonNegativeNumber("please enter shelf amount\n");

        int minAmount = utills.getNonNegativeNumber("please enter minimum amount\n");
        System.out.println("please enter expiration date:");
        LocalDate expDate = getDateFromUser();

        System.out.println("please enter buying price : ");
        double buyingPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("please enter selling price : ");
        double sellingPrice = scanner.nextDouble();
        scanner.nextLine();
        Response<Boolean> response = inventoryFacade.updateItem(itemID, name, location, producer, storageAmount, shelfAmount, minAmount, expDate, buyingPrice, sellingPrice);
        utills.printMessageOrSuccess(response, "Successfully update item : \n");
    }

    private void addCategory() {
        System.out.println("please enter category name : ");
        String name = scanner.nextLine();
        int fatherID = utills.getNonNegativeNumber("please enter father-category ID\n");
        Response<CategoryDTO> response = inventoryFacade.addCategory(name, fatherID);
        utills.printMessageOrSuccess(response, "Successfully added category : \n");
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
        utills.printMessageOrSuccess(response, "Successfully finding item :\n" + response.getValue());
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

        System.out.println("please enter start date: ");
        LocalDate startDate = getDateFromUser();

        System.out.println("please enter end date: ");
        LocalDate endDate = getDateFromUser();

        Response<SaleReportDTO> response = inventoryFacade.showSalesReport(startDate, endDate, categoriesList);
        printReport(response);
    }

    private void deleteItem() {
        int itemID = utills.getNonNegativeNumber("Enter item id\n");
        Response<Boolean> response = inventoryFacade.deleteItem(itemID);
        utills.printMessageOrSuccess(response, "Successfully deleted item\n");
    }

    private LocalDate getDateFromUser() {
        System.out.println("please enter day :");
        int day = scanner.nextInt();
        scanner.nextLine();
        while (day > 30 || day < 1) {
            System.out.println("please enter valid day :");
            day = scanner.nextInt();
            scanner.nextLine();

        }
        System.out.println("please enter month :");
        int month = scanner.nextInt();
        scanner.nextLine();
        while (month > 12 || month < 1) {
            System.out.println("please enter valid day :");
            day = scanner.nextInt();
            scanner.nextLine();

        }
        System.out.println("please enter year :");
        int year = scanner.nextInt();
        scanner.nextLine();
        while (year < 2021 || year > 2023) {
            System.out.println("please enter valid day :");
            day = scanner.nextInt();
            scanner.nextLine();

        }
        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }

//    public void scenario1() {
//        Response<CategoryDTO> response1 = inventoryFacade.addCategory("sweets", 0);
//        utills.printMessageOrSuccess(response1, "Successfully added category\n");
//
//        Response<ItemDTO> response2 = inventoryFacade.addItem(response1.getValue().getID(), 2, "chocolate", "nutela", 15, 20, 5, LocalDate.of(2021, 06, 15), 14.90, 16.90);
//        utills.printMessageOrSuccess(response2, "Successfully added item\n");
//
//        Response<SaleDTO> response3 = inventoryFacade.addSale(response2.getValue().getID(), 1);
//        utills.printMessageOrSuccess(response3, "successfully sale\n");
//
//        ArrayList categoriesList = new ArrayList();
//        categoriesList.add(response1.getValue().getID());
//        Response<ReportDTO> response4 = inventoryFacade.getWeeklyReport(categoriesList);
//        printReport(response4);
//    }
}
