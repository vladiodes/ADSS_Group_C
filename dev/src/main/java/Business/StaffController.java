package Business;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffController //Singleton
{


    //==================================================================Fields=============================================================
    private Map<String, Employee> employees;
    private TypeOfEmployee typeOfLoggedIn;
    //==================================================================Singleton & Constructor============================================

    private static StaffController instance;
    private StaffController()
    {
        this.employees = new HashMap<>();
    }

    public static StaffController getInstance()
    {
        if(instance == null)
        {
            instance = new StaffController();
        }
        return instance;
    }

    //===================================================================Methods===========================================================

    public void addEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills)
    {
        throw new NotImplementedException();
    }

    public void removeEmployee(String id)
    {
        throw new NotImplementedException();
    }

    public void editFirstName(String id, String firstName)
    {
        throw new NotImplementedException();
    }

    public void editLastName(String id, String lastName)
    {
        throw new NotImplementedException();
    }

    public void editID(String oldId, String newId)
    {
        throw new NotImplementedException();
    }

    public void editBankAccountNumber(String id, String bankAccountNumber)
    {
        throw new NotImplementedException();
    }

    public void editSalary(String id, int salary)
    {
        throw new NotImplementedException();
    }

    public void editEmpConditions(String id, String empConditions)
    {
        throw new NotImplementedException();
    }

    public void addSkill(String id, TypeOfEmployee type)
    {
        throw new NotImplementedException();
    }

    public void removeSkill(String id, TypeOfEmployee type)
    {
        throw new NotImplementedException();
    }
    public void addAvailableShift(String id, Pair<Date, TypeOfShift> shift)
    {
        throw new NotImplementedException();

    }
    public void removeAvailableShift(String id, Pair<Date, TypeOfShift> shift)
    {
        throw new NotImplementedException();
    }


    //-----------------------------------------------------------getters------------------------------------------------------------
    public List<Pair<Date,TypeOfShift>> getAvailableShifts(String id)
    {
        throw new NotImplementedException();
    }

    public List<TypeOfEmployee> getSkills()
    {
        throw new NotImplementedException();
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
    //-------------------------------------------------------------------------setters-----------------------------------------------------



    public void setEmployees(Map<String, Employee> employees) {
        this.employees = employees;
    }

    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
    }

}
