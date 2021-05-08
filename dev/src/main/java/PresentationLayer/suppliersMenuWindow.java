package PresentationLayer;

import BusinessLayer.SuppliersModule.DayOfWeek;
import BusinessLayer.Facade.ISuppliersFacade;
import BusinessLayer.Facade.Response;
import BusinessLayer.SuppliersModule.PaymentAgreement;
import DTO.ContractDTO;
import DTO.SupplierDTO;

import java.util.*;

public class suppliersMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    private ISuppliersFacade facade;

    protected void createMenu() {
        menu=new HashMap();
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
        menu.put(11,"Add a discount to a specific product supplied by a supplier");
        menu.put(12,"Delete a discount from a specific product by a supplier");
        menu.put(13,"View supplier's catalogue");
        menu.put(14,"Go back to the main menu");
    }

    public suppliersMenuWindow(ISuppliersFacade facade, String description) {
        super(description);
        this.facade=facade;
    }
    public void start() {
        printDescription();
        while (!shouldTerminate) {
            switch (printMenu()) {
                case 1:
                    addSupplier();
                    break;
                case 2:
                    deleteSupplier();
                    break;
                case 3:
                    getSupplier();
                    break;
                case 4:
                    getSuppliers();
                    break;
                case 5:
                    updateSupplierShipping();
                    break;
                case 6:
                    updateSupplierShippingDays();
                    break;
                case 7:
                    addDiscount();
                    break;
                case 8:
                    deleteDiscount();
                    break;
                case 9:
                    addItem();
                    break;
                case 10:
                    deleteItem();
                    break;
                case 11:
                    addDiscountProduct();
                    break;
                case 12:
                    deleteDiscountProduct();
                    break;
                case 13:
                    viewAllItemsBySupplier();
                    break;
                case 14:
                    terminate();
                    break;
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
        Response<Boolean> response=facade.deleteProductDiscount(utills.getNonNegativeNumber("\nEnter supplier's ID"),
                utills.getNonNegativeNumber("\nEnter the catalogue ID of the product as it appears in the supplier's catalogue"),
                utills.getNonNegativeNumber("\nAdd the quantity of the product you'd like to delete discount"));
        utills.printMessageOrSuccess(response,"Successfully deleted a discount from the supplier's product");
    }

    private void addDiscountProduct() {
        Response<Boolean> response = facade.addDiscountProduct(utills.getNonNegativeNumber("\nEnter supplier's id"),
                utills.getNonNegativeNumber("\nEnter the catalogue id of the product you'd like to add discount to"),
                utills.getNonNegativeNumber("\nEnter the quantity exceeding it will give discount"),
                utills.getNonNegativeNumber("\nEnter the percentage of the discount"));
        utills.printMessageOrSuccess(response, "\nSuccessfully added a discount to the product!");
    }

    private void deleteItem() {
        Response<Boolean> response=facade.deleteItemFromSupplier(utills.getNonNegativeNumber("\nEnter supplier's ID:"),
                utills.getNonNegativeNumber("\nEnter the product's ID in the supplier's catalogue"));
        utills.printMessageOrSuccess(response,"Successfully deleted an item from the supplier's contract");
    }

    private void addItem() {
        Response<Boolean> response=facade.addItemToSupplier(utills.getNonNegativeNumber("\nEnter supplier's ID:"),
                utills.getNonNegativeNumber("\nEnter product's store ID:"),
                utills.getNonNegativeNumber("\nEnter product's id in supplier's catalouge"),getPrice(),getQuantityAgreementForProduct());
        utills.printMessageOrSuccess(response,"Successfully added an item to the supplier's contract");
    }

    private void deleteDiscount() {
        Response<Boolean> response=facade.deleteSupplierDiscount(utills.getNonNegativeNumber("\nEnter supplier's ID:"),getPrice());
        utills.printMessageOrSuccess(response,"Successfully deleted a discount");
    }

    private void addDiscount() {
        Response<Boolean> response=facade.addDiscount(utills.getNonNegativeNumber("\nEnter supplier's ID:"),getPrice()
                ,utills.getNonNegativeNumber("Please enter the discount percentage"));
        utills.printMessageOrSuccess(response,"Successfully added a discount");
    }

    private Response<SupplierDTO> getSupplierOrFailureMessage(){
        Response<SupplierDTO> response=facade.getSupplier(utills.getNonNegativeNumber("\nEnter supplier's ID:"));
        if(response.WasException())
            System.out.println(response.getMessage());
        return response;
    }

    private void getSupplier() {
        Response<SupplierDTO> response = getSupplierOrFailureMessage();
        if (!response.WasException())
            utills.printMessageOrSuccess(response, response.getValue().toString());
    }

    private void updateSupplierShippingDays() {
        Response<SupplierDTO> response=getSupplierOrFailureMessage();
        if(!response.WasException()) {
            response.getValue().fixedDays = getFixedDays();
            Response<Boolean> resp = facade.updateSupplier(response.getValue());
            utills.printMessageOrSuccess(resp, "Successfully updated the supplier's shipping days");
        }
    }

    private void updateSupplierShipping() {
        Response<SupplierDTO> response=getSupplierOrFailureMessage();
        if(!response.WasException()) {
            response.getValue().selfPickUp = getSupplierPickUp();
            Response<Boolean> resp = facade.updateSupplier(response.getValue());
            utills.printMessageOrSuccess(resp, "Successfully updated the supplier's pick up policy");
        }
    }

    private void getSuppliers() {
        Response<List<SupplierDTO>> response=facade.getAllSuppliers();
        utills.printErrorMessageOrListOfValues(response);
    }


    private void deleteSupplier() {
        int supplierID=utills.getNonNegativeNumber("\nEnter supplier's ID:");
        Response<Boolean> response=facade.deleteSupplier(supplierID);
        utills.printMessageOrSuccess(response,"Supplier was deleted successfully!");
    }

    private void viewAllItemsBySupplier() {
        Response<List<ContractDTO>>response=facade.getItemsBySupplier(utills.getNonNegativeNumber("\nEnter supplier's ID"));
        utills.printErrorMessageOrListOfValues(response);
    }

    private void addSupplier() {
        int paymentMethod=-1;
        boolean selfPickup=false;
        String supplierName,bankAccount;
        Set<DayOfWeek> supplyingDays=null;
        Set<String> categories=new HashSet<>(),manufactures=new HashSet<>();
        HashMap<String,String> contactInfo=new HashMap<>();
        HashMap<Double,Integer>discount=new HashMap<>();


        System.out.println("\nEnter the name of the supplier:");
        supplierName=scanner.nextLine();

        supplyingDays=getFixedDays();

        selfPickup=getSupplierPickUp();

        System.out.println("\nEnter the bank account of the supplier");
        bankAccount=scanner.nextLine();

        System.out.println("\nPlease enter the payment policy: 1.Monthly    2.Per Order");
        for(paymentMethod= utills.checkPositiveNumber(scanner.nextLine()); paymentMethod<1 || paymentMethod>2; paymentMethod= utills.checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input!    1.Monthly    2.Per Order");

        System.out.println("\nPlease enter the categories of products this supplier supplies, when finished enter 'Q'");
        for(String category=scanner.nextLine();!category.equals("Q") && !category.equals("q");category=scanner.nextLine())
            categories.add(category);

        System.out.println("\nPlease enter the manufactures of products this supplier supplies, when finished enter 'Q'");
        for(String manufacture=scanner.nextLine();!manufacture.equals("Q") && !manufacture.equals("q");manufacture=scanner.nextLine())
            manufactures.add(manufacture);

        System.out.println("\nPlease enter the contact info, first the full name, afterwards the phone number");
        contactInfo.put(scanner.nextLine(),scanner.nextLine());

        System.out.println("\nPress Enter to start inserting discounts this supplier provides, or press 'Q' if none");

        while (!scanner.nextLine().equals("Q")) {

            discount.put(getPrice(), utills.getNonNegativeNumber("Please enter the discount percentage above this price"));
            System.out.println("to continue press Enter or 'Q' if done");
        }

        Response<Integer> response=facade.addSupplier(supplierName,supplyingDays,selfPickup,bankAccount, PaymentAgreement.valueOf(paymentMethod),categories,manufactures,contactInfo,discount);
        utills.printMessageOrSuccess(response,"Supplier of id " + response.getValue() + " was added successfully!");
    }

    private double getPrice() {
        double price=-1;
        System.out.println("Please enter the price");
        for(price= utills.checkDoubleNumber(scanner.nextLine()); price==-1; price= utills.checkDoubleNumber(scanner.nextLine()))
            System.out.println("Wrong input, enter price again");
        return price;
    }

    private Set<DayOfWeek> getFixedDays() {
        Set<DayOfWeek> supplyingDays=null;
        System.out.println("\nPlease enter the supplying days, each in a separated line, when finished enter a non-number input");
        printDays();
        for(int day = utills.checkIfInBounds(scanner.nextLine(),7); day!=-1; day= utills.checkIfInBounds(scanner.nextLine(),7)) {
            if (supplyingDays == null)
                supplyingDays = new HashSet<>();
            DayOfWeek dayOfWeek=DayOfWeek.valueOf(day);
            if (supplyingDays.contains(dayOfWeek))
                System.out.println("You have already entered this day!");
            else
                supplyingDays.add(dayOfWeek);
        }
        return supplyingDays;
    }

    private void printDays() {
        System.out.println("1.Sunday    2.Monday    3.Tuesday    4.Wednesday    5.Thursday    6.Friday");
    }

    private boolean getSupplierPickUp(){
        System.out.println("\nPlease enter the pickup policy: 1.Self pickup    2.Delivery by Supplier");
        int pickup=3;
        for(pickup= utills.checkPositiveNumber(scanner.nextLine()); pickup<1 || pickup>2; pickup= utills.checkPositiveNumber(scanner.nextLine()))
            System.out.println("Wrong input!    1.Self pickup    2.Delivery by Supplier");
        return pickup==1;
    }

    private Map<Integer, Integer> getQuantityAgreementForProduct() {
        HashMap<Integer, Integer> discount = new HashMap<>();
        System.out.println("\nPlease enter discounts this supplier provides for the product, to start press Enter or 'Q' if none");

        while (!scanner.nextLine().equals("Q")) {
            discount.put(utills.getNonNegativeNumber("Please enter the quantity of items above this product gets a discount"),
                    utills.getNonNegativeNumber("Please enter the percentage of discount this item will receive exceeding the quantity"));
            System.out.println("to continue press Enter or 'Q' if done");
        }
        return discount;
    }
}
