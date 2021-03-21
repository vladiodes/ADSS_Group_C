package Business.Controllers;

import Business.Objects.Truck;
import java.util.HashMap;

public class Trucks implements Controller<Truck> {
    private HashMap<Integer, Truck> trucks;

    public void addTruck(int plate, String model, int maxweight, String type, int factoryweight) throws Exception {
        if(trucks.containsKey(plate))
            throw new Exception(plate+" already exists in the database.");
        trucks.put(plate,new Truck(plate,model,maxweight,type,factoryweight));
    }

    public Truck getTruck(int plate) throws Exception {
        if(trucks.containsKey(plate))
            return trucks.get(plate);
        else throw new Exception(plate+" doesn't exist");
    }
}
