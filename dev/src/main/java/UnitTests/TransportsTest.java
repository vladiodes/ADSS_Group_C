package UnitTests;

import Business.Controllers.Sites;
import Business.Controllers.Transports;
import Business.Controllers.Trucks;
import Business.Misc.TypeOfEmployee;
import Business.Objects.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TransportsTest {
//String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills, int license
    List<TypeOfEmployee> driver = new ArrayList<TypeOfEmployee>();

    @BeforeAll
    void Init(){
        driver.add(TypeOfEmployee.Driver);
    }

    @org.junit.jupiter.api.Test
    void addTransportWrongWeight() throws Exception { //
        Transports T = new Transports();
        Truck TempTruck = new Truck(1, "s", 5, "a", 1);
        Driver TempDriver = new Driver("firstName","lastName","123","123",69,"fine",new Date(),driver,500);
        Site TempSite = new Site("", 123, "", "North");
        try {
            T.addTransport(new Date(), 7, TempDriver, TempTruck, new ArrayList<ItemContract>(), TempSite);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void addTransport() throws Exception {
        Transports T = new Transports();
        Truck TempTruck = new Truck(1, "s", 5, "a", 1);
        Driver TempDriver = new Driver("firstName","lastName","123","123",69,"fine",new Date(),driver,500);
        Site TempSite = new Site("", 123, "", "North");
        try {
            T.addTransport(new Date(), 4, TempDriver, TempTruck, new ArrayList<ItemContract>(), TempSite);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void addTransportWrongDriver() throws Exception {
        Transports T = new Transports();
        Truck TempTruck = new Truck(1, "s", 5, "a", 5);
        Driver TempDriver = new Driver("firstName","lastName","123","123",69,"fine",new Date(),driver,1);
        Site TempSite = new Site("", 123, "", "North");
        try {
            T.addTransport(new Date(), 7, TempDriver, TempTruck, new ArrayList<ItemContract>(), TempSite);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void addSiteWithWrongSection() {
        Sites TempSites = new Sites();
        try {
            TempSites.addSite("a", 231, "a", "a");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void addIdenticalSites() {
        Sites TempSites = new Sites();
        try {
            TempSites.addSite("a", 231, "a", "North");
            TempSites.addSite("a", 231, "a", "North");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void addingIdenticalSections() {
        Sites TempSites = new Sites();
        try {
            TempSites.addSection("a");
            TempSites.addSection("a");
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void addingNewSection() {
        Sites TempSites = new Sites();
        try {
            String newSection = "Atlantis";
            TempSites.addSection(newSection);
            assertEquals(TempSites.getSection(newSection), newSection);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void addingIdenticalTruck() {
        Trucks TempTrucks = new Trucks();
        try {
            TempTrucks.addTruck(1, "", 1, "", 1);
            TempTrucks.addTruck(1, "", 1, "", 1);
            assertTrue(false);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    void addingTruck() {
        Trucks TempTrucks = new Trucks();
        try {
            TempTrucks.addTruck(1, "", 1, "", 1);
            assertEquals(TempTrucks.getTruck(1).getPlateNum(), 1);
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}