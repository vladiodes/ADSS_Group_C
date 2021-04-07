package Business;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Employee {
    private String firstName;
    private String lastName;
    private String id;
    private String backAccountNumber;
    private int salary;
    private String empConditions;
    private Date startWorkingDate;
    private List<TypeOfEmployee> skills;
    private List<Pair<Date,TypeOfShift>> availableShifts;


    public Employee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.backAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.empConditions = empConditions;
        this.startWorkingDate = startWorkingDate;
        if(skills== null)
        {
            this.skills = new LinkedList<>();
        }
        else
        {
            this.skills=skills;
        }
        this.availableShifts = new LinkedList<>();
    }
    //-----------------------------------------------------------------getters----------------------------------------------------------

    public Date getStartWorkingDate() {
        return startWorkingDate;
    }

    public int getSalary() {
        return salary;
    }

    public List<TypeOfEmployee> getSkills() {
        return skills;
    }

    public String getBackAccountNumber() {
        return backAccountNumber;
    }

    public List<Pair<Date, TypeOfShift>> getAvailableShifts() {
        return availableShifts;
    }

    public String getEmpConditions() {
        return empConditions;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }
    //------------------------------------------------------------setters----------------------------------------------------------------------------------------------

    public void setAvailableShifts(List<Pair<Date, TypeOfShift>> availableShifts) {
        this.availableShifts = availableShifts;
    }

    public void setBackAccountNumber(String backAccountNumber) {
        this.backAccountNumber = backAccountNumber;
    }

    public void setEmpConditions(String empConditions) {
        this.empConditions = empConditions;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setSkills(List<TypeOfEmployee> skills) {
        this.skills = skills;
    }

    public void setStartWorkingDate(Date startWorkingDate) {
        this.startWorkingDate = startWorkingDate;
    }
}
