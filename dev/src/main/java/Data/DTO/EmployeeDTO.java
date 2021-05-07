package Data.DTO;

import Business.Misc.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDTO {
    public String firstName;
    public String lastName;
    public String id;
    public String bankAccountNumber;
    public int salary;
    public String empConditions;
    public Date startWorkingDate;
    public List<String> skills;
    public List<Pair<Date, String>> availableShifts;

    public EmployeeDTO(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<String> skills, List<Pair<Date, String>> availableShifts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.empConditions = empConditions;
        this.startWorkingDate = startWorkingDate;
        this.skills = skills;
        this.availableShifts = availableShifts;
    }

    public String fieldsToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",%s,\"%s\",\"%s\")", this.firstName, this.lastName, this.id, this.bankAccountNumber, Integer.toString(this.salary), this.empConditions,formatter.format(this.startWorkingDate));
    }

    public String getAvailableShifts(int index) {
        Pair<Date, String> p = this.availableShifts.get(index);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date currDate = p.first;
        String currType = p.second;
        return String.format("(\"%s\",\"%s\",\"%s\")", this.id, formatter.format(currDate), currType);
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