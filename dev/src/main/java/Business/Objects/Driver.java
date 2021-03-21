package Business.Objects;

public class Driver {
    private String Name;
    private int License;
    private int ID;

    public Driver(String _name, int _license, int _id){
        setID(_id);
        setName((_name));
        setLicense(_license);
    }

    public void setLicense(int license) {
        License = license;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        Name = name;
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
}
