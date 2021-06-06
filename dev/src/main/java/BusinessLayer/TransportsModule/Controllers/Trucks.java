package BusinessLayer.TransportsModule.Controllers;

import BusinessLayer.Interfaces.Controller;
import BusinessLayer.TransportsModule.Objects.Truck;
import DataAccessLayer.TrucksDAO;
import DTO.TruckDTO;

import java.time.LocalDate;
import java.util.*;

public class Trucks implements Controller<Truck> {
    private HashMap<String, Truck> trucks;
    private TrucksDAO DAO;

    private static Trucks instance=null;
    private Trucks() {
        this.trucks = new HashMap<String, Truck>();
        DAO = new TrucksDAO();
    }

    public static Trucks getInstance() {
        if(instance==null)
            instance=new Trucks();
        return instance;
    }

    /***
     * bullshit overload
     * @param dateOfOrder date for an available truck
     * @return
     */
    public Truck getAvailableTruck(LocalDate dateOfOrder) {
        for(Map.Entry<String, Truck> kaki : trucks.entrySet())
            return kaki.getValue();
        return null;
    }

    public void addTruck(String plate, String model, int maxweight, String type, int factoryweight) throws Exception {
        Truck temp = null;
        try{
            temp = getTruck(plate);
        }
        catch (Exception e){
            //truck doesn't exist in the database
        }
        if(temp !=null)
            throw new IllegalArgumentException("Truck already exists in the databse with the same plate num.");
        Truck toAdd = new Truck(plate, model, maxweight, type, factoryweight);
        trucks.put(plate, toAdd);
        DAO.insert(toAdd.toDTO());
    }

    public Truck getTruck(String plate) throws Exception {
        if (trucks.containsKey(plate))
            return trucks.get(plate);
        TruckDTO output = DAO.getTruck(plate);
        if(output == null)
            throw new Exception(plate + " doesn't exist");
        Truck toAdd = new Truck(output);
        trucks.put(toAdd.getPlateNum(),toAdd);
        return toAdd;
    }

    public ArrayList<Truck> getTrucks() {
        this.trucks = new HashMap<>();
        Load();
        return new ArrayList<Truck>(trucks.values());
    }

    @Override
    public void Load() {
        List<TruckDTO> dtos = DAO.getAll();
        for(TruckDTO element : dtos)
            trucks.put(element.plateNum,new Truck(element));
    }
}
