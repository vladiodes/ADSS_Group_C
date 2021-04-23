package PresentationLayer;

import BusinessLayer.SuppliersModule.DayOfWeek;
import BusinessLayer.Facade.SupplierFacadeImpl;
import BusinessLayer.SuppliersModule.PaymentAgreement;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

public class mainMenuWindow extends menuWindow {
    private boolean shouldTerminate=false;
    private menuWindow[] windows;
    public mainMenuWindow() {
        super(new SupplierFacadeImpl(),"Main menu");
        initWindows();
    }

    private void initWindows() {
        windows = new menuWindow[]{
                new suppliersMenuWindow(facade, "Suppliers menu"),
                new ordersMenuWindow(facade, "Orders menu"),
                new itemsMenuWindow(facade, "Items menu"),
                new menuWindow(facade,"Scenario 1") {
                    @Override
                    public void start() {
                        try {
                            scenario1();
                            System.out.println("The system now contains scenario 1 data");
                        }
                        catch (Exception e){
                            System.out.println("You've already executed this scenario.");
                        }
                    }
                    @Override
                    protected void createMenu() {
                    }
                },
                new menuWindow(facade,"Scenario 2") {
                    @Override
                    public void start() {
                        try {
                            scenario2();
                            System.out.println("The system now contains scenario 2 data");
                        }
                        catch (Exception e){
                            System.out.println("You've already executed this scenario.");
                        }
                    }
                    @Override
                    protected void createMenu() {
                    }
                },
                new menuWindow(null, "Exit") {
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
        menu.put(4,"Scenario #1");
        menu.put(5,"Scenario #2");
        menu.put(6,"Exit");
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
    private void scenario1(){
        HashSet<DayOfWeek> dayOfWeeks=new HashSet<>();
        dayOfWeeks.add(DayOfWeek.Sunday); dayOfWeeks.add(DayOfWeek.Tuesday);
        HashSet<String> categories=new HashSet<>();
        categories.add("Diary");
        HashSet<String> manufactures=new HashSet<>();
        manufactures.add("Tara");
        HashMap<String,String> contactInfo=new HashMap<>();
        contactInfo.put("Yossi Levi","052-123550");
        HashMap<Double,Integer> discounts=new HashMap<>();
        discounts.put(10000.0,5);
        HashMap<Integer,Integer> discountByQuantity=new HashMap<>();
        discountByQuantity.put(1000,5);
        int supplierID=facade.addSupplier("Yossi's Diary",dayOfWeeks,false,"125/112", PaymentAgreement.Monthly,categories,manufactures,contactInfo,discounts).getValue();
        int product1=facade.addItemToStore("Tara milk").getValue();
        int product2=facade.addItemToStore("Tara Cottege").getValue();
        facade.addItemToSupplier(supplierID,product1,7,10.90,null);
        facade.addItemToSupplier(supplierID,product2,5,5.99,discountByQuantity);
        facade.openOrder(0, LocalDateTime.now(),false);
        facade.addItemToOrder(0,0,200,7);
    }
    private void scenario2(){
        HashSet<String> categories=new HashSet<>();
        categories.add("Meat");
        HashSet<String> manufactures=new HashSet<>();
        manufactures.add("Zoglobeck");
        manufactures.add("Meatzilla");
        HashMap<String,String> contactInfo=new HashMap<>();
        contactInfo.put("Omer Cohen","052-144550");
        int supplier1=facade.addSupplier("The Butcher",null,false,"235211",PaymentAgreement.PerOrder,categories,manufactures,contactInfo,null).getValue();

        HashSet<String> categories2=new HashSet<>();
        categories2.add("Meat");
        HashSet<String> manufactures2=new HashSet<>();
        manufactures2.add("Fresh meats");
        manufactures2.add("Zoglobeck");
        HashMap<String,String> contactInfo2=new HashMap<>();
        contactInfo2.put("Daniel Cohen","052-123550");
        int supplier2=facade.addSupplier("Danny's Meat",null,false,"234211",PaymentAgreement.PerOrder,categories2,manufactures2,contactInfo2,null).getValue();

        int product=facade.addItemToStore("Zoglobeck Salami").getValue();
        facade.addItemToSupplier(supplier1,product,31,10.5,null);
        facade.addItemToSupplier(supplier2,product,21,15.5,null);
    }
}
