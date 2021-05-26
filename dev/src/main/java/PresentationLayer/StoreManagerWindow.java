package PresentationLayer;

import BusinessLayer.Facade.SupplierFacadeImpl;
import Misc.TypeOfEmployee;

import java.util.HashMap;

public class StoreManagerWindow extends menuWindow {
    private SupplierFacadeImpl supplierFacade;
    private menuWindow[] windows;
    private boolean shouldTerminate=false;
    public StoreManagerWindow(SupplierFacadeImpl supplierFacade){
        super("Store manager");
        createMenu();
        windows=new menuWindow[]{
                new suppliersMenuWindow(supplierFacade,"Manage suppliers"),
                new itemsMenuWindow(TypeOfEmployee.BranchManager),
                new menuWindow("Exit") {
                    @Override
                    public void start() {
                        terminate();
                    }

                    @Override
                    protected void createMenu() {

                    }
                }
        };
        this.supplierFacade=supplierFacade;
    }
    @Override
    public void start() {
        printDescription();
        System.out.println("Please choose an option:");
        while (!shouldTerminate){
            int choice=printMenu()-1;
            windows[choice].start();
        }
        closeWindow();
    }

    private void closeWindow() {
        shouldTerminate=false;
    }

    private void terminate() {
        shouldTerminate=true;
    }

    @Override
    protected void createMenu() {
        menu=new HashMap<>();
        menu.put(1,"Manage suppliers");
        menu.put(2,"View reports");
        menu.put(3,"Logout");

    }
}
