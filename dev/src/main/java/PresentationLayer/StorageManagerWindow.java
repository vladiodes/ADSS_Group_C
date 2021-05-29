package PresentationLayer;

import BusinessLayer.Facade.SupplierFacadeImpl;
import BusinessLayer.Facade.TransportsEmployeesFacade;
import Misc.TypeOfEmployee;

import java.util.HashMap;

public class StorageManagerWindow extends menuWindow {

    private SupplierFacadeImpl facade;
    private menuWindow[] windows;
    boolean shouldTerminate;
    public StorageManagerWindow(SupplierFacadeImpl facade, TransportsEmployeesFacade transportsEmployeesFacade){
        super("Storage Manager");
        createMenu();
        shouldTerminate=false;
        this.facade=facade;
        windows=new menuWindow[]{
                new suppliersMenuWindow(facade, "Suppliers management"),
                new itemsMenuWindow(TypeOfEmployee.Storage),
                new ordersMenuWindow(facade, "Orders management"),
                new menuWindow("Go back") {
                    @Override
                    public void start() {
                        terminate();
                    }

                    @Override
                    protected void createMenu() {

                    }
                }

        };
    }

    private void terminate() {
        shouldTerminate=true;
    }

    private void closeWindow(){
        shouldTerminate=false;
    }

    @Override
    public void start() {
        printDescription();
        while (!shouldTerminate){
            int choice=printMenu()-1;
            windows[choice].start();
        }
        closeWindow();
    }

    @Override
    protected void createMenu() {
        menu=new HashMap<>();
        menu.put(1,"Manage Suppliers");
        menu.put(2,"Manage Inventory");
        menu.put(3,"Manage Orders");
        menu.put(4,"Logout");
    }
}
