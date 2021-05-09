package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.ItemsMapper;
import BusinessLayer.SuppliersModule.Contract;
import DTO.ItemDTO;
import DTO.specificItemDTO;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Item {
    // -- fields
    private int id;
    private String name;
    private List<SpecificItem> items;
    private int minAmount;
    private List<Contract> contractList;
    private int alertTime;
    private double sellingPrice;
    private int location;
    private String producer;
    private int categoryID; //for database



    // -- constructor

    public Item(ItemDTO dto){
        items=new ArrayList<>();
        this.name=dto.getName();
        this.location=dto.getLocation();
        this.producer=dto.getProducer();
        this.minAmount=dto.getMinAmount();
        this.alertTime=dto.getAlertTime();
        this.sellingPrice=dto.getSellingPrice();
        this.categoryID=dto.getCategoryID();
        this.id=dto.getID();
        for(specificItemDTO specific:dto.getSpecificItemDTOList()){
            items.add(new SpecificItem(specific));
        }
        contractList=new ArrayList<>();
    }
    public Item(String name, int location, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate,double sellingPrice,int categoryID){
        this.name=name;
        this.minAmount=minAmount;
        this.alertTime=2;
        this.sellingPrice = sellingPrice;
        this.contractList = new ArrayList<>();
        this.producer=producer;
        this.categoryID=categoryID;
        this.location=location;
        items = new ArrayList<>();
        SpecificItem item = new SpecificItem(storageAmount,shelfAmount,expDate,getId());
        items.add(item);
        id=ItemsMapper.getInstance().addItem(this);
        item.setId(ItemsMapper.getInstance().addSpecificItem(this,item));
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
        ItemsMapper.getInstance().updateItem(this);
    }
    public void updateItem(String name, int minAmount,double sellingPrice,int location,String producer)
    {
        if(name == null || name == "")
            throw new IllegalArgumentException("invalid item name");
        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");
        if (sellingPrice<0)
            throw new IllegalArgumentException("invalid selling price");
        this.name = name;
        this.minAmount=minAmount;
        this.sellingPrice=sellingPrice;
        this.producer=producer;
        this.location=location;
        ItemsMapper.getInstance().updateItem(this);
    }

    public void addDiscount(double discount) {
        setSellingPrice(this.sellingPrice - this.sellingPrice*discount/100);
    }
    public void addSpecificItem(int storageAmount, int shelfAmount, LocalDate expDate)
    {
        SpecificItem newItem = new SpecificItem(storageAmount,shelfAmount,expDate,getId());
        this.items.add(newItem);
        newItem.setId(ItemsMapper.getInstance().addSpecificItem(this,newItem));
    }
    public int getAlertTime() {
        return this.alertTime;
    }

    public void setAlertTime(int alertTime) {
        if(alertTime < 0)
            throw new IllegalArgumentException("invalid alert time");
        this.alertTime=alertTime;
        ItemsMapper.getInstance().updateItem(this);
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
            if(quantity>item.getShelfAmount())
            {
                quantity-=item.getShelfAmount();
                item.setShelfAmount(0);
            }
            else {
                item.setShelfAmount(item.getShelfAmount()-quantity);
                break;
            }
            if(quantity>item.getStorageAmount()){
                quantity-=item.getStorageAmount();
                item.setStorageAmount(0);
            }
            else {
                item.setStorageAmount(item.getStorageAmount()-quantity);
                break;
            }
        }
        return this.getAmount() < minAmount;
    }

    public void removeFaultyItems()
    {
        for(int i=0;i<items.size();i++)
        {
            SpecificItem item = items.get(i);
            if(item.getExpDate().compareTo(LocalDate.now()) <= 0) {
                items.remove(item);
                ItemsMapper.getInstance().deleteSpecificItem(this, item);
            }
        }
    }

    public void addContract(Contract contract) {
        if (contract == null)
            throw new IllegalArgumentException("Contract can't be null");
        if (!contractList.contains(contract))
            this.contractList.add(contract);
    }

    /**
     *
     * @return returns a pair [supplierID,catalogueID] - this pair represents the cheapest supplier and the catalogue id as
     * appears in the contract
     */
    public Pair<Integer, Integer> getCheapestSupplier()
    {
        int minID = -1;
        int minCatalogueID = -1;
        double minPrice =-1;
        if(!contractList.isEmpty())
        {
            minID = contractList.get(0).getSupplierID();
            minPrice = contractList.get(0).getPricePerUnit();
            minCatalogueID = contractList.get(0).getCatalogueIDBySupplier();
        }
        for(Contract c : contractList)
        {
            if(c.getPricePerUnit() < minPrice)
            {
                minID = c.getSupplierID();
                minPrice = c.getPricePerUnit();
                minCatalogueID = c.getCatalogueIDBySupplier();
            }
        }
        Pair<Integer,Integer> ans = new Pair<>(minID,minCatalogueID);
        return ans;
    }

    public List<SpecificItem> getSpecificItems() {
        return this.items;
    }

    public double getBuyingPrice() {
        double buyingPrice = Integer.MAX_VALUE;
        for (Contract c : contractList)
            buyingPrice = Math.min(c.getPricePerUnit(), buyingPrice);
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
        ItemsMapper.getInstance().updateItem(this);
    }

    public void removeContract(Contract contract) {
        contractList.remove(contract);
    }

    public int getLocation() {
        return location;
    }

    public String getProducer() {
        return producer;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public List<Contract> getContractList() {
        return contractList;
    }
}