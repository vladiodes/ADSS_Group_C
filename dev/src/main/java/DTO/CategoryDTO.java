package DTO;

import BusinessLayer.Category;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class CategoryDTO {
    private String name;
    private int id;
    // check about itemDTO
    private HashMap<Integer,ItemDTO> items;
    private List<CategoryDTO> subCategories;
    private CategoryDTO fatherCategory;

    public CategoryDTO(Category c){
        this.name=c.getName();
        this.id=c.getID();
        // change OBJECTS to DTO
        this.subCategories=c.getSubCategories();
        this.fatherCategory=c.getFatherCategory();
        this.items=c.getItems();
    }

    @Override
    public String toString() {
        return "categoryDTO{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", items=" + items +
                ", subCategories=" + subCategories +
                ", fatherCategory=" + fatherCategory +
                '}';
    }

    public Integer getID() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public CategoryDTO getFatherCategory() {
        return fatherCategory;
    }

    public HashMap<Integer, ItemDTO> getItems() {
        return items;
    }

    public List<CategoryDTO> getSubCategories() {
        return subCategories;
    }

}
