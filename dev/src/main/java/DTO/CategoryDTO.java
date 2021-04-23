package DTO;

import BusinessLayer.InventoryModule.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDTO {
    private String name;
    private int id;
    // check about itemDTO
    private List<String> items;
    private List<String> subCategories;
    private String fatherCategory;

    public CategoryDTO(Category c){
        this.name=c.getName();
        this.id=c.getID();
        this.subCategories = new ArrayList<>();
        // change OBJECTS to DTO
        for(Category cat : c.getSubCategories())
        {
            this.subCategories.add(cat.getName());
        }
        if (c.getFatherCategory()!=null)
            this.fatherCategory=c.getFatherCategory().getName();
        else
            this.fatherCategory=null;
        this.items = new ArrayList<>();
        for(String name : c.getItemNames())
        {
            this.items.add(name);
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

    public Integer getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public String getFatherCategory() {
        return fatherCategory;
    }

    public List<String> getItems() {
        return items;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }

}
