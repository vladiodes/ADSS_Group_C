package BusinessLayer;

import DTO.CategoryDTO;
import DTO.ItemDTO;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Category {
    private String name;
    private int id;
    // check about itemDTO
    private HashMap<Integer, Item> items;
    private List<Category> subCategories;
    private Category fatherCategory;

    public Category(String name,int id){
        this.name=name;
        this.id=id;

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
    public Item addItem(int location, String producer, int availableAmount, int storageAmount, int shelfAmount, int minAmount, LocalDateTime expDate,int itemID,double buyingPrice) {
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

        Item toAdd = new Item(itemID,location,producer,availableAmount,storageAmount,shelfAmount,minAmount,expDate,buyingPrice);
        this.items.put(itemID, toAdd);
        return toAdd;
    }

    public void updateCategory(CategoryDTO categoryDTO) {
        this.name=categoryDTO.getName();
        this.items=categoryDTO.getItems();
        this.fatherCategory=categoryDTO.getFatherCategory();
        this.subCategories=categoryDTO.getSubCategories();
    }
}
