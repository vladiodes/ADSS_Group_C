package Business.Objects;

import Data.DTO.TransportDTO;
import Data.DTO.ItemContractDTO;
import java.text.SimpleDateFormat;
import java.util.*;

public class Transport implements persistentObject<TransportDTO> {
    private Date date;
    private int weight;
    private Driver driver = null;
    private Truck truck = null;
    private List<ItemContract> Contracts;
    private Site source = null;
    private int ID;

    public Transport(Date date, int weight, Driver driver, Truck truck, List<ItemContract> contracts, Site source, int ID) throws Exception {
        setTruck(truck);
        setDate(date);
        setDriver(driver);
        setContracts(contracts);
        setSource(source);
        setWeight(weight);
        this.ID = ID;
    }

    public Transport(TransportDTO dto) throws Exception {
        setTruck(dto.truck);
        setDate(new SimpleDateFormat("dd/MM/yyyy").parse(dto.date));
        setDriver(dto.driver);
        setContracts(dto.Contracts);
        setSource(dto.source);
        setWeight(dto.weight);
        this.ID = dto.ID;
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

    @Override
    public TransportDTO toDTO() {
        LinkedList<ItemContractDTO> contractsDTO = new LinkedList<>();
        for (ItemContract contract : Contracts) {
            contractsDTO.add(new ItemContractDTO(ID, contract.getDestination().getAddress(), contract.getItems(), contract.getPassed()));
        }
        return new TransportDTO(getDate().toString(), getWeight(), getDriver().getId(), getTruck().getPlateNum(), contractsDTO, getSource().getAddress(), ID);
    }
}
