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
}
