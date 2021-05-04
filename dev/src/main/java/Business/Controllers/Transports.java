package Business.Controllers;

import Business.Objects.*;
import java.util.*;

public class Transports implements Controller<Transport> {
    private ArrayList<Transport> transports;
    private int ID = 0;
    public Transports() {
        this.transports = new ArrayList<Transport>();
    }

    public void addTransport(Date date, int weight, Driver driver, Truck truck, List<ItemContract> contracts, Site source) throws Exception {
        transports.add(new Transport(date, weight, driver, truck, contracts, source, ID));
        ID ++;
    }

    public ArrayList<Transport> getTransportsOfDriver(int driverID) {
        ArrayList<Transport> filteredTransports = new ArrayList<Transport>();
        for (Transport t : transports)
            if (t.getDriver().getID() == driverID)
                filteredTransports.add(t);
        return filteredTransports;
    }

    public ArrayList<Transport> getTransportsByDate(Date date) {
        ArrayList<Transport> filteredTransports = new ArrayList<Transport>();
        for (Transport t : transports)
            if (t.getDate() == date)
                filteredTransports.add(t);
        return filteredTransports;
    }

    public ArrayList<Transport> getTransports() {
        return transports;
    }

    @Override
    public List<Transport> Load() {
        return null;
    }
}
