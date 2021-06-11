package PresentationLayer;

import BusinessLayer.Facade.SupplierFacadeImpl;
import BusinessLayer.Facade.TransportsEmployeesFacade;

import java.util.HashMap;

public class LogisticManagerWindow extends menuWindow {

    private boolean shouldTerminate;
    private menuWindow[] windows;

    public LogisticManagerWindow(SupplierFacadeImpl supplierFacade, TransportsEmployeesFacade transportsEmployeesFacade){
        super("Logistic Manager");
        createMenu();
        shouldTerminate=false;
        windows=new menuWindow[]{
                new TransportsMain(transportsEmployeesFacade),
                new menuWindow("Logout") {
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
    @Override
    public void start() {

        while (!shouldTerminate){
            int choice=printMenu();
            windows[choice-1].start();
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
        menu.put(1,"Manage transports");
        menu.put(2,"Logout");
    }
}
