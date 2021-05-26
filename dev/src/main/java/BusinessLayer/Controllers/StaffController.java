package BusinessLayer.Controllers;

import DataAccessLayer.DriverDAO;
import DataAccessLayer.EmployeeDAO;
import DataAccessLayer.ShiftDAO;
import DTO.DriverDTO;
import DTO.EmployeeDTO;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import BusinessLayer.Objects.Driver;
import BusinessLayer.Objects.Employee;
import BusinessLayer.Objects.Shift;

import java.util.*;

public class StaffController
{



    //==================================================================Fields=============================================================
    private Map<String, Employee> employees;
    private TypeOfEmployee typeOfLoggedIn;

    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private DriverDAO driverDAO = new DriverDAO();
    private ShiftDAO shiftDAO = new ShiftDAO();

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
            if(this.employees.containsKey(id))
            {
                throw new Exception("ID already exists");
            }
            this.employees.put(id, employee);
            this.employeeDAO.insert(employee.toDTO());//add to DB
        }
        catch (Exception e)
        {
            return e.getMessage();
        }

        return "Employee added successfully";
    }
    public String addDriverEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, int license) {
        try
        {
            if(this.employees.containsKey(id))
            {
                throw new Exception("ID already exists");
            }
            List<TypeOfEmployee> skills=new LinkedList<>();
            skills.add(TypeOfEmployee.Driver);
            Driver driver = new Driver(firstName, lastName,id,bankAccountNumber,salary,empConditions,startWorkingDate, skills, license); //throws exception if fields are invalid
            this.employees.put(id, driver);
            this.driverDAO.insert(driver.toDTO());//add to DB check
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Driver Employee added successfully";
    }

    /**
     * Removes an existing employee
     * @param id
     * @return Success/Fail Message
     */
    public String removeEmployee(String id, ScheduleController scheduleController)
    {
        Employee emp=getEmployeeByID(id);
        if(employees.remove(id)==null)
            return "Employee not found";
        //remove the employee from all of its shifts
        List<Shift> shiftWithEmp = scheduleController.getShiftWithEmp(id);
        for (Shift s:shiftWithEmp) {
            s.removeEmployee(id);
        }
        if(emp.getSkills().contains(TypeOfEmployee.Driver))
        {
            this.driverDAO.delete("ID",id);
            return "Driver removed successfully";
        }
        else
        {
            this.employeeDAO.delete("ID", id);//DB
            return "Employee removed successfully";
        }

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
            EmployeeDTO updatedOb= e.toDTO();
            checkIfDriverAndUpdateDAO(e, updatedOb);

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
            EmployeeDTO updatedOb= e.toDTO();
            checkIfDriverAndUpdateDAO(e, updatedOb);

        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Last Name was edited successfully";
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
            EmployeeDTO updatedOb= e.toDTO();
            checkIfDriverAndUpdateDAO(e, updatedOb);
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
            EmployeeDTO updatedOb= e.toDTO();
            checkIfDriverAndUpdateDAO(e, updatedOb);
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
            EmployeeDTO updatedOb= e.toDTO();
            checkIfDriverAndUpdateDAO(e, updatedOb);
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
            if(e.getSkills().contains(TypeOfEmployee.Driver))//driver
            {
                this.driverDAO.addSkill(id,type.toString());//DB
            }
            else
            {
                this.employeeDAO.addSkill(id,type.toString());//DB
            }

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
            if(e.getSkills().contains(TypeOfEmployee.Driver))//driver
            {
                this.driverDAO.removeSkill(id,type.toString());//DB
            }
            else
            {
                this.employeeDAO.removeSkill(id,type.toString());//DB
            }
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
            if(e.getSkills().contains(TypeOfEmployee.Driver))//driver
            {
                this.driverDAO.addAvailableShifts(id, shift.first, shift.second.toString());//DB
            }
            else
            {
                this.employeeDAO.addAvailableShifts(id, shift.first, shift.second.toString());//DB
            }

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
            if(e.getSkills().contains(TypeOfEmployee.Driver))//driver
            {
                this.driverDAO.removeAvailableShifts(id, shift.first, shift.second.toString());//DB
            }
            else
            {
                this.employeeDAO.removeAvailableShifts(id, shift.first, shift.second.toString());//DB
            }
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

    public void getAllEmployees()
    {
        List<EmployeeDTO> allEmps = this.employeeDAO.getAll();
        List<DriverDTO> allDrivers = this.driverDAO.getAll();
        for (EmployeeDTO emp:allEmps)
        {
            this.employees.put(emp.id, new Employee(emp));

        }
        for (DriverDTO driver:allDrivers)
        {
            this.employees.put(driver.id, new Driver(driver));

        }

    }
    private void checkIfDriverAndUpdateDAO(Employee e, EmployeeDTO updatedOb)
    {
        if(e.getSkills().contains(TypeOfEmployee.Driver))//driver
        {
            this.driverDAO.update((DriverDTO)updatedOb);//DB
        }
        else
        {
            this.employeeDAO.update(updatedOb);//DB
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


    //------------------------------------------------------------setters------------------------------------------------------------

    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
    }






}
