package Data.DTO;

import Business.Misc.Pair;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class DriverDTO {
    private String firstName;
    private String lastName;
    private String id;
    private String bankAccountNumber;
    private int salary;
    private String empConditions;
    private Date startWorkingDate;
    private List<TypeOfEmployeeDTO> skills;
    private List<Pair<Date, TypeOfShiftDTO>> availableShifts;
    public int License;

    public DriverDTO(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployeeDTO> skills, int License)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.empConditions = empConditions;
        this.startWorkingDate = startWorkingDate;
        this.skills=skills;
        this.availableShifts = new LinkedList<>();
        this.License=License;
    }
}