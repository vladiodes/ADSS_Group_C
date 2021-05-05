package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.ItemsMapper;
import DTO.ItemDTO;

import java.time.LocalDate;


public class Item {
    // -- fields
    private int id;
    private String name;
    private int location;
    private String producer;
    private int availableAmount;
    private int storageAmount;
    private int shelfAmount;
    private int minAmount;
    private LocalDate expDate;
    private int alertTime;
    private double buyingPrice;
    private double sellingPrice;

    //for database completeness
    private int categoryID;
    //@TODO: add contract lists as a field

    //@TODO: at the end - change the class to flyweight pattern

    //@TODO: ask rami about the automatic orders regarding expired items


    // -- constructor

    public Item(ItemDTO dto){
        this.name=dto.getName();
        this.location=dto.getLocation();
        this.producer=dto.getProducer();
        this.availableAmount=dto.getAvailableAmount();
        this.storageAmount=dto.getStorageAmount();
        this.shelfAmount=dto.getShelfAmount();
        this.minAmount=dto.getMinAmount();
        this.expDate=dto.getExpDate();
        this.alertTime=dto.getAlertTime();
        this.sellingPrice=dto.getSellingPrice();
        this.categoryID=dto.getCategoryID();
        this.id=dto.getID();
    }
    public Item(String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate,double sellingPrice,int categoryID){
        this.name=name;
        this.location=location;
        this.producer=producer;
        this.availableAmount=storageAmount+shelfAmount;
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.minAmount=minAmount;
        this.expDate=expDate;
        this.alertTime=2;
        //this.buyingPrice=buyingPrice; //@TODO: system should calculate it
        this.sellingPrice=sellingPrice;
        this.categoryID=categoryID;
        this.id=ItemsMapper.getInstance().addItem(this);
    }

    @Override
    public String toString() {
        return "item{" +
                "id=" + id +
                ", location=" + location +
                ", producer='" + producer + '\'' +
                ", availableAmount=" + availableAmount +
                ", storageAmount=" + storageAmount +
                ", shelfAmount=" + shelfAmount +
                ", minAmount=" + minAmount +
                ", expDate=" + expDate +
                '}';
    }

    public int getId() {
        return id;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public void setId(int id){
        if(id==-1)
            this.id=id;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public int getLocation() {
        return location;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getShelfAmount() {
        return shelfAmount;
    }

    public int getStorageAmount() {
        return storageAmount;
    }

    public LocalDate getExpDate() {
        return expDate;
    }

    public String getProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
        ItemsMapper.getInstance().updateItem(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateItem(String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate,double buyingPrice,double sellingPrice)
    {
        if(name == null || name == "")
            throw new IllegalArgumentException("invalid item name");
        if(location < 0)
            throw new IllegalArgumentException("invalid item location");
        if(producer == null || producer == "")
            throw new IllegalArgumentException("invalid producer name");
        if (storageAmount<0)
            throw new IllegalArgumentException("invalid storage amount");
        if (shelfAmount<0)
            throw new IllegalArgumentException("invalid shelf amount");
        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");
        if (expDate.compareTo(LocalDate.now())<0)
            throw new IllegalArgumentException("invalid exp date");
        if (buyingPrice<0)
            throw new IllegalArgumentException("invalid buying price");
        if (sellingPrice<0)
            throw new IllegalArgumentException("invalid selling price");

        this.name = name;
        this.location=location;
        this.producer=producer;
        this.availableAmount=shelfAmount+storageAmount;
        this.storageAmount=storageAmount;
        this.shelfAmount=shelfAmount;
        this.minAmount=minAmount;
        this.expDate=expDate;
        this.buyingPrice=buyingPrice;
        this.sellingPrice=sellingPrice;
        ItemsMapper.getInstance().updateItem(this);
    }

    public void addDiscount(double discount) {
        setSellingPrice(sellingPrice-sellingPrice*discount/100);
    }

    public int getAlertTime() {
        return this.alertTime;
    }

    public void setAlertTime(int alertTime) {
        if(alertTime < 0)
            throw new IllegalArgumentException("invalid alert time");
        this.alertTime = alertTime;
        ItemsMapper.getInstance().updateItem(this);
    }

    public boolean isFaulty() {
         if (this.expDate.compareTo(LocalDate.now())<=0)
             return true;
         return false;
    }

    public boolean isExp() {
        if (this.expDate.minusDays(alertTime).compareTo(LocalDate.now())<=0)
            return true;
        return false;
    }

    public boolean isMinAmount() {
        return this.minAmount>=this.availableAmount;
    }

    public void SaleItem(int quantity) {
        if(shelfAmount >= quantity)
            this.shelfAmount=shelfAmount-quantity;
        else if(shelfAmount>0) {
                quantity = quantity - shelfAmount;
                shelfAmount = 0;
                storageAmount = storageAmount - quantity;
        }
        else
                storageAmount=storageAmount-quantity;
        this.availableAmount=this.shelfAmount+this.storageAmount;
        ItemsMapper.getInstance().updateItem(this);

        //@TODO: check if need to make an order (min quantity, etc..)

        //@TODO: nice to have: add a field that maintains if there are products that are expected to arrive
    }

    public int getCategoryID() {
        return categoryID;
    }

    //@TODO: functions that get the cheapest supplier id, and the cheapest id catalogue

    //@TODO: think of a solution how to overcome the expiration date field?
}

