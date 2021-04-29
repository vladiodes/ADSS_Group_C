package Business.Controllers;

import Business.Controllers.ScheduleController;
import Business.Controllers.StaffController;
import Business.Misc.Pair;
import Business.Misc.TypeOfEmployee;
import Business.Misc.TypeOfShift;

import java.util.Date;
import java.util.List;

/**
 * This class calls functions from scheduleController and staffController
 * Functions that refer/modify employees are called from staffController
 * Functions that refer/modify shifts and schedule are called from scheduleController
 */
public class EmployeesFacade {
    private TypeOfEmployee typeOfLoggedIn;
    private ScheduleController scheduleController;
    private StaffController staffController;


    //When creating the assignment screen add the skills of each employee
    public EmployeesFacade(TypeOfEmployee typeOfLoggedIn)
    {
        this.typeOfLoggedIn=typeOfLoggedIn;
        this.staffController=new StaffController(typeOfLoggedIn);
        this.scheduleController=new ScheduleController(typeOfLoggedIn, this.staffController);
    }


    public String addEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) {
        return  staffController.addEmployee(firstName,lastName,id, bankAccountNumber, salary, empConditions, startWorkingDate, skills);
    }

    public String RemoveEmployee(String id) {
        return staffController.removeEmployee(id, scheduleController);
    }

    public String editFirstName(String idToEdit, String firstName) {
        return staffController.editFirstName(idToEdit, firstName);
    }

    public String editLastName(String idToEdit, String lastName) {
        return staffController.editLastName(idToEdit,lastName);
    }

    public String editID(String idToEdit, String newId) {
        return staffController.editID(idToEdit, newId);
    }

    public String editBankAccountNumber(String idToEdit, String newBankAccountNumber) {
        return staffController.editBankAccountNumber(idToEdit, newBankAccountNumber);
    }

    public String editSalary(String idToEdit, int newSalary) {
        return staffController.editSalary(idToEdit, newSalary);
    }

    public String editEmpConditions(String idToEdit, String newEmpConditions) {
        return staffController.editEmpConditions(idToEdit, newEmpConditions);
    }

    public String addAvailableShift(String idToEdit, Date date, TypeOfShift type) {
        return this.staffController.addAvailableShift(idToEdit, new Pair<Date, TypeOfShift>(date,type));
    }

    public String removeAvailableShift(String idToEdit, Date date, TypeOfShift type) {
        return this.staffController.removeAvailableShift(idToEdit , new Pair<Date, TypeOfShift>(date, type));
    }

    public String addSkill(String idToEdit, TypeOfEmployee type) {
        return this.staffController.addSkill(idToEdit,type);
    }

    public String removeSkill(String idToEdit, TypeOfEmployee type) {
        return this.staffController.removeSkill(idToEdit,type);
    }

    public String addEmployeeToShift(String idToEdit, TypeOfEmployee typeEmp, Date date, TypeOfShift typeShift) {
        return this.scheduleController.addEmployeeToShift(idToEdit,typeEmp, date, typeShift);
    }

    public String removeEmployeeFromShift(String idToEdit, Date date, TypeOfShift typeShift) {
        return this.scheduleController.removeEmployeeFromShift(idToEdit, date, typeShift);
    }

    public boolean checkIfEmpExist(String idToEdit) {
        return this.staffController.checkIfEmpExist(idToEdit);
    }

    public String addConstraintToShift(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee, Integer numOfEmp) {
        return this.scheduleController.addConstraint(date, typeOfShift,typeOfEmployee,numOfEmp);
    }

    public String removeConstraintToShift(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee) {
        return this.scheduleController.removeConstraint(date, typeOfShift,typeOfEmployee);
    }

    public String addShift(Date date, TypeOfShift typeOfShift) {
       return this.scheduleController.addShift(date,typeOfShift);
    }

    public String removeShift(Date date, TypeOfShift typeOfShift) {
        return this.scheduleController.removeShift(date,typeOfShift);
    }

    public String printSchedule() {
        return this.scheduleController.toString();
    }

    public String printPersonalDetails(String idToPrint) {
        return this.staffController.printPersonalDetails(idToPrint);
    }

    public TypeOfEmployee getTypeOfLoggedIn() {
        return typeOfLoggedIn;
    }

    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
        this.scheduleController.setTypeOfLoggedIn(typeOfLoggedIn);
        this.staffController.setTypeOfLoggedIn(typeOfLoggedIn);
        return this.typeOfLoggedIn;
    }

    public void setTypeOfLoggedIn(TypeOfEmployee type) {
        this.typeOfLoggedIn=type;
    }
}
