package PresentationLayer;

import BusinessLayer.Facade;
import DTO.ItemDTO;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Scanner;

public class MainWindow {
    private HashMap<Integer,String> menu;
    private Facade facade;
    private Scanner myObj;

    public MainWindow(){
        this.myObj = new Scanner(System.in);
        this.facade=new Facade();
        this.menu=new HashMap<Integer, String>();
        this.buildMenu();

    }

    private void buildMenu(){
        this.menu.put(1,"add item");
        this.menu.put(2,"update item");
        this.menu.put(3,"add category");
        this.menu.put(4,"update category");
        this.menu.put(5,"show minimum amount items");
        this.menu.put(6,"show expaired items");
        this.menu.put(7,"faulty items report");
        this.menu.put(8,"category items report");
        this.menu.put(9,"find item by location");
        this.menu.put(10,"change alert time");
    }
    private void printMenu(){
        System.out.println(menu.toString());
    }

    public void start(){

        while(true){
            this.printMenu();
            int choise =myObj.nextInt();
            switch (choise){
                case 1:
                    System.out.println("please enter category id : ");
                    int categoryID = myObj.nextInt();
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

                    this.facade.addItem(categoryID,location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);
                case 2:
                    int categoryID = myObj.nextInt();


                    ItemDTO itemDTO = new ItemDTO()
                    this.facade.updateItem(itemDTO);
                case 3:
                    System.out.println("please enter name : ");
                    String name = myObj.nextLine();
                    System.out.println("please enter father category id : ");
                    int fatherID = myObj.nextInt();
                    this.facade.addCategory(name,fatherID);
                case 4:
                    this.facade.updateCategory();
                case 5:
                    this.facade.showMinAmountItems();
                case 6:
                    this.facade.showExpItems();
                case 7:
                    this.facade.showFaultyItems();
                case 8:
                    this.facade.getWeeklyReport();
                case 9:
                    System.out.println("please enter location : ");
                    int loc = myObj.nextInt();
                    this.facade.getItemByLocation(loc);
                case 10:
                    System.out.println("please enter item id : ");
                    int itemID = myObj.nextInt();
                    System.out.println("please enter altert time : ");
                    int alertTime = myObj.nextInt();
                    this.facade.changeAlertTime(itemID,alertTime);
            }


        }
    }

    // input from the user

    private void getItemDetails() {
    }


}
