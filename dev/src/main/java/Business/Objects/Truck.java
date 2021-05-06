package Business.Objects;

public class Truck {
    private String plateNum;
    private String model; // model of the truck
    private int maxWeight; // the truck can't carry more than this.
    private String type; // What type it is
    private int factoryWeight; //used to tell what license you need to drive the truck.

    public Truck(String plateNum, String model, int maxWeight, String type, int Fw) {
        setPlateNum(plateNum);
        setModel(model);
        setMaxWeight(maxWeight);
        setType(type);
        setFactoryWeight(Fw);
    }

    public void setFactoryWeight(int factoryWeight) {
        this.factoryWeight = factoryWeight;
    }

    public int getFactoryWeight() {
        return factoryWeight;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "plateNum=" + plateNum +
                ", model='" + model + '\'' +
                ", maxWeight=" + maxWeight +
                ", type='" + type + '\'' +
                ", factoryWeight=" + factoryWeight +
                '}';
    }
}
