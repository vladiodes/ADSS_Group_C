package BusinessLayer;

import DTO.CategoryDTO;
import DTO.ItemDTO;

import java.time.LocalDateTime;
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

    public Category(String name,int id,Category fatherCategory){
        this.name=name;
        this.id=id;
        this.fatherCategory=fatherCategory;
        this.subCategories=new ArrayList<>();

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
            value.setBuyingPrice(value.getBuyingPrice() - value.getBuyingPrice() * discount / 100);

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
    public Item addItem(int location,String name, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,int itemID,double buyingPrice) {
        if (availableAmount<0)
            throw new IllegalArgumentException("invalid available amount");
        if (storageAmount<0)
            throw new IllegalArgumentException("invalid storage amount");
        if (shelfAmount<0)
            throw new IllegalArgumentException("invalid shelf amount");
        if (minAmount<0)
            throw new IllegalArgumentException("invalid minimum amount");
        if (expDate.compareTo(LocalDateTime.now())<0)
            throw new IllegalArgumentException("invalid exp date");
        if (buyingPrice<0)
            throw new IllegalArgumentException("invalid buying price");

        Item toAdd = new Item(itemID,name,location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);
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
}
