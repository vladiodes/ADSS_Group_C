package BusinessLayer.TransportsModule.Controllers;

import BusinessLayer.EmployeesModule.Controllers.ScheduleController;
import BusinessLayer.EmployeesModule.Controllers.StaffController;
import BusinessLayer.EmployeesModule.Objects.Driver;
import BusinessLayer.EmployeesModule.Objects.Shift;
import BusinessLayer.Interfaces.Controller;
import BusinessLayer.SuppliersModule.DayOfWeek;
import BusinessLayer.SuppliersModule.Order;
import BusinessLayer.TransportsModule.Objects.Site;
import BusinessLayer.TransportsModule.Objects.Transport;
import BusinessLayer.TransportsModule.Objects.Truck;
import DataAccessLayer.TransportsDAO;
import Misc.Pair;
import Misc.TypeOfEmployee;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Transports implements Controller<Transport> {
    private ArrayList<Transport> transports;
    private int ID = 0;
    private Trucks trucksController;
    private Sites sitesController;
    private StaffController staffController;
    private ScheduleController scheduleController;
    private TransportsDAO DAO;
    private static Transports instance = null;

    public static Transports getInstance() {
        if (instance == null)
            instance = new Transports();
        return instance;
    }

    private Transports() {
        this.transports = new ArrayList<>();
        trucksController = Trucks.getInstance();
        sitesController = Sites.getInstance();
        staffController = StaffController.getInstance();
        DAO = new TransportsDAO();
    }

    public void addTransport(LocalDate date, int weight, Driver driver, Truck truck, List<Order> orders, Site source) throws Exception {
        Transport toAdd = new Transport(date, weight, driver, truck, orders, source, ID);
        transports.add(toAdd);
        ID++;
        DAO.insert(toAdd.toDTO());
    }

    public ArrayList<Transport> getTransports() {
        return transports;
    }

    @Override
    public void Load() {
        /*this.transports = new ArrayList<>();
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

        }*/ // TODO
    }

    /**
     * This function should find an available transport within a week from the date issued with the order
     * With the given constraint of the fixed days the supplier is in the site destination
     * (meaning that the transport can only be executed on a day which is included in the fixedDays set).
     *
     * @param order           - the order to transport
     * @param siteDestination - the destination to pick the order from
     * @param fixedDays       - the fixed days of the supplier (the constraint).
     * @return returns true if found a transport, returns false if no transport that fulfills the constraints was found.
     */
    public boolean requestTransport(Order order, String siteDestination, Set<DayOfWeek> fixedDays, int weight) {
        if (checkForExistingTransport(order, siteDestination, fixedDays, weight))
            return true;
        else return tryMakeTransport(order, siteDestination, fixedDays, weight);
    }

    private boolean tryMakeTransport(Order order, String siteDestination, Set<DayOfWeek> fixedDays, int weight) {
        LocalDate orderDate = order.getDateOfOrder();
        int dayslater = 0;
        List<LocalDate> maybeDates = new ArrayList<>();
        while (dayslater < 8) {
            for (DayOfWeek DOW : fixedDays) {
                if ((orderDate.getDayOfWeek().getValue() + 1) % 7 != 0 && DayOfWeek.valueOf((orderDate.getDayOfWeek().getValue() + 1) % 7).equals(DOW))
                    maybeDates.add(orderDate);
            }
            orderDate = orderDate.plusDays(1);
            dayslater++;
        }
        List<Shift> maybeShifts = scheduleController.getWeeklyShiftsForTransport(maybeDates);
        if (maybeShifts == null)
            return false;
        else {
            Shift S = maybeShifts.get(0);
            Driver D = null;
            for (Pair<String, TypeOfEmployee> currP : S.getCurrentShiftEmployees())
                if (currP.second == TypeOfEmployee.Driver)
                    D = (Driver) staffController.getEmployeeByID(currP.first);
            List<Order> ords = new ArrayList<>();
            Site Si = null;
            ords.add(order);
            try {
                Si = Sites.getInstance().getSite(siteDestination);
            } catch (Exception e) {
                return false;
            }
            try {
                Truck Ttruck = this.trucksController.getAvailableTruck(order.getDateOfOrder());
                if (Ttruck == null) return false;
                this.addTransport(S.getDate(), Ttruck.getFactoryWeight() + weight, D, Ttruck, ords, Si);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    private boolean checkForExistingTransport(Order order, String siteDestination, Set<DayOfWeek> fixedDays, int weight) {
        if (order == null || sitesController.getSite(siteDestination) == null || fixedDays.isEmpty())
            return false;
        Transport TranstoAdd = null;
        for (Transport temp : this.transports) {
            if (TranstoAdd != null) break;
            if (temp.getDate().isAfter(order.getDateOfOrder())) { //if the order is ready before the transport
                long TimeDiff = ChronoUnit.DAYS.between(temp.getDate(), order.getDateOfOrder());
                if (TimeDiff <= 7) //week time difference
                    for (DayOfWeek DOW : fixedDays)
                        if (DOW.compareTo(DayOfWeek.valueOf((temp.getDate().getDayOfWeek().getValue() - 1) % 8)) == 0) {
                            TranstoAdd = temp;
                            int newWeight = TranstoAdd.getWeight() + weight;
                            if (newWeight <= TranstoAdd.getTruck().getMaxWeight()) {
                                TranstoAdd.getOrders().add(order);
                                try {
                                    TranstoAdd.setWeight(newWeight);
                                } //because ima shitty programer dont ask questions please
                                catch (Exception e) {
                                }
                                return true;
                            }
                        }
            }
        }
        return false;
    }

    public Transport getTransport(int ID){
        for(Transport transport:transports){
            if(transport.getID()==ID)
                return transport;
        }
        throw new IllegalArgumentException("No such transport with the given ID");
    }
}