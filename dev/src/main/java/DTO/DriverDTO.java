package DTO;

import Misc.Pair;
import java.util.Date;
import java.util.List;
import static Misc.Functions.DateToString;

public class DriverDTO extends EmployeeDTO {
    public int License;

    public DriverDTO(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions,
                     Date startWorkingDate, int License, List<String> skills, List<Pair<Date, String>> availableShifts) {
        super(firstName, lastName, id, bankAccountNumber, salary, empConditions, startWorkingDate, skills, availableShifts);
        this.License = License;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")", this.firstName,
                this.lastName, this.id, this.bankAccountNumber, Integer.toString(this.salary), this.empConditions, DateToString(this.startWorkingDate), Integer.toString(this.License));
    }

    public String getAvailableShifts(int index) {
        Pair<Date, String> p = this.availableShifts.get(index);
        String currDate = p.first.toString();
        String currType = p.second;
        return String.format("(%s,\"%s\",\"%s\",\"%s\")", null, currDate, currType, this.id);
    }

    public int getNumberOfAvailableShifts() {
        return this.availableShifts.size();
    }

    public int getNumberOfSkills() {
        return this.skills.size();
    }

    public String getSkills(int index) {
        return String.format("(%s,\"%s\",\"%s\")", null, skills.get(index), this.id);
    }

    public String getId() {
        return this.id;
    }
}