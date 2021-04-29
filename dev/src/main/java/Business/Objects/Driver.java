package Business.Objects;

import Business.Misc.TypeOfEmployee;

import java.util.Date;
import java.util.List;

public class Driver extends Employee {
    private String Name;
    private int License;
    private int ID;

    public Driver(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills, int license) throws Exception {
        super(firstName,lastName,id,bankAccountNumber,salary,empConditions,startWorkingDate,skills);
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
