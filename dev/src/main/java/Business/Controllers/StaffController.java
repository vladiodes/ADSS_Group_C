package Business.Controllers;

import Business.Misc.Pair;
import Business.Misc.TypeOfEmployee;
import Business.Misc.TypeOfShift;
import Business.Objects.Driver;
import Business.Objects.Employee;
import Business.Objects.Shift;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffController
{



    //==================================================================Fields=============================================================
    private Map<String, Employee> employees;
    private TypeOfEmployee typeOfLoggedIn;
    //==================================================================Constructor============================================


    public StaffController(TypeOfEmployee type)
    {
        this.employees = new HashMap<>();
        this.typeOfLoggedIn = type;
    }

    //===================================================================Methods===========================================================


    /**
     * Adds a new Employee
     * The check for duplicate id is done before calling this function
     * @param firstName
     * @param lastName
     * @param id Assumes given id doesnt already exist
     * @param bankAccountNumber
     * @param salary
     * @param empConditions
     * @param startWorkingDate
     * @param skills
     * @return Success/Fail Message
     */
    public String addEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills)
    {
        try
        {
            Employee employee = new Employee(firstName, lastName,id,bankAccountNumber,salary,empConditions,startWorkingDate, skills); //throws exception if fields are invalid
            this.employees.put(id, employee);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Employee added successfully";
    }
    public String addDriverEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills, int license) {
        try
        {
            Employee employee = new Driver(firstName, lastName,id,bankAccountNumber,salary,empConditions,startWorkingDate, skills, license); //throws exception if fields are invalid
            this.employees.put(id, employee);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        //add insert
        return "Driver Employee added successfully";
    }

    /**
     * Removes an existing employee
     * @param id
     * @return Success/Fail Message
     */
    public String removeEmployee(String id, ScheduleController scheduleController)
    {
        if(employees.remove(id)==null)
            return "Employee not found";
        //remove the employee from all of its shifts
        List<Shift> shiftWithEmp = scheduleController.getShiftWithEmp(id);
        for (Shift s:shiftWithEmp) {
            s.removeEmployee(id);

        }
        return "Employee removed successfully";
    }

    /**
     * Edit First Name of employee by ID
     * @param id
     * @param firstName
     * @return Success/Fail Message
     */
    public String editFirstName(String id, String firstName)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.setFirstName(firstName);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "First Name was edited successfully";
    }

    /**
     * Edit Last Name of employee by ID
     * @param id
     * @param lastName
     * @return Success/Fail Message
     */
    public String editLastName(String id, String lastName)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.setLastName(lastName);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Last Name was edited successfully";
    }


    /**
     * Edit ID of employee by ID
     * @param oldId
     * @param newId
     * @return Success/Fail Message
     */
    public String editID(String oldId, String newId)
    {
        try
        {
            Employee e = getEmpIfExists(oldId);
            this.employees.remove(oldId);
            e.setId(newId);
            this.employees.put(newId, e);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "ID was edited successfully";
    }

    /**
     * Edit Bank Account Number of employee by ID
     * @param id
     * @param bankAccountNumber
     * @return Success/Fail Message
     */
    public String editBankAccountNumber(String id, String bankAccountNumber)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.setBankAccountNumber(bankAccountNumber);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }


        return "Bank Account number was edited successfully";
    }

    /**
     * Edit Salary of employee by ID
     * @param id
     * @param salary
     * @return Success/Fail Message
     */
    public String editSalary(String id, int salary)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.setSalary(salary);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

        return "Salary was edited successfully";


    }

    /**
     * Edit Employee Conditions employee by ID
     * @param id
     * @param empConditions
     * @return Success/Fail Message
     */
    public String editEmpConditions(String id, String empConditions)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.setEmpConditions(empConditions);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

        return "employee conditions was edited successfully";

    }


    /**
     * Adds a skill to the employee by id
     * @param id
     * @param type
     * @return Success/Fail Message
     */
    public String addSkill(String id, TypeOfEmployee type)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.addSkill(type);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

        return "skill added successfully";
    }


    /**
     * Removes a skill from the employee by id
     * @param id
     * @param type
     * @return Success/Fail Message
     */
    public String removeSkill(String id, TypeOfEmployee type)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.removeSkill(type);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "skill was removed successfully";
    }


    /**
     * Adds a shift to the list of available shifts for the user by id
     * @param id
     * @param shift
     * @return Success/Fail Message
     */
    public String addAvailableShift(String id, Pair<Date, TypeOfShift> shift)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.addAvailableShift(shift);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Available shift added successfully";
    }

    /**
     * Removes a shift from the list of available shifts for the user by id
     * @param id
     * @param shift
     * @return Success/Fail Message
     */
    public String removeAvailableShift(String id, Pair<Date, TypeOfShift> shift)
    {
        try
        {
            Employee e = getEmpIfExists(id);
            e.removeAvailableShift(shift);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Available shift was removed successfully";
    }
    public boolean checkIfEmpExist(String idToEdit) {
        return this.employees.containsKey(idToEdit);
    }
    public String printPersonalDetails(String idToPrint) {
        try
        {
            return this.employees.get(idToPrint).toString();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }


    //-----------------------------------------------------------getters------------------------------------------------------------


    private Employee getEmpIfExists(String id) throws Exception
    {
        if(!this.employees.containsKey(id))
        {
            throw new Exception("No such employee");
        }
        return this.employees.get(id);
    }

    public Employee getEmployeeByID(String id)
    {
        if(!this.employees.containsKey(id))
            return null;
        return this.employees.get(id);
    }

    public Map<String, Employee> getEmployees() {
        return employees;
    }

    public TypeOfEmployee getTypeOfLoggedIn() {
        return typeOfLoggedIn;
    }
    //------------------------------------------------------------setters------------------------------------------------------------



    public void setEmployees(Map<String, Employee> employees) {
        this.employees = employees;
    }

    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
    }



}
