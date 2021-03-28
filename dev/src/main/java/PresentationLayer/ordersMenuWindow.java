package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;
import BusinessLayer.Facade.Response;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ordersMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    public ordersMenuWindow(ISuppliersFacade facade, String description) {
        super(facade,description);
    }

    @Override
    protected void createMenu() {
        menu=new HashMap<>();
        menu.put(1,"Create an order");
        menu.put(2,"Re-order an existing order");
        menu.put(3,"Add an item to an order");
        menu.put(4,"View order details");
        menu.put(5,"Receive order from supplier");
        menu.put(6,"View all order ids by a given supplier");
        menu.put(7,"Cancel an order");
        menu.put(8,"Delete an item from an order");
        menu.put(9,"Go back to the main menu");
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
                    terminate();
                    break;
            }
        }
        closeWindow();
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
        Response<List<Integer>> response = facade.getOrderIdsBySupplier(
                utills.getNonNegativeNumber("\nEnter supplier's id you'd like to see its orders"));
        utills.printErrorMessageOrListOfValues(
                new Response<>(
                        response.getValue().stream().map((num) -> "Order ID: " + num).collect(Collectors.toList())));
    }

    private void receiveOrder() {
        Response<Boolean> response=facade.receiveOrder(
                utills.getNonNegativeNumber("\nEnter supplier's id the order was supplied by"),
                utills.getNonNegativeNumber("\nEnter the id of the supplied order"));
        utills.printMessageOrSuccess(response,"Successfully received the order");
    }

    private void viewOrder() {
        Response<List<String>> response=facade.getOrder(
                utills.getNonNegativeNumber("\nEnter the supplier's id"),
                utills.getNonNegativeNumber("\nEnter the id of the order you'd like to see details about"));
        utills.printErrorMessageOrListOfValues(response);
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
                new Date(LocalDate.now().getYear(),LocalDate.now().getMonth().getValue(),LocalDate.now().getDayOfMonth())
        );
        utills.printMessageOrSuccess(response,"Successfully reordered, the id of the new order is " + response.getValue());
    }

    private void createOrder() {
        //@TODO: isFixed param should be decided by the supplier contract and not delivered by parameter from the user
        facade.openOrder(
                utills.getNonNegativeNumber("\nEnter the supplier id you'd like to order from"),
                new Date(LocalDate.now().getYear(),LocalDate.now().getMonth().getValue(),LocalDate.now().getDayOfMonth()),
                true
        );
    }

    private void terminate(){shouldTerminate=true;}
    private void closeWindow(){shouldTerminate=false;}
}
