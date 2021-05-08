package BusinessLayer.InventoryModule;

import BusinessLayer.Mappers.CategoryMapper;
import BusinessLayer.Mappers.ItemsMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private String name;
    private int id=-1;
    // check about itemDTO
    private HashMap<Integer, Item> items;
    private List<Category> subCategories;
    // can be null
    private Category fatherCategory;

    /**
     * Used when reading data from database
     * @param name
     * @param id
     */
    public Category(String name, int id,List<Item> items) {
        this.name = name;
        this.id = id;
        this.fatherCategory = null;
        this.subCategories = new ArrayList<>();
        this.items = new HashMap<>();
        if (items != null) {
            for (Item item : items)
                this.items.put(item.getId(), item);
        }
    }

    public Category(String name,Category fatherCategory){
        this.name=name;
        this.fatherCategory=fatherCategory;
        this.subCategories=new ArrayList<>();
        this.items=new HashMap<>();
    }

    public void addDiscount(double discount) {
        for (Map.Entry<Integer, Item> entry : this.items.entrySet()) {
            Item value = entry.getValue();
            value.setSellingPrice(value.getSellingPrice() - value.getSellingPrice() * discount / 100);
        }
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getFatherCategory() {
        return fatherCategory;
    }

    public void setFatherCategory(Category fatherCategory) {
        this.fatherCategory = fatherCategory;
    }

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }
    public Item addItem(int location, String name, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate,double sellingPrice) {
        if (storageAmount<0)
            throw new IllegalArgumentException("invalid storage amount");
        if (shelfAmount<0)
            throw new IllegalArgumentException("invalid shelf amount");
        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");

        if (expDate.compareTo(LocalDate.now())<0)
            throw new IllegalArgumentException("invalid exp date");

        Item toAdd = new Item(name,location,producer,storageAmount,shelfAmount,minAmount,expDate,sellingPrice,getID());
        this.items.put(toAdd.getId(), toAdd);
        return toAdd;
    }

    public void updateCategory(String name) {
        this.name=name;
        CategoryMapper.getInstance().updateCategory(this);
    }
    public void addSubCategory(Category toAdd){
        // go over sub categories and see if the category already exists
        for(Category cat : subCategories){
            if(cat.getID() == toAdd.getID())
                throw new IllegalArgumentException("Sub Category Already Exists");
        }
        this.subCategories.add(toAdd);
    }

    public boolean containsItem(int itemID)
    {
        return items.containsKey(itemID);
    }
    public boolean deleteItem(int itemID)
    {
        if(items.containsKey(itemID)) {
            items.remove(itemID);
        return true;
        }
        return false;
    }

    public void setID(int id) {
        if(this.id==-1)
            this.id=id;
    }
}
