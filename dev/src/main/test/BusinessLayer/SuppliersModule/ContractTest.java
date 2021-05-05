package BusinessLayer.SuppliersModule;

import BusinessLayer.InventoryModule.Item;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ContractTest {
    private Contract contract;

    @Before
    public void setUp() {
        contract=new Contract(100.0,1,new HashMap<>(),new Item(1,"product",1,"producer",1,1,1, LocalDate.now(),1.2,2));
    }

    @Test
    public void testAddDiscount(){
        contract.addDiscount(100,10);
        assertEquals(10, (int) contract.getDiscountByQuantity().get(100));
        assertEquals(1, contract.getDiscountByQuantity().size());
    }

    @Test
    public void testAddDiscount2(){
        try {
            contract.addDiscount(-100,10);
        }
        catch (IllegalArgumentException e){
            assertEquals(0, contract.getDiscountByQuantity().size());
            return;
        }
        fail();
    }

    @Test public void testAddDiscount3() {
        contract.addDiscount(100, 10);
        try {
            contract.addDiscount(100, 15);
        } catch (IllegalArgumentException e) {
            assertEquals(1, contract.getDiscountByQuantity().size());
            return;
        }
        fail();
    }

    @Test
    public void testAddDiscountAbove100(){
        try {
            contract.addDiscount(100, 101);
        }
        catch (IllegalArgumentException e){
            assertTrue(true);
            return;
        }
        fail();
    }
}