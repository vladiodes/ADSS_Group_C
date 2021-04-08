package Business.Controllers;

import Business.Employee;
import Business.Pair;
import Business.TypeOfEmployee;
import Business.TypeOfShift;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffController
{



    //==================================================================Fields=============================================================
    private Map<String, Employee> employees;
    private TypeOfEmployee typeOfLoggedIn;
    //==================================================================Singleton & Constructor============================================


    public StaffController(TypeOfEmployee type)
    {
        this.employees = new HashMap<>();
        this.typeOfLoggedIn = type;
    }

    //===================================================================Methods===========================================================
    private Employee getEmpIfExists(String id) throws Exception
    {
        if(!this.employees.containsKey(id))
        {
            throw new Exception("Not such employee");
        }
        return this.employees.get(id);
    }

    /**
     * The check for duplicate id is done before calling this function
     * @param firstName
     * @param lastName
     * @param id Assumes given id doesnt already exist
     * @param bankAccountNumber
     * @param salary
     * @param empConditions
     * @param startWorkingDate
     * @param skills
     * @return
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

    public String removeEmployee(String id)
    {
        if(employees.remove(id)==null)
            return "Employee not found";
        return "Employee removed successfully";
    }

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


        return "bank Account Name was edited successfully";
    }

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
        return "available shift added successfully";
    }
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
        return "available shift was removed successfully";
    }


    //-----------------------------------------------------------getters------------------------------------------------------------


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
    //-------------------------------------------------------------------------setters-----------------------------------------------------



    public void setEmployees(Map<String, Employee> employees) {
        this.employees = employees;
    }

    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
    }

    public boolean checkIfEmpExist(String idToEdit) {
        return this.employees.containsKey(idToEdit);
    }
}
