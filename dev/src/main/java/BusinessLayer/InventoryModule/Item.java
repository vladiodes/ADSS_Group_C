package BusinessLayer.InventoryModule;

import BusinessLayer.SuppliersModule.Contract;
import javafx.util.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Item {
    // -- fields
    private int specificIDCounter = 0;
    private int id;
    private String name;
    private List<SpecificItem> items;
    private int minAmount;
    private List<Contract> contractList;
    private int alertTime;
    private double buyingPrice;
    private double sellingPrice;

    //@TODO: add contract lists as a field (FINISHED)

    //@TODO: at the end - change the class to flyweight pattern(FINISHED)

    //@TODO: ask rami about the automatic orders regarding expired items (FINISHED)


    // -- constructor

    public Item(int id, String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, double buyingPrice,double sellingPrice){
        this.id=id;
        this.name=name;
        this.minAmount=minAmount;
        this.alertTime=2;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.contractList = new ArrayList<>();
        items = new ArrayList<>();
        SpecificItem item = new SpecificItem(specificIDCounter++,location,storageAmount,shelfAmount,expDate,producer);
        items.add(item);
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Item ID: "+ this.id + "\n");
        builder.append("Item Name: " + this.name + "\n");
        builder.append("Item Minimum Amount: "+ this.minAmount + "\n");
        builder.append("Item Alert Time: "+ this.alertTime +"\n");
        builder.append("Item Available Amount: "+ this.getAmount());
        return builder.toString();
    }

    public int getId() {
        return id;
    }
    public int getMinAmount() {
        return minAmount;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void updateItem(String name, int minAmount,double buyingPrice,double sellingPrice)
    {
        if(name == null || name == "")
            throw new IllegalArgumentException("invalid item name");

        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");
        if (buyingPrice<0)
            throw new IllegalArgumentException("invalid buying price");
        if (sellingPrice<0)
            throw new IllegalArgumentException("invalid selling price");

        this.name = name;
        this.minAmount=minAmount;
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
    }

    public void addDiscount(double discount) {
        this.sellingPrice = this.sellingPrice - this.sellingPrice*discount/100;
    }
    public void addSpecificItem(int location,  int storageAmount, int shelfAmount, LocalDate expDate, String producer)
    {
        SpecificItem newItem = new SpecificItem(this.specificIDCounter++,location,storageAmount,shelfAmount,expDate,producer);
        this.items.add(newItem);
    }
    public int getAlertTime() {
        return this.alertTime;
    }

    public void setAlertTime(int alertTime) {
        if(alertTime < 0)
            throw new IllegalArgumentException("invalid alert time");
        this.alertTime = alertTime;
    }

    public boolean isMinAmount() {
        return this.minAmount>=this.getAmount();
    }
    public int getAmount()
    {
        int sum = 0;
        for(SpecificItem item : items)
        {
            sum += item.getAvailableAmount();
        }
        return sum;
    }
    //@TODO sale item should return true/false upon missing items (FINISHED)

    // we sell the specific items who are almost expired first
    public boolean SaleItem(int quantity) {
        this.items.sort(new Comparator<SpecificItem>() {
            @Override
            public int compare(SpecificItem o1, SpecificItem o2) {
                return o1.getExpDate().compareTo(o2.getExpDate());
            }
        });
        for(SpecificItem item : items)
        {
            if(quantity > 0)
            {
                if(item.getShelfAmount() >= quantity)
                {
                    item.setShelfAmount(item.getShelfAmount()-quantity);
                    quantity = 0;
                }
                else if(item.getShelfAmount() > 0)
                {
                    quantity = quantity - item.getShelfAmount();
                    item.setShelfAmount(0);
                    if(item.getStorageAmount() >= quantity)
                    {
                        item.setStorageAmount(item.getStorageAmount()-quantity);
                        quantity = 0;
                    }
                    else
                    {
                        quantity = quantity - item.getStorageAmount();
                        item.setStorageAmount(0);
                    }
                }
            }
            else
                break;
        }

        //@TODO: check if need to make an order (min quantity, etc..) (FINISHED)
        return this.getAmount() < minAmount;
    }

    public void removeFaultyItems()
    {
        for(int i=0;i<items.size();i++)
        {
            SpecificItem item = items.get(i);
            if(item.getExpDate().compareTo(LocalDate.now()) <= 0)
                items.remove(item);
        }
    }
    public void addContract(Contract contract)
    {
        if(contract == null)
            throw new IllegalArgumentException("Contract can't be null");
        this.contractList.add(contract);
    }

    public Pair<Integer, Integer> getCheapestSupplier()
    {
        int minID = -1;
        int minCatalogueID = -1;
        double minPrice =-1;
        if(!contractList.isEmpty())
        {
            minID = contractList.get(0).getSupplier().getSupplierID();
            minPrice = contractList.get(0).getPricePerUnit();
            minCatalogueID = contractList.get(0).getCatalogueIDBySupplier();
        }
        for(Contract c : contractList)
        {
            if(c.getPricePerUnit() < minPrice)
            {
                minID = c.getSupplier().getSupplierID();
                minPrice = c.getPricePerUnit();
                minCatalogueID = c.getCatalogueIDBySupplier();
            }
        }
        Pair<Integer,Integer> ans = new Pair<>(minID,minCatalogueID);
        return ans;
    }

    public void setID(int i) {
        if(i == -1) // we only adjust itemID in case of item deletion
            this.id = i;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }

    public List<SpecificItem> getSpecificItems() {
        return this.items;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
    //@TODO: functions that get the cheapest supplier id, and the cheapest id catalogue (FINISHED)

    //@TODO: think of a solution how to overcome the expiration date field? (flyweight)
}