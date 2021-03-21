package Business.Controllers;

import Business.Objects.Truck;
import java.util.HashMap;

public class Trucks implements Controller<Truck> {
    HashMap<Integer, Truck> trucks;

    public void addTruck(Truck truck){
        trucks.put(truck.getPlateNum(),truck);
    }
}
