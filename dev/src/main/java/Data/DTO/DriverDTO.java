package Data.DTO;

import Misc.Pair;
import java.util.Date;
import java.util.List;

public class DriverDTO extends EmployeeDTO{
    public int License;

    public DriverDTO(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions,
                     Date startWorkingDate, int License, List<String> skills, List<Pair<Date, String>> availableShifts) {
        super(firstName,lastName,id,bankAccountNumber,salary,empConditions,startWorkingDate,skills,availableShifts);
        this.License = License;
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