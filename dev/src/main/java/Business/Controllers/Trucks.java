package Business.Controllers;

import Business.Objects.Truck;
import java.util.ArrayList;
import java.util.HashMap;

public class Trucks implements Controller<Truck> {
    private HashMap<Integer, Truck> trucks;

    public Trucks() {
        this.trucks = new HashMap<Integer, Truck>();
        trucks.put(3212345,new Truck(3212345,"Honda Ridgeline",5000,"Pickup Truck",1500));
        trucks.put(6942021,new Truck(6942021,"Tesla",10000,"Smart Car",1500));
    }

    public void addTruck(int plate, String model, int maxweight, String type, int factoryweight) throws Exception {
        if (trucks.containsKey(plate))
            throw new Exception(plate + " already exists in the database.");
        trucks.put(plate, new Truck(plate, model, maxweight, type, factoryweight));
    }

    public Truck getTruck(int plate) throws Exception {
        if (trucks.containsKey(plate))
            return trucks.get(plate);
        else throw new Exception(plate + " doesn't exist");
    }

    public ArrayList<Truck> getTrucks(){
        return new ArrayList<Truck>(trucks.values());
    }
}
