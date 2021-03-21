package Business.Objects;

public class Truck {
    private int plateNum;
    private String model;
    private int maxWeight;
    private String type;

    public Truck(int plateNum, String model, int maxWeight, String type) {
        setPlateNum(plateNum);
        setModel(model);
        setMaxWeight(maxWeight);
        setType(type);
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
}
