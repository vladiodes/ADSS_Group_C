package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;
import BusinessLayer.Facade.Response;
import DTO.ProductDTO;

import java.util.HashMap;
import java.util.List;

public class itemsMenuWindow extends menuWindow {
    boolean shouldTerminate=false;

    public itemsMenuWindow(ISuppliersFacade facade, String description) {
        super(facade,description);
    }

    @Override
    protected void createMenu() {
        menu=new HashMap<>();
        menu.put(1,"Add item to the store");
        menu.put(2,"View all items in the contract of a given supplier");
        menu.put(3,"Go back to main menu");
    }

    public void start() {
        printDescription();
        while (!shouldTerminate) {
            switch (printMenu()) {
                case 1:
                    addItemToStore();
                    break;
                case 2:
                    viewAllItemsBySupplier();
                    break;
                case 3:
                    terminate();
                    break;
            }
        }
        closeWindow();
    }

    private void viewAllItemsBySupplier() {
        Response<List<ProductDTO>>response=facade.getItemsBySupplier(utills.getNonNegativeNumber("\nEnter supplier's ID"));
        utills.printErrorMessageOrListOfValues(response);
    }

    private void addItemToStore() {
        System.out.println("\nEnter the product's name");
        String productName=scanner.nextLine();
        Response<Integer> response=facade.addItemToStore(productName);
        utills.printMessageOrSuccess(response,"Successfully added an item of id " + response.getValue() + " to the store");
    }

    private void terminate(){shouldTerminate=true;}
    private void closeWindow(){shouldTerminate=false;}



}
