package Business.Controllers;

import Business.Objects.Driver;
import java.util.*;

public class Drivers implements Controller<Driver> {
    private HashMap<Integer, Driver> drivers;

    public Drivers() {
        this.drivers = new HashMap<Integer, Driver>();
        drivers.put(123123123,new Driver("Yossi Mizrahi", 123123123, 5000));
    }

    public void addDriver(String name, int license, int id) throws Exception {
        if (drivers.containsKey(id))
            throw new Exception(id + " Driver already exists in the database");
        drivers.put(id, new Driver(name, id, license));
    }

    public Driver getDriver(int ID) throws Exception {
        if(!drivers.containsKey(ID))
            throw new Exception("Driver " + ID + " doesn't exist in the data base");
        else return drivers.get(ID);
    }

    public ArrayList<Driver> getDrivers(){
            return new ArrayList<Driver>(drivers.values());
    }
}
