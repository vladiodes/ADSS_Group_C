package BusinessLayer.SuppliersModule;

import BusinessLayer.SuppliersModule.Contract;
import BusinessLayer.SuppliersModule.Product;
import BusinessLayer.SuppliersModule.ProductInOrder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class ProductInOrderTest {
    private ProductInOrder pio;

    @Before
    public void setUp() {
        Product p=new Product(1,"product");
        Contract c=new Contract(100.0,1,new HashMap<>(),p);
        pio=new ProductInOrder(100,c);
    }

    @Test
    public void testOrderMore(){
        pio.orderMore(10);
        assertEquals(pio.getQuantity(),110);
        assertEquals(pio.getTotalPrice(),100*110,0.0);
    }
}