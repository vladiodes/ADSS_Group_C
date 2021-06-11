package BusinessLayer.EmployeesModule.Objects;

import BusinessLayer.Interfaces.persistentObject;
import DTO.EmployeeDTO;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Employee implements persistentObject {
    final int ID_LENGTH = 9; //magic number
    //======================================================Fields=================================================================
    protected String firstName;
    protected String lastName;
    protected String id;
    protected String bankAccountNumber;
    protected int salary;
    protected String empConditions;
    protected Date startWorkingDate;
    protected List<TypeOfEmployee> skills;
    protected List<Pair<Date, TypeOfShift>> availableShifts;

    //======================================================Constructor=================================================================
    public Employee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) throws Exception {
        validityCheckEmp(firstName, lastName, id, bankAccountNumber, salary, empConditions, startWorkingDate, skills);//Checks validity and throws exception if found invalid field
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
        this.bankAccountNumber = bankAccountNumber;
        this.salary = salary;
        this.empConditions = empConditions;
        this.startWorkingDate = startWorkingDate;
        this.skills = skills;
        this.availableShifts = new LinkedList<>();
    }

    public Employee(EmployeeDTO DTO) {
        this.firstName = DTO.firstName;
        this.lastName = DTO.lastName;
        this.id = DTO.id;
        this.bankAccountNumber = DTO.bankAccountNumber;
        this.salary = DTO.salary;
        this.empConditions = DTO.empConditions;
        this.startWorkingDate = DTO.startWorkingDate;
        List<TypeOfEmployee> TypeList = new ArrayList<>();
        for (String el : DTO.skills)
            TypeList.add(TypeOfEmployee.valueOf(el));
        this.skills = TypeList;
        this.availableShifts = availableShiftsFromDTOToBus(DTO.availableShifts);
    }

    private List<Pair<Date, TypeOfShift>> availableShiftsFromDTOToBus(List<Pair<Date, String>> DTOAvaShifts) {
        List<Pair<Date, TypeOfShift>> toReturn = new LinkedList<>();
        for (Pair<Date, String> p : DTOAvaShifts) {
            toReturn.add(new Pair(p.first, TypeOfShift.valueOf(p.second)));
        }
        return toReturn;
    }

    //======================================================Methods=================================================================

    /**
     * Regex taken from https://stackoverflow.com/questions/46326822/java-regex-first-name-validation
     *
     * @param name
     * @return
     */
    private boolean nameValidation(String name) {
        return name != null && name.matches("(?i)(^[a-z]+)[a-z .,-]((?! .,-)$){1,25}$");
    }

    private boolean isValidId(String id) {
        if (id == null || id.length() != ID_LENGTH) {
            return false;
        }
        for (int i = 0; i < id.length(); i++) {
            if (id.charAt(i) < '0' || id.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * Check validity of fields for employee (parameters)
     * Throws exception for each invalid field
     *
     * @param firstName
     * @param lastName
     * @param id
     * @param bankAccountNumber
     * @param salary
     * @param empConditions
     * @param startWorkingDate
     * @param skills
     * @throws Exception
     */
    private void validityCheckEmp(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) throws Exception {
        // maybe add valid check for salary , bank account, start working date
        if (!nameValidation(firstName)) {
            throw new Exception("first name is not valid");
        }
        if (!nameValidation(lastName)) {
            throw new Exception("last name is not valid");
        }
        if (!isValidId(id)) {
            throw new Exception("invalid id");
        }
        if (skills == null || skills.size() == 0) {
            throw new Exception("employee must have skills");
        }
        if (salary < 0) {
            throw new Exception("Salary must be greater than 0");
        }
        if (bankAccountNumber == null || bankAccountNumber.length() <= 0) {
            throw new Exception("Bank account is empty");
        }
        if (empConditions == null) //can be empty
        {
            throw new Exception("Employee conditions cant be null");
        }
        if (startWorkingDate == null) {
            throw new Exception("Date was not inserted");
        }
    }

    public void addSkill(TypeOfEmployee type) throws Exception {
        if (type == TypeOfEmployee.Driver) {
            throw new Exception("Driver skill can not be added");
        }
        if (this.skills.contains(type)) {
            throw new Exception("Skill already exists");
        }
        this.skills.add(type);
    }

    public void removeSkill(TypeOfEmployee type) throws Exception {
        if (this.skills.contains(TypeOfEmployee.Driver))//driver
        {
            if (type == TypeOfEmployee.Driver) {
                throw new Exception("Driver can't remove his driver skill");
            }
        }
        if (this.skills.size() <= 1) //Cant have an employee without skills
        {
            throw new Exception("Employee only has 1 skill and it cannot be removed");
        }
        if (!this.skills.contains(type)) {
            throw new Exception("Skill doesn't exist");
        }
        this.skills.remove(type);
    }

    /**
     * Adds an available shift to the employee by ID
     * Shift's date cant be in the past
     *
     * @param shift
     * @throws Exception
     */
    public void addAvailableShift(Pair<Date, TypeOfShift> shift) throws Exception {
        Date date = shift.first;
        long m = System.currentTimeMillis();
        if (date.before(new Date(m))) //An employee cant request a shift in the past
        {
            throw new Exception("Date of available shift cant be in the past");
        }
        if (this.availableShifts.contains(shift)) {
            throw new Exception("Available shift already exist");
        }
        this.availableShifts.add(shift);
    }

    public void removeAvailableShift(Pair<Date, TypeOfShift> shift) throws Exception {
        if (!this.availableShifts.contains(shift)) {
            throw new Exception("Available shift doesn't exist");
        }
        this.availableShifts.remove(shift);
    }

    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder = new StringBuilder();
        builder.append("Employee: ");
        builder.append("\n\tFirst Name: " + firstName);
        builder.append("\n\tLast Name: " + lastName);
        builder.append("\n\tID: " + id);
        builder.append("\n\tBank Account Number: " + bankAccountNumber);
        builder.append("\n\tSalary: " + salary);
        builder.append("\n\tEmployee Conditions: " + empConditions);
        builder.append("\n\tStart Working Date: " + dateFormat.format(this.startWorkingDate));
        builder.append("\n\tSkills:");
        for (TypeOfEmployee type : skills)
            builder.append("\n\t\t" + type.toString());
        builder.append("\n");
        builder.append("\n\tAvailable Shifts:");
        for (Pair<Date, TypeOfShift> p : availableShifts) {
            builder.append("\n\t\tDate: " + dateFormat.format(p.first));
            builder.append("\n\t\tType: " + p.second.toString() + "\n");
        }
        builder.append("\n");
        return builder.toString();
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

    public String getBankAccountNumber() {
        return bankAccountNumber;
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

    public void setAvailableShifts(List<Pair<Date, TypeOfShift>> availableShifts) throws Exception {
        if (availableShifts == null)
            throw new Exception("available shifts cant be null");
        this.availableShifts = availableShifts;
    }

    public void setBankAccountNumber(String bankAccountNumber) throws Exception {
        if (bankAccountNumber == null || bankAccountNumber.length() <= 0)
            throw new Exception("Bank account is empty");
        this.bankAccountNumber = bankAccountNumber;
    }

    public void setEmpConditions(String empConditions) throws Exception {
        if (empConditions == null) //can be empty
            throw new Exception("Employee conditions cant be null");
        this.empConditions = empConditions;
    }

    public void setFirstName(String firstName) throws Exception {
        if (!nameValidation(firstName))
            throw new Exception("Invalid First Name");
        this.firstName = firstName;
    }

    public void setId(String id) throws Exception {
        if (!isValidId(id))
            throw new Exception("Invalid id");
        this.id = id;
    }

    public void setLastName(String lastName) throws Exception {
        if (!nameValidation(lastName))
            throw new Exception("Invalid Last Name");
        this.lastName = lastName;
    }

    public void setSalary(int salary) throws Exception {
        if (salary < 0)
            throw new Exception("Salary must be greater than 0");
        this.salary = salary;
    }

    public void setSkills(List<TypeOfEmployee> skills) throws Exception {
        if (skills == null || skills.size() == 0)
            throw new Exception("employee must have skills");
        this.skills = skills;
    }

    public void setStartWorkingDate(Date startWorkingDate) throws Exception {
        if (startWorkingDate == null)
            throw new Exception("Date was not inserted");
        this.startWorkingDate = startWorkingDate;
    }

    @Override
    public EmployeeDTO toDTO() {
        return new EmployeeDTO(this.firstName, this.lastName, this.id, this.bankAccountNumber, this.salary, this.empConditions, this.startWorkingDate, skillsToDTO(this.skills), availableShiftsToDTO(this.availableShifts));
    }

    private List<String> skillsToDTO(List<TypeOfEmployee> skillsBusiness) {
        List<String> skills = new LinkedList<>();
        for (TypeOfEmployee type : skillsBusiness) {
            skills.add(type.toString());
        }
        return skills;
    }

    private List<Pair<Date, String>> availableShiftsToDTO(List<Pair<Date, TypeOfShift>> availableShiftBusiness) {
        List<Pair<Date, String>> availableShift = new LinkedList<>();
        for (Pair<Date, TypeOfShift> p : availableShiftBusiness)
            availableShift.add(new Pair<Date, String>(p.first, p.second.toString()));
        return availableShift;
    }
}
