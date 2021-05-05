package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Category;
import DTO.CategoryDTO;
import DataAccessLayer.CategoryDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryMapper {
    private static CategoryMapper instance=null;
    private HashMap<Integer, Category> categoryMapper;
    private CategoryDAO dao;

    private CategoryMapper(){
        categoryMapper=new HashMap<>();
        dao=new CategoryDAO();
    }

    public static CategoryMapper getInstance(){
        if(instance==null)
            instance=new CategoryMapper();
        return instance;
    }

    public Category buildCategory(CategoryDTO dto){
        Category cat=new Category(dto.name,dto.id,getCategory(dto.fatherCatID),getSubCategories(dto.categoriesIDS),ItemsMapper.getInstance().getItems(dto.itemIDS));
        categoryMapper.put(cat.getID(),cat);
        return cat;
    }

    /**
     * Adds a new category that was created in the business layer to the mapper, plus inserts it to the database
     * @param category
     */
    public int addCategory(Category category){
        int id=dao.insert(new CategoryDTO(category));
        categoryMapper.put(id,category);
        return id;
    }

    public void updateCategory(Category category){
        dao.update(new CategoryDTO(category));
    }

    private List<Category> getSubCategories(List<Integer> categoriesIDS) {
        ArrayList<Category> categories = new ArrayList<>();
        for (int id : categoriesIDS)
            categories.add(getCategory(id));
        return categories;
    }

    private Category getCategory(Integer categoryID) {
        if(categoryMapper.containsKey(categoryID))
            return categoryMapper.get(categoryID);
        CategoryDTO dto=dao.get(categoryID);
        if(dto==null)
            throw new IllegalArgumentException("No such category in database");
        return buildCategory(dto);
    }
}
