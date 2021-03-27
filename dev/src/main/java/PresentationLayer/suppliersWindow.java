package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;
import BusinessLayer.Facade.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class suppliersWindow extends Window {
    private boolean shouldTerminate=false;
    private Map<Integer,String> menu=createMenu();

    private Map<Integer, String> createMenu() {
        HashMap menu=new HashMap();
        menu.put(1,"Add supplier");
        menu.put(2,"Delete supplier");
        menu.put(3,"View supplier's details");
        menu.put(4,"Get all suppliers in the system");
        menu.put(5,"Update a supplier's shipping policy");
        menu.put(6,"Update supplier's supplying days");
        menu.put(7,"Add a quantity agreement to a supplier");
        menu.put(8,"Delete a quantity agreement from a supplier");
        menu.put(9,"Add an item to a supplier's contract");
        menu.put(10,"Delete an item from a supplier's contract");
        menu.put(11,"Add a discount to a specific product supplied by a supplier"); //@TODO: Add this function to the facade.
        menu.put(12,"Delete a discount from a specific product by a supplier");
        menu.put(13,"Go back to the main menu");
        return menu;
    }

    public suppliersWindow(ISuppliersFacade facade,String description) {
        super(facade,description);
    }
    public void start() {
        printDescription();
        while (!shouldTerminate){
            switch (printMenu()){
                case 1: addSupplier(); break;
                case 2: deleteSupplier(); break;
                case 3: getSupplier(); break;
                case 4: getSuppliers(); break;
                case 5: updateSupplierShipping(); break;
                case 6: updateSupplierShippingDays(); break;
                case 7: addDiscount(); break;
                case 8: deleteDiscount(); break;
                case 9: addItem(); break;
                case 10: deleteItem(); break;
                case 11: addDiscountProduct(); break;
                case 12: deleteDiscountProduct(); break;
                case 13: terminate(); break;
            }
        }
        closeWindow();
    }

    private void closeWindow() {
        shouldTerminate=false;
    }

    private void terminate() {
        shouldTerminate=true;
    }

    private void deleteDiscountProduct() {

    }

    private void addDiscountProduct() {

    }

    private void deleteItem() {

    }

    private void addItem() {


    }

    private void deleteDiscount() {
        Response<Boolean> response=facade.deleteSupplierDiscount(getSupplierID(),getPrice());
        printMessageOrSuccess(response,"Successfully deleted a discount");

    }

    private void addDiscount() {
        Response<Boolean> response=facade.addDiscount(getSupplierID(),getPrice(),getPercentage());
        printMessageOrSuccess(response,"Successfully added a discount");
    }

    private void updateSupplierShippingDays() {
        Response<Boolean> response=facade.updateSuppliersFixedDays(getSupplierID(),getFixedDays());
        printMessageOrSuccess(response,"Successfully updated the supplier's shipping days");
    }

    private void updateSupplierShipping() {
        Response<Boolean> response=facade.updateSuppliersShippingStatus(getSupplierID(), getSupplierPickUp());
        printMessageOrSuccess(response,"Successfully updated supplier's shipping policy");
    }

    private void getSuppliers() {
        Response<List<String>> response=facade.getAllSuppliers();
        if(response.WasException())
            System.out.println(response.getMessage());
        else {
            for (String supplier : response.getValue())
                System.out.println(supplier);
        }
    }

    private void getSupplier() {
        int supplierID=getSupplierID();
        Response<String> response=facade.getSupplier(supplierID);
        printMessageOrSuccess(response,response.getValue());
    }

    private void deleteSupplier() {
        int supplierID=getSupplierID();
        Response<Boolean> response=facade.deleteSupplier(supplierID);
        printMessageOrSuccess(response,"Supplier was deleted successfully!");
    }

    private void addSupplier() {
        //@TODO: maybe better not to give the id as a parameter, the system better decide the id
        int supplierID,paymentMethod=-1;
        boolean selfPickup=false;
        String supplierName,bankAccount;
        List<Integer> supplyingDays=null;
        ArrayList<String> categories=new ArrayList<>(),manufactures=new ArrayList<>();
        HashMap<String,String> contactInfo=new HashMap<>();
        HashMap<Double,Integer>discount=new HashMap<>();

        supplierID=getSupplierID();

        System.out.println("\nEnter the name of the supplier:");
        supplierName=scanner.nextLine();

        supplyingDays=getFixedDays();

        selfPickup=getSupplierPickUp();

        System.out.println("\nEnter the bank account of the supplier");
        bankAccount=scanner.nextLine();

        //@TODO: change the documentation of the facade regarding the binding of the number of the payment type
        System.out.println("\nPlease enter the payment policy: 1.Monthly    2.Per Order");
        for(paymentMethod=inputChecker.checkPositiveNumber(scanner.nextLine());paymentMethod<1 || paymentMethod>2;paymentMethod=inputChecker.checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input!    1.Monthly    2.Per Order");

        System.out.println("\nPlease enter the categories of products this supplier supplies, when finished enter 'Q'");
        for(String category=scanner.nextLine();!category.equals("Q") && !category.equals("q");category=scanner.nextLine())
            categories.add(category);

        System.out.println("\nPlease enter the manufactures of products this supplier supplies, when finished enter 'Q'");
        for(String manufacture=scanner.nextLine();!manufacture.equals("Q") && !manufacture.equals("q");manufacture=scanner.nextLine())
            manufactures.add(manufacture);

        System.out.println("\nPlease enter the contact info, first the full name, afterwards the phone number");
        contactInfo.put(scanner.nextLine(),scanner.nextLine());

        System.out.println("\nPlease enter discounts this supplier provides, to start press Enter or 'Q' if none");

        while (!scanner.nextLine().equals("Q")) {

            System.out.println("Please enter the discount for orders above this price");
            double price = getPrice();
            int percentage = getPercentage();
            discount.put(price, percentage);
            System.out.println("to continue press Enter or 'Q' if done");
        }

        Response<Boolean> response=facade.addSupplier(supplierID,supplierName,supplyingDays,selfPickup,bankAccount,paymentMethod,categories,manufactures,contactInfo,discount);
        printMessageOrSuccess(response,"Supplier was added successfully!");
    }

    private int getPercentage() {
        int percentage=-1;
        System.out.println("Please enter the discount percentage");
        for(percentage=inputChecker.checkPositiveNumber(scanner.nextLine());percentage==-1;percentage=inputChecker.checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input, enter percentage again");
        return percentage;
    }

    private double getPrice() {
        double price=-1;
        System.out.println("Please enter the price");
        for(price=inputChecker.checkDoubleNumber(scanner.nextLine());price==-1;price=inputChecker.checkDoubleNumber(scanner.nextLine()))
            System.out.println("Wrong input, enter price again");
        return price;
    }

    private List<Integer> getFixedDays() {
        ArrayList<Integer> supplyingDays=null;
        System.out.println("\nPlease enter the supplying days, each in a separated line, when finished enter a non-number input");
        printDays();
        for(int day=inputChecker.checkIfInBounds(scanner.nextLine(),7);day!=-1;day=inputChecker.checkIfInBounds(scanner.nextLine(),7)) {
            if (supplyingDays == null)
                supplyingDays = new ArrayList<>();
            if (supplyingDays.contains(day))
                System.out.println("You have already entered this day!");
            else
                supplyingDays.add(day);
        }
        return supplyingDays;
    }

    private void printDays() {
        //@TODO:Change facade interface documentation - the number of the days have changed.
        System.out.println("1.Sunday    2.Monday    3.Tuesday    4.Wednesday    5.Thursday    6.Friday");
    }

    private int getSupplierID(){
        int supplierID=-1;
        System.out.println("\nEnter the id of the supplier:");
        for(supplierID=inputChecker.checkPositiveNumber(scanner.nextLine());supplierID==-1;supplierID=inputChecker.checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input, please enter a non negative number");
        return supplierID;
    }

    private boolean getSupplierPickUp(){
        System.out.println("\nPlease enter the pickup policy: 1.Self pickup    2.Delivery by Supplier");
        int pickup=3;
        for(pickup=inputChecker.checkPositiveNumber(scanner.nextLine());pickup<1 || pickup>2;pickup=inputChecker.checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input!    1.Self pickup    2.Delivery by Supplier");
        return pickup==1;
    }

    private void printMessageOrSuccess(Response<? extends Object> response, String successMessage) {
        if(response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(successMessage);
    }

    private int printMenu() {
        int input = -2;
        System.out.println("Please choose an option");
        while (input < 0) {
            if (input == -1)
                System.out.println("Wrong input. Please choose one of the following numbers:");
            for (int i = 1; menu.get(i) != null; i++)
                System.out.println(i + ". " + menu.get(i));
            System.out.println("\nEnter your choice:");
            String choice = scanner.nextLine();
            input = inputChecker.checkIfInBounds(choice, menu.size() + 1);
        }
        return input;
    }
}
