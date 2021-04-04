package Business.Objects;

import java.util.*;

public class Transport {
    private Date date;
    private int weight;
    private Driver driver = null;
    private Truck truck = null;
    private List<ItemContract> Contracts;
    private Site source = null;

    public Transport(Date date, int weight, Driver driver, Truck truck, List<ItemContract> contracts, Site source) throws Exception {
        setTruck(truck);
        setDate(date);
        setDriver(driver);
        setContracts(contracts);
        setSource(source);
        setWeight(weight);
    }

    public void setDate(Date date) {
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
            throw new Exception(driver.getName() + " Doesn't have a license to drive " + truck.getPlateNum());
        this.driver = driver;
    }

    public void setTruck(Truck _truck) throws Exception {
        if (driver != null && _truck.getFactoryWeight() >= driver.getLicense())
            throw new Exception(driver.getName() + " Doesn't have a license to drive " + truck.getPlateNum());
        this.truck = _truck;
    }

    public void setContracts(List<ItemContract> contracts) {
        Contracts = contracts;
    }

    public void setSource(Site source) {
        this.source = source;
    }

    public Date getDate() {
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

    public List<ItemContract> getContracts() {
        return Contracts;
    }

    public Site getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "date=" + date +
                ", weight=" + weight +
                ", driver=" + driver +
                ", truck=" + truck +
                ", Contracts=" + Contracts +
                ", source=" + source +
                '}';
    }
}
