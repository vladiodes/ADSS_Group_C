package Business.Controllers;

import Business.Objects.Driver;
import java.util.HashMap;

public class Drivers implements Controller<Driver> {
    HashMap<Integer,Driver> drivers = new HashMap<Integer, Driver>();

    public void addDriver(String name, int license, int id) throws Exception {
        if(drivers.containsKey(id))
            throw new Exception(id+" Driver already exists in the database");
        drivers.put(id,new Driver(name,license,id));
    }
}
