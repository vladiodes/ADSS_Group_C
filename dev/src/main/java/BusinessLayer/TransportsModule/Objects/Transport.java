package BusinessLayer.TransportsModule.Objects;

import BusinessLayer.EmployeesModule.Objects.Driver;
import BusinessLayer.Interfaces.persistentObject;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import BusinessLayer.SuppliersModule.Order;
import DTO.OrderDTO;
import DTO.TransportDTO;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import static Misc.Functions.LocalDateToString;

public class Transport implements persistentObject<TransportDTO> {
    private LocalDate date;
    private int weight;
    private Driver driver = null;
    private Truck truck = null;
    private List<Order> Orders;
    private Site source = null;
    private int ID;
    private boolean delivered;

    public Transport(LocalDate date, int weight, Driver driver, Truck truck, List<Order> orders, Site source, int ID) throws Exception {
        setTruck(truck);
        setDate(date);
        setDriver(driver);
        setOrders(orders);
        setSource(source);
        setWeight(weight);
        this.ID = ID;
        delivered = false;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setWeight(int weight) throws Exception {
        if (truck != null && truck.getMaxWeight() < weight)
            throw new Exception("Truck weight exceeded.");
        if (truck != null && truck.getFactoryWeight() > weight)
            throw new Exception("Truck weight can't be less than factory weight.");
        this.weight = weight;
    }

    public void setDriver(Driver driver) throws Exception {
        if (truck != null && truck.getFactoryWeight() >= driver.getLicense())
            throw new Exception(driver.getId() + " Doesn't have a license to drive " + truck.getPlateNum());
        this.driver = driver;
    }

    public void setTruck(Truck _truck) throws Exception {
        if (driver != null && _truck.getFactoryWeight() >= driver.getLicense())
            throw new Exception(driver.getId() + " Doesn't have a license to drive " + truck.getPlateNum());
        this.truck = _truck;
    }

    public void setOrders(List<Order> orders) {
        Orders = orders;
    }

    public void setSource(Site source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getWeight() {
        return weight;
    }

    public Driver getDriver() {
        return driver;
    }

    public Truck getTruck() {
        return truck;
    }

    public List<Order> getOrders() {
        return Orders;
    }

    public Site getSource() {
        return source;
    }

    /***
     * when an order arrives this function is called. Its purpose is to update  the Mlai with the items.
     * In case some orders and not Ok, and needed to be inserted manualy, allgood is false and bad orders has bad orders IDS.
     * @param AllGood are all the items alright?
     * @param badOrders what orders are bad and shouldnt update in the Mlai
     */
    public void setDelivered(boolean AllGood, List<Integer> badOrders) {
        SuppliersController SC = SuppliersController.getInstance();
        if (AllGood)
            for (Order d : this.getOrders()) {
                SC.receiveOrder(d.getSupplierID(), d.getOrderID());
                d.transportHasArrived();
            }
        else {
            if (badOrders == null || badOrders.size() == 0)
                throw new IllegalArgumentException();
            for (Order d : this.getOrders()) {
                d.transportHasArrived();
                if (!badOrders.contains(d.getOrderID()))
                    SC.receiveOrder(d.getSupplierID(), d.getOrderID());
            }
        }
        this.delivered = true;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "date=" + date +
                ", weight=" + weight +
                ", driver=" + driver +
                ", truck=" + truck +
                ", Contracts=" + Orders +
                ", source=" + source +
                '}';
    }

    @Override
    public TransportDTO toDTO() {
        LinkedList<OrderDTO> OrdersDTO = new LinkedList<>();
        for (Order ord : Orders) {
            OrdersDTO.add(new OrderDTO(ord, ord.getSupplierID()));
        }
        return new TransportDTO(LocalDateToString(getDate()), getWeight(), getDriver().getId(), getTruck().getPlateNum(), OrdersDTO, getSource().getAddress(), ID);
    }
}
