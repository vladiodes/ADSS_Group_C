package BusinessLayer.SuppliersModule;

import BusinessLayer.InventoryModule.Item;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.*;

public class OrderTest {
    private Order order;

    @Before
    public void setUp(){
        order=new Order(LocalDateTime.now(),false,1);
    }

    @Test
    public void addItemToDeliveredOrder(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
        Contract c=new Contract(100,1,new HashMap<>(),product);
        order.receive();
        try {
            order.addItem(c,10,new HashMap<>());
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
            return;
        }
        fail();
    }

    @Test
    public void addItemTest(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);
        Contract c=new Contract(100,1,new HashMap<>(),product);
        order.addItem(c,10,new HashMap<>());
        assertEquals(1, order.getProductsInOrder().size());
        assertEquals(10, order.getTotalQuantity());
        assertEquals(1000.0, order.getPriceAfterDiscount(), 0.0);
        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
    }

    @Test
    public void addItemWithDiscountsTest(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        HashMap<Double,Integer>discounts=new HashMap<>();
        discounts.put(500.0,10);
        Contract c=new Contract(100,1,new HashMap<>(),product);
        order.addItem(c,10,discounts);
        assertEquals(1, order.getProductsInOrder().size());
        assertEquals(10, order.getTotalQuantity());
        assertEquals(900.0, order.getPriceAfterDiscount(), 0.0);
        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
    }

    @Test
    public void addItemWith2DiscountsTest(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        HashMap<Double,Integer>discounts=new HashMap<>();
        discounts.put(500.0,10);
        discounts.put(600.0,20);
        Contract c=new Contract(100,1,new HashMap<>(),product);
        order.addItem(c,10,discounts);
        assertEquals(1, order.getProductsInOrder().size());
        assertEquals(10, order.getTotalQuantity());
        assertEquals(800.0, order.getPriceAfterDiscount(), 0.0);
        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
    }

    @Test
    public void addItemWithDiscountsNotExceedingPriceTest(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        HashMap<Double,Integer>discounts=new HashMap<>();
        discounts.put(5000.0,10);
        discounts.put(6000.0,20);
        Contract c=new Contract(100,1,new HashMap<>(),product);
        order.addItem(c,10,discounts);
        assertEquals(1, order.getProductsInOrder().size());
        assertEquals(10, order.getTotalQuantity());
        assertEquals(1000.0, order.getPriceAfterDiscount(), 0.0);
        assertEquals(1000.0, order.getPriceBeforeDiscount(), 0.0);
    }

    @Test
    public void receiveOrderTest(){
        order.receive();
        assertSame(order.getShipmentStatus(), Order.ShipmentStatus.Delivered);
        try {
            order.receive();
        }
        catch (IllegalArgumentException e)
        {
            assertTrue(true);
            return;
        }
        fail();
    }

    @Test
    public void removeProductFromDeliveredOrderTest() {
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        Contract c = new Contract(100, 1, new HashMap<>(), product);
        order.addItem(c, 10, new HashMap<>());
        order.receive();
        try {
            order.removeProduct(c, new HashMap<>());
        } catch (IllegalArgumentException e) {
            assertTrue(true);
            return;
        }
        fail();
    }

    @Test
    public void removeProductTest(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        Contract c = new Contract(100, 1, new HashMap<>(), product);
        order.addItem(c,10,new HashMap<>());
        order.removeProduct(c,new HashMap<>());
        assertEquals(0, order.getTotalQuantity());
        assertEquals(0, order.getProductsInOrder().size());
    }

    @Test public void checkIfProductExistsTest(){
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        Item product2=new Item(2,"product2",2,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        Contract c = new Contract(100, 1, new HashMap<>(), product);
        order.addItem(c,10,new HashMap<>());
        assertTrue(order.checkIfProductExists(product));
        assertFalse(order.checkIfProductExists(product2));
    }

    @Test
    public void reOrderTest(){
        Order order1=new Order(LocalDateTime.now(),true,10);
        Item product=new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2.2);

        Contract c = new Contract(100, 1, new HashMap<>(), product);
        order1.addItem(c,10,new HashMap<>());
        Order order2=new Order(order1,11,LocalDateTime.now());
        assertEquals(1, order2.getProductsInOrder().size());
        order1.removeProduct(c,new HashMap<>());
        assertEquals(1, order2.getProductsInOrder().size());
    }

}