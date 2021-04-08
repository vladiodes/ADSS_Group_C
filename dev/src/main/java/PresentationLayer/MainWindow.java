package PresentationLayer;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.StockController;
import DTO.CategoryDTO;
import DTO.ItemDTO;
import DTO.ReportDTO;
import DTO.SaleDTO;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainWindow {
    private HashMap<Integer,String> menu;
    private Facade facade;
    private Scanner myObj;
    private boolean shouldTerminate;

    public MainWindow(){
        this.shouldTerminate=false;
        this.myObj = new Scanner(System.in);
        this.facade=Facade.getInstance();
        this.menu=new HashMap<Integer, String>();
        this.buildMenu();

    }

    private void buildMenu(){
        this.menu.put(1,"1) add item");
        this.menu.put(2,"2) update item");
        this.menu.put(3,"3) add category");
        this.menu.put(4,"4) update category");
        this.menu.put(5,"5) find item by location");
        this.menu.put(6,"6) change alert time");
        this.menu.put(7,"7) add category discount");
        this.menu.put(8,"8) add item discount");
        this.menu.put(9,"9) add sale");
        this.menu.put(10,"10) get weekly sales report");
        this.menu.put(11,"11) show faulty items");
        this.menu.put(12,"12) show exp items");
        this.menu.put(13,"13) show minimum amount items");
        this.menu.put(14,"14) end proccess");

    }
    private void printMenu(){
        System.out.println(menu.toString());
    }
    public void start(){
        while(!shouldTerminate){
            this.printMenu();
            int choise =myObj.nextInt();
            switch (choise){
                case 1:
                    this.addItem();
                case 2:
                    this.updateItem();
                case 3:
                    this.addCategory();
                case 4:
                    this.updateCategory();
                case 5:
                    this.findItemByLocation();
                case 6:
                    this.changeAlertTime();
                case 7:
                    this.addCategoryDiscount();
                case 8:
                    this.addItemDiscount();
                case 9:
                    this.addSale();
                case 10:
                    this.weeklyReport();
                case 11:
                    this.faultyItems();
                case 12:
                    this.expItems();
                case 13:
                    this.minAmountItems();
                case 14:
                    this.shouldTerminate=true;
                    System.out.println("bye bye");

            }
        }
    }
    public static void printMessage(Response<? extends Object> response, String successMessage) {
        if(response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(successMessage);
    }
    public static void printReport(Response<? extends Object> response) {
        if(response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(response.getValue().toString());
    }

    private void addItem () {
        System.out.println("please enter category id : ");
        int categoryID = myObj.nextInt();
        System.out.println("please enter item name : ");
        String name = myObj.nextLine();
        System.out.println("please enter item location : ");
        int location = myObj.nextInt();
        System.out.println("please enter producer : ");
        String producer = myObj.nextLine();
        System.out.println("please enter available amount : ");
        int availableAmount = myObj.nextInt();
        System.out.println("please enter storage amount : ");
        int storageAmount = myObj.nextInt();
        System.out.println("please enter shelf amount : ");
        int shelfAmount = myObj.nextInt();
        System.out.println("please enter minimum amount : ");
        int minAmount = myObj.nextInt();
        System.out.println("please enter experation date : ");
        LocalDateTime expDate = new LocalDateTime();
        System.out.println("please enter buying price : ");
        double buyingPrice = myObj.nextDouble();

        Response<ItemDTO> response=this.facade.addItem(categoryID,location,name,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);
        this.printMessage(response,"Successfully added item");
    }
    private void updateItem () {
        System.out.println("please enter item name : ");
        String name = myObj.nextLine();
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        System.out.println("please enter item location : ");
        int location = myObj.nextInt();
        System.out.println("please enter producer : ");
        String producer = myObj.nextLine();
        System.out.println("please enter available amount : ");
        int availableAmount = myObj.nextInt();
        System.out.println("please enter storage amount : ");
        int storageAmount = myObj.nextInt();
        System.out.println("please enter shelf amount : ");
        int shelfAmount = myObj.nextInt();
        System.out.println("please enter minimum amount : ");
        int minAmount = myObj.nextInt();
        System.out.println("please enter experation date : ");
        LocalDateTime expDate = new LocalDateTime();
        System.out.println("please enter buying price : ");
        double buyingPrice = myObj.nextDouble();


        Response<Boolean> response=this.facade.updateItem(itemID,name,location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);

        this.printMessage(response,"Successfully update item");
    }
    private void addCategory () {
        System.out.println("please enter category name : ");
        String name = myObj.nextLine();
        System.out.println("please enter father-category ID : ");
        int fatherID = myObj.nextInt();

        Response<CategoryDTO> response=this.facade.addCategory(name,fatherID);
        this.printMessage(response,"Successfully added category");
    }
    private void updateCategory () {
        System.out.println("please enter category name : ");
        String name = myObj.nextLine();
        System.out.println("please enter category ID : ");
        int categoryID = myObj.nextInt();

        Response<Boolean> response=this.facade.updateCategory(categoryID,name);
        this.printMessage(response,"Successfully update category");
    }
    private void findItemByLocation(){
        System.out.println("please enter item location : ");
        int location = myObj.nextInt();
        Response<ItemDTO> response=this.facade.getItemByLocation(location);
        this.printMessage(response,"Successfully finding item");
    }
    private void changeAlertTime(){
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        System.out.println("please enter new days amount of alert : ");
        int daysAmount = myObj.nextInt();
        Response<Boolean> response=this.facade.changeAlertTime(itemID,daysAmount);
        this.printMessage(response,"Successfully changing item alert time");

    }
    private void addCategoryDiscount(){
        System.out.println("please enter category id : ");
        int categoryID = myObj.nextInt();
        System.out.println("please enter discount % : ");
        double discount = myObj.nextDouble();
        Response<Boolean> response = this.facade.addCategoryDiscount(categoryID,discount);
        this.printMessage(response,"Successfully added discount");

    }
    private void addItemDiscount(){
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        System.out.println("please enter discount % : ");
        double discount = myObj.nextDouble();
        Response<Boolean> response = this.facade.addItemDiscount(itemID,discount);
        this.printMessage(response,"Successfully added discount");

    }
    private void addSale(){
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        System.out.println("please enter item id : ");
        double sellingPrice = myObj.nextDouble();
        LocalDateTime saleDate=LocalDateTime.now();
        Response<SaleDTO> response = this.facade.addSale(itemID,sellingPrice,saleDate);
        this.printMessage(response,"Successfully added sale");

    }
    private void weeklyReport(){
        ArrayList<Integer> categoriesList = new ArrayList<>();
        while (myObj.hasNext())
            categoriesList.add(myObj.nextInt());
        Response<ReportDTO> response = this.facade.getWeeklyReport(categoriesList);
        this.printReport(response);
    }
    private void faultyItems(){
        Response<ReportDTO> response = this.facade.showFaultyItems();
        this.printReport(response);
    }
    private void expItems(){
        Response<ReportDTO> response = this.facade.showExpItems();
        this.printReport(response);    }
    private void minAmountItems(){
        Response<ReportDTO> response = this.facade.showMinAmountItems();
        this.printReport(response);
    }




}
