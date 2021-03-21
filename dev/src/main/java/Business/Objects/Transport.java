package Business.Objects;

import java.util.Date;
import java.util.List;

public class Transport {
    private Date date;
    private int weight;
    private Driver driver;
    private Truck truck;
    private List<ItemContract> Contracts;
    private Site source;

    public Transport(Date date, int weight, Driver driver, Truck truck, List<ItemContract> contracts, Site source) throws Exception {
        setDate(date);
        setWeight(weight);
        setDriver(driver);
        setTruck(truck);
        setContracts(contracts);
        setSource(source);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setWeight(int weight) throws Exception {
        if(truck!=null && truck.getMaxWeight() < weight)
            throw new Exception("Truck weight exceeded.");
        this.weight = weight;
    }

    public void setDriver(Driver driver) throws Exception {
        if(truck!=null && truck.getFactoryWeight() >= driver.getLicense())
            throw new Exception(driver.getName() + " Doesn't have a license to drive " + truck.getPlateNum());
        this.driver = driver;
    }

    public void setTruck(Truck _truck) throws Exception {
        if(driver!=null && _truck.getFactoryWeight() >= driver.getLicense())
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
}
