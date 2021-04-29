package Data.DTO;

public class TruckDTO {
    public int plateNum;
    public String model; // model of the truck
    public int maxWeight; // the truck can't carry more than this.
    public String type; // What type it is
    public int factoryWeight; //used to tell what license you need to drive the truck.

    public TruckDTO(int plateNum, String model, int maxWeight, String type, int factoryWeight) {
        this.plateNum = plateNum;
        this.model = model;
        this.maxWeight = maxWeight;
        this.type = type;
        this.factoryWeight = factoryWeight;
    }
}
