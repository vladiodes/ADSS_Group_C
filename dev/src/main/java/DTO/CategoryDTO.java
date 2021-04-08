package DTO;

import BusinessLayer.Category;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
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
        this.fatherCategory=c.getFatherCategory().getName();
        this.items = new ArrayList<>();
        for(String name : c.getItemNames())
        {
            this.items.add(name);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Category Name:" + this.name + "\n");
        builder.append("Category Father Category: "+ this.fatherCategory + "\n");
        builder.append("Category Sub Categories:\n");
        for(String catName : this.subCategories){
            builder.append(catName + " ");
        }
        builder.append("\nCategory Items:\n");
        for(String itemName : items){
            builder.append(itemName +" ");
        }
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
