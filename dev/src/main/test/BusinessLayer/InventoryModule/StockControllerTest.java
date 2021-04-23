
package BusinessLayer.InventoryModule;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class StockControllerTest {
    private StockController stockController;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
         stockController = StockController.getInstance();

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        this.stockController.clear();
    }

    @org.junit.jupiter.api.Test
    void addItemFindByLocation() {
        try {
            this.stockController.addCategory("milk", 0);
            this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 05, 02), 1, 02, 08.50);
            assertTrue(this.stockController.getItemByLocation(2) != null);
        }
        catch (Exception e)
        {
            fail("should have added the item");
        }
    }
    @org.junit.jupiter.api.Test
    void addItemFindByID() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Item item = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 05, 02), cat.getID(), 02, 08.50);
            assertEquals(this.stockController.getItemById(item.getId()).getName() , "shoko");
        }
        catch (Exception e)
        {
            fail("should have updated the item with the same name");
        }
    }

    @org.junit.jupiter.api.Test
    void updateItem() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Item item = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 05, 02), cat.getID(), 02, 08.50);
            this.stockController.updateItem(item.getId(), "okdk*", item.getLocation(), item.getProducer(), item.getStorageAmount(), item.getShelfAmount(), item.getMinAmount(), item.getExpDate(), item.getBuyingPrice(), item.getSellingPrice());
            assertEquals(item.getName(), "okdk*");
        }
        catch (Exception e){
            fail("should have update item name");
        }
    }


    @org.junit.jupiter.api.Test
    void updateCategory() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            this.stockController.updateCategory(cat.getID(),"okdk*");
            assertEquals(cat.getName(),"okdk*");
        }
        catch (Exception e){
            fail("should have update category name");
        }
    }

    @org.junit.jupiter.api.Test
    void addCategoryDiscount() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Item item1 = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 05, 02), cat.getID(), 02, 10);
            Item item2 = this.stockController.addItem(3,"chocolate","tnuva",200,20,10,LocalDate.of(2024,07,25),cat.getID(),20,25);
            this.stockController.addCategoryDiscount(cat.getID(),20);
            assertEquals(item1.getSellingPrice(), 8);
            assertEquals(item2.getSellingPrice(), 20);
        }
        catch (Exception e){
            fail("should have update items prices");
        }
    }

    @org.junit.jupiter.api.Test
    void addItemDiscount() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Item item1 = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 05, 02), cat.getID(), 02, 10);
            this.stockController.addItemDiscount(item1.getId(),20);
            assertEquals(item1.getSellingPrice(), 8);
        }
        catch (Exception e){
            fail("should have update item price");
        }
    }

    @org.junit.jupiter.api.Test
    void addCategory() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Category cat1 = this.stockController.addCategory("Drinks", 0);
            Category cat2 = this.stockController.addCategory("Alcohol", cat1.getID());

            List<Category> categories = this.stockController.getAllCategories();
            ArrayList<Category> toCompare = new ArrayList<>();
            toCompare.add(cat);
            toCompare.add(cat1);
            toCompare.add(cat2);
            assertEquals(categories,toCompare);
        }
        catch (Exception e){
            fail("should have added all three categories");
        }
    }

    @org.junit.jupiter.api.Test
    void addCategoryFailBadFatherID() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Category cat1 = this.stockController.addCategory("Drinks", -1);
            Category cat2 = this.stockController.addCategory("Alcohol", cat1.getID());

            List<Category> categories = this.stockController.getAllCategories();
            ArrayList<Category> toCompare = new ArrayList<>();
            toCompare.add(cat);
            toCompare.add(cat1);
            toCompare.add(cat2);
            fail("should not have added cat1 (bad fatherID)");

        }
        catch (Exception e){
            assertTrue(true);
        }
    }
    @org.junit.jupiter.api.Test
    void addCategoryFailNotExistingFatherID() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Category cat1 = this.stockController.addCategory("Drinks", 13);
            Category cat2 = this.stockController.addCategory("Alcohol", cat1.getID());

            List<Category> categories = this.stockController.getAllCategories();
            ArrayList<Category> toCompare = new ArrayList<>();
            toCompare.add(cat);
            toCompare.add(cat1);
            toCompare.add(cat2);
            fail("should not have added cat1 (Father id not existing)");

        }
        catch (Exception e){
            assertTrue(true);
        }
    }

    @org.junit.jupiter.api.Test
    void deleteItem() {
        try {
            Category cat = this.stockController.addCategory("milk", 0);
            Item item = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 05, 02), cat.getID(), 02, 08.50);
            this.stockController.deleteItem(item.getId());
            assertEquals(cat.getItems().size(), 0);
        }
        catch (Exception e){
            fail("should have deleted the item");
        }
    }
}