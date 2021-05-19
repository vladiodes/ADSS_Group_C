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
        if(dto==null)
            return null;
        Category cat=new Category(dto.name,dto.id,ItemsMapper.getInstance().getItems(dto.itemIDS));
        categoryMapper.put(cat.getID(),cat);
        cat.setFatherCategory(getCategory(dto.fatherCatID));
        for(Category sub:getSubCategories(dto.categoriesIDS))
            cat.addSubCategory(sub);
        return cat;
    }

    /**
     * Adds a new category that was created in the business layer to the mapper, plus inserts it to the database
     * @param category
     */
    public int addCategory(Category category){
        int id=dao.insert(new CategoryDTO(category));
        categoryMapper.put(id,category);
        category.setID(id);
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

    public Category getCategory(Integer categoryID) {
        if(categoryID<=0)
            return null;
        if (categoryMapper.containsKey(categoryID))
            return categoryMapper.get(categoryID);
        CategoryDTO dto = dao.get(categoryID);
        return buildCategory(dto);
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> output=new ArrayList<>();
        for(CategoryDTO dto:dao.getAllCategories()){
            output.add(buildCategory(dto));
        }
        return output;
    }

    public HashMap<Integer, Category> getCategoryMapper() {
        return categoryMapper;
    }
}
