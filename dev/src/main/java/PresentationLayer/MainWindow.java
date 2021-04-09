package PresentationLayer;

import BusinessLayer.Facade;
import BusinessLayer.Response;
import BusinessLayer.StockController;
import DTO.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class MainWindow {
    private HashMap<Integer,String> menu;
    private Facade facade;
    private Scanner myObj;
    private boolean shouldTerminate;
    private boolean scenario;

    public MainWindow(){
        this.scenario=true;
        this.shouldTerminate=false;
        this.myObj = new Scanner(System.in);

        this.facade=Facade.getInstance();
        this.menu=new HashMap<Integer, String>();
        this.buildMenu();

    }

    private void buildMenu(){
        this.menu.put(1," add item\n");
        this.menu.put(2," update item\n");
        this.menu.put(3," add category\n");
        this.menu.put(4," update category\n");
        this.menu.put(5," find item by location\n");
        this.menu.put(6," change alert time\n");
        this.menu.put(7,"add category discount\n");
        this.menu.put(8,"add item discount\n");
        this.menu.put(9,"add sale\n");
        this.menu.put(10,"get weekly inventory report\n");
        this.menu.put(11,"show faulty items\n");
        this.menu.put(12,"show exp items\n");
        this.menu.put(13,"show minimum amount items\n");
        this.menu.put(14,"Show Sales Report\n");
        this.menu.put(15, "delete item\n");
        this.menu.put(16, "run scenario\n");
        this.menu.put(17,"end\n");

    }
    private void printMenu(){
        System.out.println(menu.toString());
    }
    public void start(){
        while(!shouldTerminate){
            this.printMenu();
            int choise =myObj.nextInt();
            myObj.nextLine();
            switch (choise){
                case 1:
                    this.addItem();
                    break;
                case 2:
                    this.updateItem();
                    break;
                case 3:
                    this.addCategory();
                    break;
                case 4:
                    this.updateCategory();
                    break;
                case 5:
                    this.findItemByLocation();
                    break;
                case 6:
                    this.changeAlertTime();
                    break;
                case 7:
                    this.addCategoryDiscount();
                    break;
                case 8:
                    this.addItemDiscount();
                    break;
                case 9:
                    this.addSale();
                    break;
                case 10:
                    this.weeklyReport();
                    break;
                case 11:
                    this.faultyItems();
                    break;
                case 12:
                    this.expItems();
                    break;
                case 13:
                    this.minAmountItems();
                    break;
                case 14:
                    this.salesReport();
                    break;
                case 15:
                    this.deleteItem();
                    break;
                case 16:
                    if (scenario) {
                        this.scenario1();
                        this.scenario = false;
                    }
                    else
                        System.out.println("scenario can run only one time");
                    break;
                default:
                    this.shouldTerminate=true;
                    System.out.println("bye bye");
                    break;
            }
        }
    }

    public static void printMessage(Response<? extends Object> response, String successMessage) {
        if(response.WasException())
            System.out.println(response.getMessage());
        else
            System.out.println(successMessage+response.getValue().toString());
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
        myObj.nextLine();
        System.out.println("please enter item name : ");
        String name = myObj.nextLine();
        System.out.println("please enter item location : ");
        int location = myObj.nextInt();
        myObj.nextLine();

        System.out.println("please enter producer : ");
        String producer = myObj.nextLine();
        System.out.println("please enter storage amount : ");
        int storageAmount = myObj.nextInt();
        myObj.nextLine();

        System.out.println("please enter shelf amount : ");
        int shelfAmount = myObj.nextInt();
        myObj.nextLine();

        System.out.println("please enter minimum amount : ");
        int minAmount = myObj.nextInt();
        myObj.nextLine();

        System.out.println("please enter experation date : ");
        LocalDate expDate = this.getDateFromUser();
        System.out.println("please enter buying price : ");
        double buyingPrice = myObj.nextDouble();
        myObj.nextLine();
        System.out.println("please enter selling price : ");
        double sellingPrice = myObj.nextDouble();
        myObj.nextLine();


        Response<ItemDTO> response=this.facade.addItem(categoryID,location,name,producer,storageAmount,shelfAmount,minAmount,expDate,buyingPrice,sellingPrice);
        this.printMessage(response,"Successfully added item\n");
    }
    private void updateItem () {
        System.out.println("please enter item name : ");
        String name = myObj.nextLine();
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter item location : ");
        int location = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter producer : ");
        String producer = myObj.nextLine();
        System.out.println("please enter storage amount : ");
        int storageAmount = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter shelf amount : ");
        int shelfAmount = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter minimum amount : ");
        int minAmount = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter experation date : ");
        LocalDate expDate = this.getDateFromUser();

        System.out.println("please enter buying price : ");
        double buyingPrice = myObj.nextDouble();
        myObj.nextLine();
        System.out.println("please enter selling price : ");
        double sellingPrice = myObj.nextDouble();
        myObj.nextLine();

        Response<Boolean> response=this.facade.updateItem(itemID,name,location,producer,storageAmount,shelfAmount,minAmount,expDate,buyingPrice,sellingPrice);

        this.printMessage(response,"Successfully update item : \n");
    }
    private void addCategory () {
        System.out.println("please enter category name : ");
        String name = myObj.nextLine();
        System.out.println("please enter father-category ID : ");
        int fatherID = myObj.nextInt();
        myObj.nextLine();


        Response<CategoryDTO> response=this.facade.addCategory(name,fatherID);
        this.printMessage(response,"Successfully added category : \n");
    }
    private void updateCategory () {
        System.out.println("please enter category name : ");
        String name = myObj.nextLine();
        System.out.println("please enter category ID : ");
        int categoryID = myObj.nextInt();
        myObj.nextLine();


        Response<Boolean> response=this.facade.updateCategory(categoryID,name);
        this.printMessage(response,"Successfully update category\n");
    }
    private void findItemByLocation(){
        System.out.println("please enter item location : ");
        int location = myObj.nextInt();
        myObj.nextLine();
        Response<ItemDTO> response=this.facade.getItemByLocation(location);
        this.printMessage(response,"Successfully finding item : \n");
    }
    private void changeAlertTime(){
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter new days amount of alert : ");
        int daysAmount = myObj.nextInt();
        myObj.nextLine();
        Response<Boolean> response=this.facade.changeAlertTime(itemID,daysAmount);
        this.printMessage(response,"Successfully changing item alert time\n");

    }
    private void addCategoryDiscount(){
        System.out.println("please enter category id : ");
        int categoryID = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter discount % : ");
        double discount = myObj.nextDouble();
        myObj.nextLine();
        Response<Boolean> response = this.facade.addCategoryDiscount(categoryID,discount);
        this.printMessage(response,"Successfully added discount\n");

    }
    private void addItemDiscount(){
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter discount % : ");
        double discount = myObj.nextDouble();
        myObj.nextLine();
        Response<Boolean> response = this.facade.addItemDiscount(itemID,discount);
        this.printMessage(response,"Successfully added discount\n");

    }
    private void addSale(){
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        myObj.nextLine();
        System.out.println("please enter quantity : ");
        int quantity = myObj.nextInt();
        myObj.nextLine();
        LocalDate saleDate=LocalDate.now();
        Response<SaleDTO> response = this.facade.addSale(itemID,quantity);
        this.printMessage(response,"Successfully added sale\n");

    }

    private void weeklyReport(){
        ArrayList<Integer> categoriesList = new ArrayList<>();
        System.out.println("please enter categories: (enter -1 to stop)");
        while (true) {
            int nextChoice = myObj.nextInt();
            myObj.nextLine();
            if(nextChoice != -1) {
                categoriesList.add(nextChoice);
            }
            else
                break;
        }
        Response<amountReportDTO> response = this.facade.getWeeklyReport(categoriesList);
        this.printReport(response);
    }
    private void faultyItems(){
        Response<defactReportDTO> response = this.facade.showFaultyItems();
        this.printReport(response);
    }
    private void expItems(){
        Response<defactReportDTO> response = this.facade.showExpItems();
        this.printReport(response);    }
    private void minAmountItems(){
        Response<amountReportDTO> response = this.facade.showMinAmountItems();
        this.printReport(response);
    }

    private void salesReport()
    {

        ArrayList<Integer> categoriesList = new ArrayList<>();
        System.out.println("please enter categories: (enter -1 to stop)");
        while (true) {
            int nextChoice = myObj.nextInt();
            myObj.nextLine();
            if(nextChoice != -1) {
                categoriesList.add(nextChoice);
            }
            else
                break;
        }

        System.out.println("please enter start date: ");
        LocalDate startDate = this.getDateFromUser();

        System.out.println("please enter end date: ");
        LocalDate endDate = this.getDateFromUser();

        Response<SaleReportDTO> response = this.facade.showSalesReport(startDate,endDate,categoriesList);
        this.printReport(response);
    }

    private void deleteItem() {
        System.out.println("please enter item id : ");
        int itemID = myObj.nextInt();
        myObj.nextLine();
        Response<Boolean> response = this.facade.deleteItem(itemID);
        this.printMessage(response,"Successfully deleted item\n");


    }
    private LocalDate getDateFromUser() {
        System.out.println("please enter day :");
        int day = myObj.nextInt();
        myObj.nextLine();
        while (day>30 || day<1) {
            System.out.println("please enter valid day :");
             day = myObj.nextInt();
            myObj.nextLine();

        }
        System.out.println("please enter month :");
        int month = myObj.nextInt();
        myObj.nextLine();
        while (month>12 || month<1) {
            System.out.println("please enter valid day :");
            day = myObj.nextInt();
            myObj.nextLine();

        }
        System.out.println("please enter year :");
        int year = myObj.nextInt();
        myObj.nextLine();
        while (year<2021 || year>2023) {
            System.out.println("please enter valid day :");
            day = myObj.nextInt();
            myObj.nextLine();

        }




        LocalDate date = LocalDate.of(year, month, day);
        return date;
    }
    public void scenario1(){
        Response<CategoryDTO> response1=this.facade.addCategory("sweets",0);
        this.printMessage(response1,"Successfully added category\n");

        Response<ItemDTO> response2=this.facade.addItem(response1.getValue().getID(),2,"chocolate","nutela",15,20,5,LocalDate.of(2021,06,15),14.90,16.90);
        this.printMessage(response2,"Successfully added item\n");

        Response<SaleDTO> response3 = this.facade.addSale(response2.getValue().getID(),1);
        this.printMessage(response3,"successfully sale\n");

        ArrayList categoriesList = new ArrayList();
        categoriesList.add(response1.getValue().getID());
        Response<ReportDTO> response4 = this.facade.getWeeklyReport(categoriesList);
        this.printReport(response4);

    }


}
