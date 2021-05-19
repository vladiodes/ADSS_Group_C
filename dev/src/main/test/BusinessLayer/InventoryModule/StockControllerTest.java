//package BusinessLayer.InventoryModule;
//
//import BusinessLayer.SuppliersModule.*;
//import DTO.SupplierDTO;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class StockControllerTest {
//    private StockController stockController;
//    private RecordController recordController;
//    private Contract contract;
//    private Supplier supplier;
//    private ProductInOrder pio;
//    private Order order;
//
//
//
//    @org.junit.jupiter.api.BeforeEach
//    void setUp() {
//        order=new Order(LocalDateTime.now(),false,1);
//
//        stockController = StockController.getInstance();
//        this.recordController=RecordController.getInstance();
//        supplier=new Supplier(0,"supplier1",new HashSet<>(),true,"bank account", PaymentAgreement.Monthly,new HashSet<>(),new HashSet<>(),new HashMap<>(),new HashMap<>());
//        Item p=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Contract c=new Contract(100.0,supplier,1,new HashMap<>(),p);
//        pio=new ProductInOrder(100,c);
//        contract=new Contract(100.0,supplier,1,new HashMap<>(),new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2));
//
//    }
//
//    @org.junit.jupiter.api.AfterEach
//    void tearDown() {
//        this.stockController.clear();
//        this.recordController.clear();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addItemToDeliveredOrder(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//        Contract c=new Contract(100,supplier,1,new HashMap<>(),product);
//        order.receive();
//        try {
//            order.addItem(c,10,new HashMap<>());
//        }
//        catch (IllegalArgumentException e)
//        {
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addItemTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//        Contract c=new Contract(100,supplier,1,new HashMap<>(),product);
//        order.addItem(c,10,new HashMap<>());
//        assertEquals(1, order.getProductsInOrder().size());
//        assertEquals(10, order.getTotalQuantity());
//        assertEquals(1000.0, order.getPriceAfterDiscount(), 0.0);
//        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addItemWithDiscountsTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        HashMap<Double,Integer>discounts=new HashMap<>();
//        discounts.put(500.0,10);
//        Contract c=new Contract(100,supplier,1,new HashMap<>(),product);
//        order.addItem(c,10,discounts);
//        assertEquals(1, order.getProductsInOrder().size());
//        assertEquals(10, order.getTotalQuantity());
//        assertEquals(900.0, order.getPriceAfterDiscount(), 0.0);
//        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addItemWith2DiscountsTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        HashMap<Double,Integer>discounts=new HashMap<>();
//        discounts.put(500.0,10);
//        discounts.put(600.0,20);
//        Contract c=new Contract(100,supplier,1,new HashMap<>(),product);
//        order.addItem(c,10,discounts);
//        assertEquals(1, order.getProductsInOrder().size());
//        assertEquals(10, order.getTotalQuantity());
//        assertEquals(800.0, order.getPriceAfterDiscount(), 0.0);
//        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addItemWithDiscountsNotExceedingPriceTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        HashMap<Double,Integer>discounts=new HashMap<>();
//        discounts.put(5000.0,10);
//        discounts.put(6000.0,20);
//        Contract c=new Contract(100,supplier,1,new HashMap<>(),product);
//        order.addItem(c,10,discounts);
//        assertEquals(1, order.getProductsInOrder().size());
//        assertEquals(10, order.getTotalQuantity());
//        assertEquals(1000.0, order.getPriceAfterDiscount(), 0.0);
//        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
//    }
//
//    @org.junit.jupiter.api.Test
//    public void receiveOrderTest(){
//        order.receive();
//        assertSame(order.getShipmentStatus(), Order.ShipmentStatus.Delivered);
//        try {
//            order.receive();
//        }
//        catch (IllegalArgumentException e)
//        {
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeProductFromDeliveredOrderTest() {
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Contract c = new Contract(100,supplier, 1, new HashMap<>(), product);
//        order.addItem(c, 10, new HashMap<>());
//        order.receive();
//        try {
//            order.removeProduct(c, new HashMap<>());
//        } catch (IllegalArgumentException e) {
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeProductTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Contract c = new Contract(100,supplier, 1, new HashMap<>(), product);
//        order.addItem(c,10,new HashMap<>());
//        order.removeProduct(c,new HashMap<>());
//        assertEquals(0, order.getTotalQuantity());
//        assertEquals(0, order.getProductsInOrder().size());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void checkIfProductExistsTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Item product2=new Item(2,"product2",2,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Contract c = new Contract(100, supplier,1, new HashMap<>(), product);
//        order.addItem(c,10,new HashMap<>());
//        assertTrue(order.checkIfProductExists(product));
//        assertFalse(order.checkIfProductExists(product2));
//    }
//
//    @org.junit.jupiter.api.Test
//    public void reOrderTest(){
//        Order order1=new Order(LocalDateTime.now(),true,10);
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Contract c = new Contract(100,supplier, 1, new HashMap<>(), product);
//        order1.addItem(c,10,new HashMap<>());
//        Order order2=new Order(order1,11,LocalDateTime.now());
//        assertEquals(1, order2.getProductsInOrder().size());
//        order1.removeProduct(c,new HashMap<>());
//        assertEquals(1, order2.getProductsInOrder().size());
//    }
//
//
//    @org.junit.jupiter.api.Test
//    public void testOrderMore(){
//        pio.orderMore(10);
//        assertEquals(pio.getQuantity(),110);
//        assertEquals(pio.getTotalPrice(),100*110,0.0);
//    }
//    @org.junit.jupiter.api.Test
//    void addItemFindByLocation() {
//        try {
//            this.stockController.addCategory("milk", 0);
//            this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 06, 02), 1, 02, 08.50);
//            assertTrue(this.stockController.getItemByLocation(2) != null);
//        }
//        catch (Exception e)
//        {
//            fail("should have added the item");
//        }
//    }
//    @org.junit.jupiter.api.Test
//    void addItemFindByID() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Item item = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 06, 02), cat.getID(), 02, 08.50);
//            assertEquals(this.stockController.getItemById(item.getId()).getName() , "shoko");
//        }
//        catch (Exception e)
//        {
//            fail("should have updated the item with the same name");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void updateItem() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Item item = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 06, 02), cat.getID(), 02, 08.50);
//            this.stockController.updateItem(item.getId(), "okdk*", item.getMinAmount(),  item.getBuyingPrice(), item.getSellingPrice());
//            assertEquals(item.getName(), "okdk*");
//        }
//        catch (Exception e){
//            fail("should have update item name");
//        }
//    }
//
//
//    @org.junit.jupiter.api.Test
//    void updateCategory() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            this.stockController.updateCategory(cat.getID(),"okdk*");
//            assertEquals(cat.getName(),"okdk*");
//        }
//        catch (Exception e){
//            fail("should have update category name");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void addCategoryDiscount() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Item item1 = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 06, 02), cat.getID(), 02, 10);
//            Item item2 = this.stockController.addItem(3,"chocolate","tnuva",200,20,10,LocalDate.of(2024,07,25),cat.getID(),20,25);
//            this.stockController.addCategoryDiscount(cat.getID(),20);
//            assertEquals(item1.getSellingPrice(), 8);
//            assertEquals(item2.getSellingPrice(), 20);
//        }
//        catch (Exception e){
//            fail("should have update items prices");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void addItemDiscount() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Item item1 = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 06, 02), cat.getID(), 02, 10);
//            this.stockController.addItemDiscount(item1.getId(),20);
//            assertEquals(item1.getSellingPrice(), 8);
//        }
//        catch (Exception e){
//            fail("should have update item price");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void addCategory() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Category cat1 = this.stockController.addCategory("Drinks", 0);
//            Category cat2 = this.stockController.addCategory("Alcohol", cat1.getID());
//
//            List<Category> categories = this.stockController.getAllCategories();
//            ArrayList<Category> toCompare = new ArrayList<>();
//            toCompare.add(cat);
//            toCompare.add(cat1);
//            toCompare.add(cat2);
//            assertEquals(categories,toCompare);
//        }
//        catch (Exception e){
//            fail("should have added all three categories");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void addCategoryFailBadFatherID() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Category cat1 = this.stockController.addCategory("Drinks", -1);
//            Category cat2 = this.stockController.addCategory("Alcohol", cat1.getID());
//
//            List<Category> categories = this.stockController.getAllCategories();
//            ArrayList<Category> toCompare = new ArrayList<>();
//            toCompare.add(cat);
//            toCompare.add(cat1);
//            toCompare.add(cat2);
//            fail("should not have added cat1 (bad fatherID)");
//
//        }
//        catch (Exception e){
//            assertTrue(true);
//        }
//    }
//    @org.junit.jupiter.api.Test
//    void addCategoryFailNotExistingFatherID() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Category cat1 = this.stockController.addCategory("Drinks", 13);
//            Category cat2 = this.stockController.addCategory("Alcohol", cat1.getID());
//
//            List<Category> categories = this.stockController.getAllCategories();
//            ArrayList<Category> toCompare = new ArrayList<>();
//            toCompare.add(cat);
//            toCompare.add(cat1);
//            toCompare.add(cat2);
//            fail("should not have added cat1 (Father id not existing)");
//
//        }
//        catch (Exception e){
//            assertTrue(true);
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void deleteItem() {
//        try {
//            Category cat = this.stockController.addCategory("milk", 0);
//            Item item = this.stockController.addItem(2, "shoko", "tnuva", 100, 200, 50, LocalDate.of(2021, 06, 02), cat.getID(), 02, 08.50);
//            this.stockController.deleteItem(item.getId());
//            assertEquals(cat.getItems().size(), 0);
//        }
//        catch (Exception e){
//            fail("should have deleted the item");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void getWeeklyReport() {
//        try{
//            ArrayList<Category> categories = new ArrayList();
//            Category cat = new Category("meat",1,null);
//            Item item1 = cat.addItem(202,"chicken","osem",200,200,50, LocalDate.now(),28,30,40);
//            Item item2 = cat.addItem(255,"burger","elit",300,200,50, LocalDate.now(),30,60,80);
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
//    @org.junit.jupiter.api.Test
//    void showMinAmountItems() {
//        try
//        {
//            ArrayList<Category> categories = new ArrayList();
//            Category cat = new Category("meat",1,null);
//            Item item1 = cat.addItem(202,"chicken","osem",1,0,50, LocalDate.now(),28,30,40);
//            Item item2 = cat.addItem(255,"burger","elit",300,1,50, LocalDate.now(),30,60,80);
//            categories.add(cat);
//            ArrayList<Item> toCompare = new ArrayList<>();
//            toCompare.add(item1);
//            Report report = this.recordController.showMinAmountItems(categories);
//            assertEquals(report.getItems(),toCompare);
//        }
//        catch (Exception e){
//            fail("should show two items");
//        }
//    }
//
//    @org.junit.jupiter.api.Test
//    void getSalesReportAndAddSale() {
//        try
//        {
//            ArrayList<Category> categories = new ArrayList();
//            Category cat = new Category("meat",1,null);
//            Item item1 = cat.addItem(202,"chicken","osem",100,0,50, LocalDate.now(),28,30,40);
//            Item item2 = cat.addItem(255,"burger","elit",300,1,50, LocalDate.now(),30,60,80);
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
//    @org.junit.jupiter.api.Test
//    public void testAddDiscount(){
//        contract.addDiscount(100,10);
//        assertEquals(10, (int) contract.getDiscountByQuantity().get(100));
//        assertEquals(1, contract.getDiscountByQuantity().size());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void testAddDiscount2(){
//        try {
//            contract.addDiscount(-100,10);
//        }
//        catch (IllegalArgumentException e){
//            assertEquals(0, contract.getDiscountByQuantity().size());
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void testAddDiscount3() {
//        contract.addDiscount(100, 10);
//        try {
//            contract.addDiscount(100, 15);
//        } catch (IllegalArgumentException e) {
//            assertEquals(1, contract.getDiscountByQuantity().size());
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void testAddDiscountAbove100(){
//        try {
//            contract.addDiscount(100, 101);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//    @org.junit.jupiter.api.Test
//    public void testUpdate(){
//        SupplierDTO dto=new SupplierDTO(supplier);
//        dto.supplierName="supplier2";
//        dto.bankAccount="bank account2";
//        dto.selfPickUp=false;
//        HashSet<DayOfWeek> set=new HashSet<>();
//        set.add(DayOfWeek.Monday);
//        dto.fixedDays=set;
//        supplier.updateSupplier(dto);
//        assertEquals("supplier2",supplier.getSupplierName());
//        assertEquals("bank account2",supplier.getBankAccount());
//        assertFalse(supplier.isSelfPickUp());
//        assertEquals(supplier.getFixedDays().size(),1);
//        assertTrue(supplier.getFixedDays().contains(DayOfWeek.Monday));
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addDiscountNegativePriceTest() {
//        try {
//            supplier.addDiscount(-5, 10);
//        } catch (IllegalArgumentException e) {
//            assertTrue(true);
//            return;
//        }
//        fail("Should throw an exception");
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addDiscountAbove100(){
//        try {
//            supplier.addDiscount(100,101);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addDiscountNegativeDiscountTest(){
//        try {
//            supplier.addDiscount(150,-12);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail("Should throw an exception");
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addExistingDiscount(){
//        supplier.addDiscount(150.0,10);
//        assertTrue(supplier.getDiscountsByPrice().get(150.0)==10);
//        try {
//            supplier.addDiscount(150.0,12);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail("Should throw an exception");
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addOrderGetOrderTest(){
//        supplier.addOrder(LocalDateTime.now(),true,1);
//        assertNotNull(supplier.getOrder(1));
//        try {
//            supplier.getOrder(2);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail("Should throw an exception");
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addOrderSameIDTest(){
//        supplier.addOrder(LocalDateTime.now(),true,1);
//        try {
//            supplier.addOrder(LocalDateTime.now(),false,1);
//        }
//        catch (IllegalArgumentException e)
//        {
//            assertTrue(true);
//            return;
//        }
//        fail("Shouldn't allow adding 2 orders with same id");
//    }
//
//    @org.junit.jupiter.api.Test
//    public void getOrdersTest(){
//        supplier.addOrder(LocalDateTime.now(),true,1);
//        supplier.addOrder(LocalDateTime.now(),true,2);
//        assertEquals(2, supplier.getOrders().size());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addContractTest(){
//        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product,1,150.0,new HashMap<>());
//        assertEquals(1, supplier.getSupplierContracts().size());
//        try {
//            supplier.addContract(null,2,150.0,new HashMap<>());
//        }
//        catch (IllegalArgumentException e) {
//            assertTrue(true);
//            return;
//        }
//        fail("Should throw exception");
//
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addContractSameID(){
//        Item product1=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        Item product2=new Item(2,"product2",2,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        try {
//            supplier.addContract(product2,1,150.0,new HashMap<>());
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void addContractSameProductTest(){
//        Item product1=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        try {
//            supplier.addContract(product1,2,150.0,new HashMap<>());
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeContractTest() {
//        try {
//            supplier.removeContract(1);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeContractTest2(){
//        Item product1=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        supplier.removeContract(1);
//        assertEquals(0, supplier.getSupplierContracts().size());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeContractTest3(){
//        Item product1=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        try {
//            supplier.removeContract(2);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeContractInOrderTest(){
//        Item product1=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        supplier.addOrder(LocalDateTime.now(),false,1);
//        supplier.addItemToOrder(1,1,1);
//        try {
//            supplier.removeContract(1);
//        }
//        catch (IllegalArgumentException e){
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void removeContractInDeliveredOrderTest() {
//        Item product1=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
//
//        supplier.addContract(product1, 1, 150.0, new HashMap<>());
//        supplier.addOrder(LocalDateTime.now(), false, 1);
//        supplier.addItemToOrder(1, 1, 1);
//        supplier.receiveOrder(1);
//        supplier.removeContract(1);
//        assertEquals(0, supplier.getSupplierContracts().size());
//    }
//
//    @org.junit.jupiter.api.Test
//    public void cancelOrderTest2() {
//        supplier.addOrder(LocalDateTime.now(), true, 1);
//        try {
//            supplier.cancelOrder(2);
//        } catch (IllegalArgumentException e) {
//            assertTrue(true);
//            return;
//        }
//        fail();
//    }
//
//    @org.junit.jupiter.api.Test
//    public void cancelOrderTest(){
//        supplier.addOrder(LocalDateTime.now(), true, 1);
//        supplier.cancelOrder(1);
//        assertEquals(0, supplier.getOrders().size());
//    }
//}