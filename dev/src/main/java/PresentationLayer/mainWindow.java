package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;

public class mainWindow extends Window {
    private boolean shouldTerminate=false;
    private Window[] menu={
            new suppliersWindow(facade,"Suppliers menu"),
            new ordersWindow(facade,"Orders menu"),
            new itemsWindow(facade,"Items menu"),
            new Window(null,"Exit") {
        @Override
        public void start() {
            terminate();
        }
    }};
    public mainWindow(ISuppliersFacade facade) {
        super(facade,"Main menu");
    }

    public void start() {
        printDescription();
        while (!shouldTerminate){
            int choice=printMenu();
            menu[choice].start();
        }
        System.out.println("Goodbye!");
    }

    private int printMenu() {
        int input = -2;
        System.out.println("Please choose an option:");
        while (input < 0) {
            if (input == -1)
                System.out.println("Wrong input. Please enter one of the following numbers:");
            for (int i = 0; i < menu.length; i++)
                System.out.println((i + 1) + ". " + menu[i].description);
            System.out.println("\nEnter your choice:");
            String choice = scanner.nextLine();
            input = inputChecker.checkIfInBounds(choice, menu.length + 1);
        }
        return input-1;
    }
    private void terminate(){
        shouldTerminate=true;
    }
}
