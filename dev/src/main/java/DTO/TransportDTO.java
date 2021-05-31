package DTO;

import java.util.List;

public class TransportDTO {
    public String date;
    public int weight;
    public String driver;
    public String truck;
    public List<OrderDTO> Contracts;
    public String source;
    public int ID;

    public TransportDTO(String date, int weight, String driver, String truck, List<OrderDTO> contracts, String source, int ID) {
        this.date = date;
        this.weight = weight;
        this.driver = driver;
        this.truck = truck;
        Contracts = contracts;
        this.source = source;
        this.ID = ID;
    }
}
