package BusinessLayer.Facade;

import BusinessLayer.Controllers.*;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import BusinessLayer.Objects.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class calls functions from scheduleController and staffController
 * Functions that refer/modify employees are called from staffController
 * Functions that refer/modify shifts and schedule are called from scheduleController
 */
public class TransportsEmployeesFacade {
    private TypeOfEmployee typeOfLoggedIn;
    private ScheduleController scheduleController;
    private StaffController staffController;
    private Sites Sit = new Sites();
    private Trucks Tru = new Trucks();
    private Transports Tra;

    //When creating the assignment screen add the skills of each employee
    public TransportsEmployeesFacade(TypeOfEmployee typeOfLoggedIn)
    {
        this.typeOfLoggedIn=typeOfLoggedIn;
        this.staffController=new StaffController(typeOfLoggedIn);
        this.scheduleController=new ScheduleController(typeOfLoggedIn, this.staffController);
        Tra = new Transports(Tru,Sit,staffController);
        loadAllControllers();
    }


    public String addEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) {
        return  staffController.addEmployee(firstName,lastName,id, bankAccountNumber, salary, empConditions, startWorkingDate, skills);
    }
    public String addDriverEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, int License) {
        return staffController.addDriverEmployee(firstName,lastName,id, bankAccountNumber, salary, empConditions, startWorkingDate, License);
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

    public TransportsEmployeesFacade setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
        this.scheduleController.setTypeOfLoggedIn(typeOfLoggedIn);
        this.staffController.setTypeOfLoggedIn(typeOfLoggedIn);
        return this;
    }

    //========================================================Transports========================================================================
    public void addSite(String Ad, String phonenum, String c, String Sec) throws Exception {
        Sit.addSite(Ad, phonenum, c, Sec);
    }

    public void addTruck(String plate, String model, int maxweight, String type, int factoryweight) throws Exception {
        Tru.addTruck(plate, model, maxweight, type, factoryweight);
    }

    public String addTransport(Date date, int weight, String driverID, String TruckID, List<ItemContract> IC, String Source, TypeOfShift TransportationShift) throws Exception {
        if(!scheduleController.shiftContainsEmployee(driverID,date,TransportationShift))
            throw new Exception("Driver not in shift at the time of the transport.");
        if(!scheduleController.shiftContainsTypeOfEmployee(TypeOfEmployee.Storage,date ,TransportationShift))
            throw new Exception("No storage employee at the time of the shift, can't register transport.");
        if(staffController.getEmployeeByID(driverID) == null)
            throw new Exception("Id isn't legal.");
        if(!staffController.getEmployeeByID(driverID).getSkills().contains(TypeOfEmployee.Driver))
            throw new Exception("Requested ID isn't a Driver.");
        Tra.addTransport(date, weight, (Driver)staffController.getEmployeeByID(driverID) , Tru.getTruck(TruckID), IC, Sit.getSite(Source));

        int newDriverAmount = scheduleController.getNumOfConstraint(date, TransportationShift, TypeOfEmployee.Driver);
        if(newDriverAmount==-1)
        {
            this.scheduleController.addConstraint(date, TransportationShift, TypeOfEmployee.Driver,1);
        }
        else
        {
            this.scheduleController.addConstraint(date, TransportationShift, TypeOfEmployee.Driver, newDriverAmount+1);
        }

        int newStorageAmount = scheduleController.getNumOfConstraint(date, TransportationShift, TypeOfEmployee.Storage);
        if(newStorageAmount==-1)
        {
            this.scheduleController.addConstraint(date, TransportationShift, TypeOfEmployee.Storage,1);
        }
        else
        {
            this.scheduleController.addConstraint(date, TransportationShift, TypeOfEmployee.Storage, newStorageAmount+1);
        }
        return "Transport Registered Successfuly.";
    }

    public void addSection(String section) throws Exception {
        Sit.addSection(section);
    }

    public ArrayList<Truck> getAllTrucks() {
        return Tru.getTrucks();
    }

    public ArrayList<Site> getAllSites() {
        return Sit.getSites();
    }

    public List<String> getAllSections() {
        return Sit.getSections();
    }

    public ArrayList<Transport> getAllTransports() {
        return Tra.getTransports();
    }

    public String getSection(String s) throws Exception {
        return Sit.getSection(s);
    }

    public Site getSite(String s) {
        return Sit.getSite(s);
    }

    public void loadAllControllers() {
        this.staffController.getAllEmployees();
        this.scheduleController.getAllShifts();
        this.Sit.Load();
        this.Tru.Load();
        this.Tra.Load();
    }
}
