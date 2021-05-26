package PresentationLayer;
import BusinessLayer.Facade.SupplierFacadeImpl;
import BusinessLayer.Facade.TransportsEmployeesFacade;
import Misc.TypeOfEmployee;

import java.util.HashMap;

public class mainMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    private menuWindow[] windows;
    public mainMenuWindow() {
        super("Main menu");
        createMenu();
        initWindows();
    }

    private void initWindows() {
        TransportsEmployeesFacade transportsEmployeeFacade=new TransportsEmployeesFacade(TypeOfEmployee.HRManager);
        SupplierFacadeImpl supplierFacade=new SupplierFacadeImpl();
        windows = new menuWindow[]{
                new Menus(transportsEmployeeFacade.setTypeOfLoggedIn(TypeOfEmployee.HRManager)),
                new StorageManagerWindow(supplierFacade),
                new TransportsMain(transportsEmployeeFacade.setTypeOfLoggedIn(TypeOfEmployee.LogisticManager)),
                new StoreManagerWindow(supplierFacade),
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
        menu.put(1,"HR Manager"); //employees module
        menu.put(2,"Storage Manager"); //inventory+suppliers module
        menu.put(3,"Logistic Manager"); //transportations module
        menu.put(4,"Store Manager"); //misc of modules
        menu.put(5,"Exit");
    }

    public void start() {
        printDescription();
        System.out.println("Please choose a type of employee:");
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
