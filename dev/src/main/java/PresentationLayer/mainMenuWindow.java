package PresentationLayer;

import BusinessLayer.Facade.ISuppliersFacade;

import java.util.HashMap;

public class mainMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    private menuWindow[] windows;
    public mainMenuWindow(ISuppliersFacade facade) {
        super(facade,"Main menu");
        windows = new menuWindow[]{
                new suppliersMenuWindow(facade, "Suppliers menu"),
                new ordersMenuWindow(facade, "Orders menu"),
                new itemsMenuWindow(facade, "Items menu"),
                new menuWindow(null, "Exit") {
                    @Override
                    public void start() {
                        terminate();
                    }

                    @Override
                    protected void createMenu() {
                        return;//does nothing
                    }
                }};
    }
    @Override
    protected void createMenu() {
        menu=new HashMap<>();
        menu.put(1,"Suppliers menu");
        menu.put(2,"Orders menu");
        menu.put(3,"Items menu");
        menu.put(4,"Exit");
    }

    public void start() {
        printDescription();
        while (!shouldTerminate){
            int choice=printMenu()-1;
            windows[choice].start();
        }
        System.out.println("Goodbye!");
    }
    private void terminate(){
        shouldTerminate=true;
    }
}
