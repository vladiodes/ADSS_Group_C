package Business.Controllers;

import Business.Objects.*;
import java.util.*;

public class TransportsFacade {
    Sites Sit = new Sites();
    Transports Tra = new Transports();
    Drivers Dri = new Drivers();
    Trucks Tru = new Trucks();

    public TransportsFacade(){}

    public void addDriver(String name, int license, int id) throws Exception {
        Dri.addDriver(name, id, license);
    }

    public void addSite(String Ad, int num, String c, String Sec) throws Exception {
        Sit.addSite(Ad, num, c, Sec);
    }

    public void addTruck(int plate, String model, int maxweight, String type, int factoryweight) throws Exception {
        Tru.addTruck(plate,model,maxweight,type,factoryweight);
    }

    public void addTransport(Date date, int w, int driverID, int TruckID, List<ItemContract> IC, String Source) throws Exception {
        Tra.addTransport(new Transport(date,w,Dri.getDriver(driverID),Tru.getTruck(TruckID), IC, Sit.getSite(Source)));
    }

    public void addSection(String section) throws Exception {
        Sit.addSection(section);
    }

    public ArrayList<Transport> getTransportsOfDriver(int driverID) {
        return Tra.getTransportsOfDriver(driverID);
    }

    public ArrayList<Transport> getTransportsByDate(Date date) {
        return Tra.getTransportsByDate(date);
    }

    public ArrayList<Truck> getAllTrucks(){
        return Tru.getTrucks();
    }

    public ArrayList<Driver> getAllDrivers(){
        return Dri.getDrivers();
    }

    public ArrayList<Site> getAllSites(){
        return Sit.getSites();
    }

    public ArrayList<String> getAllSections(){
        return Sit.getSections();
    }

    public ArrayList<Transport> getAllTransports(){
        return Tra.getTransports();
    }

    public String getSection(String s) throws Exception {
        return Sit.getSection(s);
    }

    public Site getSite(String s) throws Exception {
        return Sit.getSite(s);
    }
}
