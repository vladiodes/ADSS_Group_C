package Business.Objects;

public class Driver {
    private String Name;
    private int License;
    private int ID;

    public Driver(String name, int id, int license) {
        setName(name);
        setID(id);
        setLicense(license);
    }

    public void setName(String name) {
        Name = name;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setLicense(int license) {
        License = license;
    }

    public String getName() {
        return Name;
    }

    public int getLicense() {
        return License;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "Name='" + Name + '\'' +
                ", License=" + License +
                ", ID=" + ID +
                '}';
    }
}
