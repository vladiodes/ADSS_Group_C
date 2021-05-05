package PresentationLayer;

import BusinessLayer.Facade.SupplierFacadeImpl;

import java.util.HashMap;

//@TODO: fix a bug with the items menu: opens but doesn't close at the end
public class mainMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    private menuWindow[] windows;
    public mainMenuWindow() {
        super("Main menu");
        initWindows();
    }

    private void initWindows() {
        SupplierFacadeImpl supplierFacade=new SupplierFacadeImpl();
        windows = new menuWindow[]{
                new suppliersMenuWindow(supplierFacade, "Suppliers menu"),
                new ordersMenuWindow(supplierFacade, "Orders menu"),
                new itemsMenuWindow(),
                new menuWindow("Exit") {
                    @Override
                    public void start() {
                        terminate();
                    }

                    @Override
                    protected void createMenu() {
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
