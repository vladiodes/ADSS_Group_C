package Business.Objects;

public class Truck {
    private int plateNum;
    private String model;
    private int maxWeight;
    private String type;
    private int factoryWeight;

    public Truck(int plateNum, String model, int maxWeight, String type, int Fw) {
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

    public int getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(int plateNum) {
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
