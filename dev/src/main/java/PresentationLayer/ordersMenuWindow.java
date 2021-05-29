package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;
import BusinessLayer.Facade.Response;
import DTO.OrderDTO;
import Misc.utills;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ordersMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    private ISuppliersFacade facade;
    public ordersMenuWindow(ISuppliersFacade facade, String description) {
        super(description);
        createMenu();
        this.facade=facade;
    }

    @Override
    protected void createMenu() {
        menu=new HashMap<>();
        menu.put(1,"Create an order");
        menu.put(2,"Re-order an existing order");
        menu.put(3,"Add an item to an order");
        menu.put(4,"View order details");
        menu.put(5,"Receive order from supplier");
        menu.put(6,"View all order by a given supplier");
        menu.put(7,"Cancel an order");
        menu.put(8,"Delete an item from an order");
        menu.put(9,"Find a transportation for an order");
        menu.put(10,"Go back to the main menu");
    }


    public void start() {
        printDescription();
        while (!shouldTerminate) {
            switch (printMenu()) {
                case 1:
                    createOrder();
                    break;
                case 2:
                    reOrder();
                    break;
                case 3:
                    addItemToOrder();
                    break;
                case 4:
                    viewOrder();
                    break;
                case 5:
                    receiveOrder();
                    break;
                case 6:
                    viewOrders();
                    break;
                case 7:
                    cancelOrder();
                    break;
                case 8:
                    deleteItemFromOrder();
                    break;
                case 9:
                    findTransportForOrder();
                    break;
                case 10:
                    terminate();
                    break;
            }
        }
        closeWindow();
    }

    private void findTransportForOrder() {
        Response<Boolean> response=facade.findTransportForOrder(
                utills.getNonNegativeNumber("\nEnter the id of the supplier that supplies this order"),
                utills.getNonNegativeNumber("\nEnter the id of the order")
        );
        utills.printMessageOrSuccess(response,"Successfully found a transportation for the order!");
    }

    private void deleteItemFromOrder() {
        Response<Boolean> response = facade.deleteProductFromOrder(
                utills.getNonNegativeNumber("\nEnter the id of the supplier that supplies this order"),
                utills.getNonNegativeNumber("\nEnter the id of the order"),
                utills.getNonNegativeNumber("\nEnter the id of the product as it appears in the catalogue of the supplier"));
        utills.printMessageOrSuccess(response, "Successfully deleted an item from the order");
    }

    private void cancelOrder() {
        Response<Boolean> response = facade.cancelOrder(
                utills.getNonNegativeNumber("\nEnter the id of the supplier that supplies this order"),
                utills.getNonNegativeNumber("\nEnter the id of the order you'd like to cancel"));
        utills.printMessageOrSuccess(response, "Successfully canceled the order");
    }

    private void viewOrders() {
        Response<List<OrderDTO>> response = facade.getOrdersBySupplier(
                utills.getNonNegativeNumber("\nEnter supplier's id you'd like to see its orders"));
        utills.printErrorMessageOrListOfValues(response);
    }

    private void receiveOrder() {
        Response<Boolean> response=facade.receiveOrder(
                utills.getNonNegativeNumber("\nEnter supplier's id the order was supplied by"),
                utills.getNonNegativeNumber("\nEnter the id of the supplied order"));
        utills.printMessageOrSuccess(response,"Successfully received the order");
    }

    private void viewOrder() {
        Response<OrderDTO> response = facade.getOrder(
                utills.getNonNegativeNumber("\nEnter the supplier's id"),
                utills.getNonNegativeNumber("\nEnter the id of the order you'd like to see details about"));
        if (response.WasException())
            utills.printMessageOrSuccess(response, null);
        else
            utills.printMessageOrSuccess(response, response.getValue().toString());
    }


    private void addItemToOrder() {
        Response<Boolean> response=facade.addItemToOrder(
                utills.getNonNegativeNumber("\nEnter the supplier's id"),
                utills.getNonNegativeNumber("\nEnter the id of the order you'd like to add an item to"),
                utills.getNonNegativeNumber("\nEnter the quantity of the given item you'd like to add to the order"),
                utills.getNonNegativeNumber("\nEnter the id of the product you'd like to add as it appears in the supplier's catalogue"));
        utills.printMessageOrSuccess(response,"Successfully added an item to the given order");
    }

    private void reOrder() {
        Response<Integer> response=facade.reOrder(
                utills.getNonNegativeNumber("\nEnter the id of the supplier you'd like to re order from"),
                utills.getNonNegativeNumber("\nEnter the id of the original order you'd like to re order"),
                LocalDate.now());
        utills.printMessageOrSuccess(response,"Successfully reordered, the id of the new order is " + response.getValue());
    }

    private void createOrder() {
        Response<Integer> response=facade.openOrder(
                utills.getNonNegativeNumber("\nEnter the supplier id you'd like to order from"),
                utills.getDateFromUser("Enter the date the order is supposed to arrive"),
                getFixed()
        );
        utills.printMessageOrSuccess(response,"A new order of id " + response.getValue() + " was issued with the given supplier");
    }

    private boolean getFixed(){
        System.out.println("\nDo you want this order to be fixed? (will be automatically re-ordered to a week forward) 1.Fixed    2.Not fixed");
        int fixed=-1;
        for(fixed=utills.checkIfInBounds(scanner.nextLine(),3);fixed==-1;fixed=utills.checkIfInBounds(scanner.nextLine(),3))
            System.out.println("\nWrong input, 1.Fixed    2.Not fixed");
        return fixed==1;
    }

    private void terminate(){shouldTerminate=true;}
    private void closeWindow(){shouldTerminate=false;}
}
