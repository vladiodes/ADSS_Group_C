package DTO;

import BusinessLayer.InventoryModule.Category;
import BusinessLayer.InventoryModule.Item;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    public String name;
    public int id;

    //=============Those fields are when reading data from the database===========//
    public List<Integer> itemIDS;
    public List<Integer> categoriesIDS;
    //===========================================================================//

    public List<String> items;
    public List<String> subCategories;
    public String fatherCategory;
    public Integer fatherCatID;

    public CategoryDTO(Integer id,String name, Integer fatherCatID){
        this.fatherCatID=fatherCatID;
        this.id=id;
        this.name=name;
    }
    public CategoryDTO(Category c){
        itemIDS=new ArrayList<>();
        categoriesIDS=new ArrayList<>();
        this.name=c.getName();
        this.id=c.getID();
        this.subCategories = new ArrayList<>();
        // change OBJECTS to DTO
        for(Category cat : c.getSubCategories())
        {
            this.subCategories.add(cat.getName());
            categoriesIDS.add(cat.getID());
        }
        if (c.getFatherCategory()!=null) {
            fatherCatID=c.getFatherCategory().getID();
            this.fatherCategory = c.getFatherCategory().getName();
        }
        else {
            fatherCatID = null;
            this.fatherCategory = null;
        }
        this.items = new ArrayList<>();
        for(Item item: c.getItems().values())
        {
            items.add(item.getName());
            itemIDS.add(item.getId());
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Category ID: " + this.id + "\n");
        builder.append("Category Name: " + this.name + "\n");
        if(this.fatherCategory != null)
            builder.append("Category Father Category: "+ this.fatherCategory + "\n");
        else
            builder.append("Category Has No Father Category\n");
        if(this.subCategories.size() > 0)
        {
            builder.append("Category Sub Categories:\n");
            for(String catName : this.subCategories){
                builder.append(catName + " ");
            }
        }
        else
            builder.append("Category Has No Sub Categories\n");
        if(this.items.size() > 0)
        {
            builder.append("\nCategory Items:\n");
            for(String itemName : items){
                builder.append(itemName +" ");
            }
        }
        else
            builder.append("Category Has No Items\n");
        return builder.toString();
    }
}
