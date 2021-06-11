package DTO;

import java.util.List;

public class TransportDTO {
    public String date;
    public int weight;
    public String driver;
    public String truck;
    public List<OrderDTO> orders;
    public boolean wasDelivered;
    public int ID;

    public TransportDTO(String date, int weight, String driver, String truck, List<OrderDTO> contracts, boolean _wasDelivered, int ID) {
        this.date = date;
        this.weight = weight;
        this.driver = driver;
        this.truck = truck;
        orders = contracts;
        this.wasDelivered = _wasDelivered;
        this.ID = ID;
    }
}
