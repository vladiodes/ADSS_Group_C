package Business.Controllers;

import Business.Objects.*;
import Data.DAO.TransportsDAO;
import Data.DTO.ItemContractDTO;
import Data.DTO.TransportDTO;

import java.text.SimpleDateFormat;
import java.util.*;

public class Transports implements Controller<Transport> {
    private ArrayList<Transport> transports;
    private int ID = 0;
    private Trucks trucksController;
    private Sites sitesController;
    private StaffController staffController;
    private TransportsDAO DAO;
    public Transports(Trucks tc, Sites sitesc, StaffController sc) {
        this.transports = new ArrayList<Transport>();
        trucksController = tc;
        sitesController = sitesc;
        staffController = sc;
        DAO = new TransportsDAO();
    }

    public void addTransport(Date date, int weight, Driver driver, Truck truck, List<ItemContract> contracts, Site source) throws Exception {
        Transport toAdd = new Transport(date, weight, driver, truck, contracts, source, ID);
        transports.add(toAdd);
        ID ++;
        DAO.insert(toAdd.toDTO());
    }

    public ArrayList<Transport> getTransportsOfDriver(String driverID) {
        ArrayList<Transport> filteredTransports = new ArrayList<Transport>();
        for (Transport t : transports)
            if (t.getDriver().getId().equals(driverID))
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
    public void Load() {
        ID = 0;
        try {
            List<TransportDTO> DTOS = DAO.getAll();
            for (TransportDTO el : DTOS) {
                List<ItemContract> ICS = new ArrayList<>();
                for (ItemContractDTO el2 : el.Contracts) {
                    ICS.add(new ItemContract(el2.ID,sitesController.getSite(el2.destination), el2.items, el2.passed));
                }
                this.transports.add(new Transport(Misc.Functions.StringToDate(el.date), el.weight, (Driver)staffController.getEmployeeByID(el.driver), trucksController.getTruck(el.truck), ICS, sitesController.getSite(el.source), el.ID));
                ID ++;
            }
        }
        catch (Exception e )
        {
            System.out.println(e.getMessage());
        }
    }
}
