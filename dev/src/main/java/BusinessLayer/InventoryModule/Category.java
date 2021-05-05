package BusinessLayer.InventoryModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private String name;
    private int id;
    // check about itemDTO
    private HashMap<Integer, Item> items;
    private List<Category> subCategories;
    // can be null
    private Category fatherCategory;

    /**
     * Used when reading data from database
     * @param name
     * @param id
     * @param fatherCategory
     * @param subCategories
     */
    public Category(String name, int id,Category fatherCategory,List<Category> subCategories,List<Item> items){
        this.name=name;
        this.id=id;
        this.fatherCategory=fatherCategory;
        this.subCategories=subCategories;
        for(Item item:items)
            this.items.put(item.getId(),item);
    }

    public Category(String name,int id,Category fatherCategory){
        this.name=name;
        this.id=id;
        this.fatherCategory=fatherCategory;
        this.subCategories=new ArrayList<>();
        this.items=new HashMap<>();

    }

    @Override
    public String toString() {
        return "category{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", items=" + items +
                ", subCategories=" + subCategories +
                ", fatherCategory=" + fatherCategory +
                '}';
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

    public HashMap<Integer, Item> getItems() {
        return items;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }
    public Item addItem(int location, String name, String producer, int storageAmount, int shelfAmount, int minAmount, LocalDate expDate, int itemID, double buyingPrice,double sellingPrice) {
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

        //@TODO: buying price should be calculated by system and not passed as an argument
        Item toAdd = new Item(itemID,name,location,producer,storageAmount,shelfAmount,minAmount,expDate,sellingPrice,getID());
        this.items.put(itemID, toAdd);
        return toAdd;
    }
    public List<String> getItemNames()
    {
        List<String> toReturn = new ArrayList<>();
        for(Item item : this.items.values())
        {
            toReturn.add(item.getName());
        }
        return toReturn;
    }

    public void updateCategory(String name) {
        this.name=name;
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
    public void deleteItem(int itemID)
    {
        if(items.containsKey(itemID))
            items.remove(itemID);
        else
            throw new IllegalArgumentException("Item does not exists in this category");
    }
}
