package BusinessLayer.TransportsModule.Objects;

import BusinessLayer.EmployeesModule.Objects.Driver;
import BusinessLayer.Interfaces.persistentObject;
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
    private int ID;
    private boolean delivered;

    public Transport(LocalDate date, int weight, Driver driver, Truck truck, List<Order> orders, int ID) throws Exception {
        setTruck(truck);
        setDate(date);
        setDriver(driver);
        setOrders(orders);
        setWeight(weight);
        this.ID = ID;
        delivered = false;
    }

    public Transport(LocalDate date, int weight, Driver driver, Truck truck, List<Order> orders, int ID, boolean Del) throws Exception {
        setTruck(truck);
        setDate(date);
        setDriver(driver);
        setOrders(orders);
        setWeight(weight);
        this.ID = ID;
        delivered = Del;
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

    public int getID() {
        return ID;
    }

    public boolean getDelivered(){return this.delivered;}

    /**
     * Once the order has arrived to the store, it's marked as arrived, and the orders it contains can now be stashed
     * in the inventory
     */
    public void setDelivered() {
        //@TODO: synchronize this with the database
        delivered=true;
        for(Order o:Orders)
            o.transportHasArrived(); //simply changes shipment status

        /**
         * Ilay's prev implementation
         */
//        SuppliersController SC = SuppliersController.getInstance();
//        if (AllGood)
//            for (Order d : this.getOrders()) {
//                SC.receiveOrder(d.getSupplierID(), d.getOrderID());
//                d.transportHasArrived();
//            }
//        else {
//            if (badOrders == null || badOrders.size() == 0)
//                throw new IllegalArgumentException();
//            for (Order d : this.getOrders()) {
//                d.transportHasArrived();
//                if (!badOrders.contains(d.getOrderID()))
//                    SC.receiveOrder(d.getSupplierID(), d.getOrderID());
//            }
//        }
//        this.delivered = true;
    }

    @Override
    public String toString() {
        //@TODO: Order object doesn't have any toString() method, you should first create an orderDTO object, which DOES have a toString() method
        return "Transport{" +
                "date=" + date +
                ", weight=" + weight +
                ", driver=" + driver +
                ", truck=" + truck +
                ", Orders=" + Orders +
                ", Delivered=" + this.delivered +
                '}';
    }

    @Override
    public TransportDTO toDTO() {
        LinkedList<OrderDTO> OrdersDTO = new LinkedList<>();
        for (Order ord : Orders) {
            OrdersDTO.add(new OrderDTO(ord, ord.getSupplierID()));
        }
        return new TransportDTO(LocalDateToString(getDate()), getWeight(), getDriver().getId(), getTruck().getPlateNum(), OrdersDTO, this.delivered, ID);
    }
}
