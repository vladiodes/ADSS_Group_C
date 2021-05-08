//package BusinessLayer.SuppliersModule;
//
//import BusinessLayer.InventoryModule.Item;
//import DTO.SupplierDTO;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.HashSet;
//
//import static org.junit.Assert.*;
//
//public class SupplierTest {
//    private Supplier supplier;
//
//    @Before
//    public void setUp() {
//        supplier=new Supplier(0,"supplier1",new HashSet<>(),true,"bank account", PaymentAgreement.Monthly,new HashSet<>(),new HashSet<>(),new HashMap<>(),new HashMap<>());
//    }
//
//    @Test
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
//    @Test
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
//    @Test
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
//    @Test
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
//    @Test
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
//    @Test
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
//    @Test
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
//    @Test
//    public void getOrdersTest(){
//        supplier.addOrder(LocalDateTime.now(),true,1);
//        supplier.addOrder(LocalDateTime.now(),true,2);
//        assertEquals(2, supplier.getOrders().size());
//    }
//
//    @Test
//    public void addContractTest(){
//        Item product=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
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
//    @Test
//    public void addContractSameID(){
//        Item product1=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
//
//        Item product2=new Item("product2",2,"producer",1,1,1, LocalDate.now(),1.2,2);
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
//    @Test
//    public void addContractSameProductTest(){
//        Item product1=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
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
//    @Test
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
//    @Test
//    public void removeContractTest2(){
//        Item product1=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        supplier.removeContract(1);
//        assertEquals(0, supplier.getSupplierContracts().size());
//    }
//
//    @Test
//    public void removeContractTest3(){
//        Item product1=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
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
//    @Test
//    public void removeContractInOrderTest(){
//        Item product1=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
//
//        supplier.addContract(product1,1,150.0,new HashMap<>());
//        supplier.addOrder(LocalDateTime.now(),true,1);
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
//    @Test
//    public void removeContractInDeliveredOrderTest() {
//        Item product1=new Item("product",1,"producer",1,1,1, LocalDate.now(),1.2,2);
//
//        supplier.addContract(product1, 1, 150.0, new HashMap<>());
//        supplier.addOrder(LocalDateTime.now(), true, 1);
//        supplier.addItemToOrder(1, 1, 1);
//        supplier.receiveOrder(1);
//        supplier.removeContract(1);
//        assertEquals(0, supplier.getSupplierContracts().size());
//    }
//
//    @Test
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
//    @Test
//    public void cancelOrderTest(){
//        supplier.addOrder(LocalDateTime.now(), true, 1);
//        supplier.cancelOrder(1);
//        assertEquals(0, supplier.getOrders().size());
//    }
//}