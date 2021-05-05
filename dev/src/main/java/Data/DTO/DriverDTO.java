package Data.DTO;

import Business.Misc.Pair;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DriverDTO {
    public String firstName;
    public String lastName;
    public String id;
    public String bankAccountNumber;
    public int salary;
    public String empConditions;
    public Date startWorkingDate;
    public List<String> skills;
    public List<Pair<Date, String>> availableShifts;
    public int License;

    public DriverDTO(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions,
                     Date startWorkingDate, int License, List<String> skills, List<Pair<Date, String>> availableShifts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.empConditions = empConditions;
        this.startWorkingDate = startWorkingDate;
        this.skills = skills;
        this.License = License;
        this.availableShifts = availableShifts;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")", this.firstName,
                this.lastName, this.id, this.bankAccountNumber, Integer.toString(this.salary), this.empConditions, this.startWorkingDate.toString(), Integer.toString(this.License));
    }

    public String getAvailableShifts(int index) {
        Pair<Date, String> p = this.availableShifts.get(index);
        String currDate = p.first.toString();
        String currType = p.second;
        return String.format("(\"%s\",\"%s\",\"%s\")", this.id, currDate, currType);
    }

    public int getNumberOfAvailableShifts() {
        return this.availableShifts.size();
    }

    public int getNumberOfSkills() {
        return this.skills.size();
    }

    public String getSkills(int index) {
        return String.format("(\"%s\",\"%s\")", this.id, skills.get(index));
    }

    public String getId() {
        return this.id;
    }
}