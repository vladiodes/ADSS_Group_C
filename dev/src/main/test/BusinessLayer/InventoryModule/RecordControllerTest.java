//package BusinessLayer.InventoryModule;
//
//import BusinessLayer.InventoryModule.*;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class RecordControllerTest {
//    private RecordController recordController;
//    private StockController stockController;
//    @BeforeEach
//    void setUp() {
//        this.recordController=RecordController.getInstance();
//        this.stockController=StockController.getInstance();
//    }
//
//    @AfterEach
//    void tearDown() {
//    }
//
///*
//    @Test
//    void showFaultyItems() {
//        try{
//            ArrayList<Category> categories = new ArrayList();
//            Category cat = new Category("meat",1,null);
//            Item item1 = cat.addItem(202,"chicken","osem",200,200,50, LocalDate.of(2021,4,9),28,30,40);
//            Item item2 = cat.addItem(255,"burger","elit",300,200,50, LocalDate.of(2021,4,9),30,60,80);
//            categories.add(cat);
//            ArrayList<Item> toCompare = new ArrayList<>();
//            toCompare.add(item1);
//            toCompare.add(item2);
//
//            Report report = this.recordController.showFaultyItems(categories);
//            assertEquals(report.getItems(),toCompare);
//        }
//        catch (Exception e){
//            fail("should show two items");
//        }
//    }
//
//
//
// */
//    @Test
//    void getWeeklyReport() {
//        try{
//            ArrayList<Category> categories = new ArrayList();
//            Category cat = new Category("meat",null);
//            Item item1 = cat.addItem(202,"chicken","osem",200,200,50, LocalDate.now(),28,30);
//            Item item2 = cat.addItem(255,"burger","elit",300,200,50, LocalDate.now(),30,60);
//            categories.add(cat);
//            ArrayList<Item> toCompare = new ArrayList<>();
//            toCompare.add(item1);
//            toCompare.add(item2);
//
//            Report report = this.recordController.getWeeklyReport(categories);
//            assertEquals(report.getItems(),toCompare);
//        }
//        catch (Exception e){
//            fail("should show two items");
//        }
//    }
//
//
//    @Test
//    void showMinAmountItems() {
//        try
//        {
//        ArrayList<Category> categories = new ArrayList();
//        Category cat = new Category("meat",null);
//        Item item1 = cat.addItem(202,"chicken","osem",1,0,50, LocalDate.now(),28,30);
//        Item item2 = cat.addItem(255,"burger","elit",300,1,50, LocalDate.now(),30,60);
//        categories.add(cat);
//        ArrayList<Item> toCompare = new ArrayList<>();
//        toCompare.add(item1);
//        Report report = this.recordController.showMinAmountItems(categories);
//        assertEquals(report.getItems(),toCompare);
//    }
//        catch (Exception e){
//        fail("should show two items");
//    }
//    }
//
//    @Test
//    void getSalesReportAndAddSale() {
//        try
//        {
//            ArrayList<Category> categories = new ArrayList();
//            Category cat = new Category("meat",null);
//            Item item1 = cat.addItem(202,"chicken","osem",100,0,50, LocalDate.now(),28,30);
//            Item item2 = cat.addItem(255,"burger","elit",300,1,50, LocalDate.now(),30,60);
//            categories.add(cat);
//            ArrayList<Sale> toCompare = new ArrayList<>();
//            Sale sale1 = this.recordController.addSale(item1,50);
//            Sale sale2 = this.recordController.addSale(item2,50);
//            Sale sale3 = this.recordController.addSale(item1,50);
//
//            toCompare.add(sale1);
//            toCompare.add(sale2);
//            toCompare.add(sale3);
//            SaleReport report = this.recordController.getSalesReport(categories,LocalDate.of(2020,04,19),LocalDate.now().plusDays(1));
//            assertEquals(report.getSales(),toCompare);
//        }
//        catch (Exception e){
//            fail("should show two items");
//        }
//    }
//}