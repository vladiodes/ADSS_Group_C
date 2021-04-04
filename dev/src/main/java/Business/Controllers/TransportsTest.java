package Business.Controllers;

import Business.Objects.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TransportsTest {


    @org.junit.jupiter.api.Test
    void addTransportWrongWeight() {
        Transports T = new Transports();
        Truck TempTruck = new Truck(1,"s",5,"a",1);
        Driver TempDriver = new Driver("a",2,1000);
        Site TempSite = new Site("",123,"","North");
        try {
           T.addTransport(new Transport(new Date(), 7, TempDriver, TempTruck, new ArrayList<ItemContract>(), TempSite));
           assertTrue(false);
        }
        catch (Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    void addTransportWrongDriver(){
        Transports T = new Transports();
        Truck TempTruck = new Truck(1,"s",5,"a",5);
        Driver TempDriver = new Driver("a",2,4);
        Site TempSite = new Site("",123,"","North");
        try {
            T.addTransport(new Transport(new Date(), 7, TempDriver, TempTruck, new ArrayList<ItemContract>(), TempSite));
            assertTrue(false);
        }
        catch (Exception e)
        {
            assertTrue(true);
        }
    }

   @Test
    void addSiteWithWrongSection(){
        Sites TempSites = new Sites();
        assertThrows(Class<Exception>,TempSites.addSite("a",231,"a","a"));
   }
}