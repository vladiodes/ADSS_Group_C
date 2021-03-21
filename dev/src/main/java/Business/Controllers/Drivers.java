package Business.Controllers;

import Business.Objects.Driver;
import java.util.HashMap;

public class Drivers implements Controller {
    HashMap<Integer,Driver> drivers = new HashMap<Integer, Driver>();

    public void addDriver(Driver driver){
        drivers.put(driver.getID(),driver);
    }
}
