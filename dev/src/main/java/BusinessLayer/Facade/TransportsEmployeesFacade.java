package BusinessLayer.Facade;

import BusinessLayer.EmployeesModule.Controllers.ScheduleController;
import BusinessLayer.EmployeesModule.Controllers.StaffController;
import BusinessLayer.EmployeesModule.Objects.Driver;
import BusinessLayer.EmployeesModule.Objects.Shift;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import BusinessLayer.SuppliersModule.Order;
import BusinessLayer.TransportsModule.Controllers.Sites;
import BusinessLayer.TransportsModule.Controllers.Transports;
import BusinessLayer.TransportsModule.Controllers.Trucks;
import BusinessLayer.TransportsModule.Objects.Site;
import BusinessLayer.TransportsModule.Objects.Transport;
import BusinessLayer.TransportsModule.Objects.Truck;
import Misc.Functions;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import java.time.LocalDate;
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
    private SuppliersController suppliersController;
    private Sites Sit = Sites.getInstance();
    private Trucks Tru = Trucks.getInstance();
    private Transports Tra;

    //When creating the assignment screen add the skills of each employee
    public TransportsEmployeesFacade(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
        this.staffController = StaffController.getInstance();
        staffController.setTypeOfLoggedIn(typeOfLoggedIn);
        suppliersController = SuppliersController.getInstance();
        this.scheduleController = new ScheduleController(typeOfLoggedIn, this.staffController);
        Tra = Transports.getInstance();
        loadAllControllers();
    }

    //Gets list of dates sorted by date from previous to latest
    //Returns list of shifts sorted by date from previous to latest
    public List<Shift> getWeeklyShiftsForTransport(List<LocalDate> dates) {
        ArrayList<Date> dateTimes = new ArrayList<>();
        for(LocalDate d : dates)
            dateTimes.add(Functions.LocalDateToDate(d));
        return this.scheduleController.getWeeklyShiftsForTransport(dateTimes);
    }

    public String addEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, List<TypeOfEmployee> skills) {
        return staffController.addEmployee(firstName, lastName, id, bankAccountNumber, salary, empConditions, startWorkingDate, skills);
    }

    public String addDriverEmployee(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions, Date startWorkingDate, int License) {
        return staffController.addDriverEmployee(firstName, lastName, id, bankAccountNumber, salary, empConditions, startWorkingDate, License);
    }

    public String RemoveEmployee(String id) {
        return staffController.removeEmployee(id, scheduleController);
    }

    public String editFirstName(String idToEdit, String firstName) {
        return staffController.editFirstName(idToEdit, firstName);
    }

    public String editLastName(String idToEdit, String lastName) {
        return staffController.editLastName(idToEdit, lastName);
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

    public String addAvailableShift(String idToEdit, LocalDate date, TypeOfShift type) {
        return this.staffController.addAvailableShift(idToEdit, new Pair<Date, TypeOfShift>(Functions.LocalDateToDate(date), type));
    }

    public String removeAvailableShift(String idToEdit, Date date, TypeOfShift type) {
        return this.staffController.removeAvailableShift(idToEdit, new Pair<Date, TypeOfShift>(date, type));
    }

    public String addSkill(String idToEdit, TypeOfEmployee type) {
        return this.staffController.addSkill(idToEdit, type);
    }

    public String removeSkill(String idToEdit, TypeOfEmployee type) {
        return this.staffController.removeSkill(idToEdit, type);
    }

    public String addEmployeeToShift(String idToEdit, TypeOfEmployee typeEmp, LocalDate date, TypeOfShift typeShift) {
        return this.scheduleController.addEmployeeToShift(idToEdit, typeEmp, Functions.LocalDateToDate(date), typeShift);
    }

    public String removeEmployeeFromShift(String idToEdit, LocalDate date, TypeOfShift typeShift) {
        return this.scheduleController.removeEmployeeFromShift(idToEdit, Functions.LocalDateToDate(date), typeShift);
    }

    public boolean checkIfEmpExist(String idToEdit) {
        return this.staffController.checkIfEmpExist(idToEdit);
    }

    public String addConstraintToShift(LocalDate date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee, Integer numOfEmp) {
        return this.scheduleController.addConstraint(Functions.LocalDateToDate(date), typeOfShift, typeOfEmployee, numOfEmp);
    }

    public String removeConstraintToShift(LocalDate date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee) {
        return this.scheduleController.removeConstraint(Functions.LocalDateToDate(date), typeOfShift, typeOfEmployee);
    }

    public String addShift(LocalDate date, TypeOfShift typeOfShift) {
        return this.scheduleController.addShift(Functions.LocalDateToDate(date), typeOfShift);
    }

    public String removeShift(LocalDate date, TypeOfShift typeOfShift) {
        return this.scheduleController.removeShift(Functions.LocalDateToDate(date), typeOfShift);
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

    public void addTransport(LocalDate date,int weight, String driverID, String TruckID, List<Pair<Integer, Integer>> OrderIDs, TypeOfShift TransportationShift) throws Exception {
        if (!scheduleController.shiftContainsEmployee(driverID, Functions.LocalDateToDate(date), TransportationShift))
            throw new Exception("Driver not in shift at the time of the transport.");
        if (!scheduleController.shiftContainsTypeOfEmployee(TypeOfEmployee.Storage, Functions.LocalDateToDate(date), TransportationShift))
            throw new Exception("No storage employee at the time of the shift, can't register transport.");
        if (staffController.getEmployeeByID(driverID) == null)
            throw new Exception("Id isn't legal.");
        if (!staffController.getEmployeeByID(driverID).getSkills().contains(TypeOfEmployee.Driver))
            throw new Exception("Requested ID isn't a Driver.");
        ArrayList<Order> orders = new ArrayList<>();
        for (Pair<Integer, Integer> pair : OrderIDs) {
            orders.add(suppliersController.getOrder(pair.first, pair.second));
        }
        Tra.addTransport(date,weight, (Driver) staffController.getEmployeeByID(driverID), Tru.getTruck(TruckID), orders); //TODO
        int newDriverAmount = scheduleController.getNumOfConstraint(Functions.LocalDateToDate(date), TransportationShift, TypeOfEmployee.Driver);
        if (newDriverAmount == -1)
            this.scheduleController.addConstraint(Functions.LocalDateToDate(date), TransportationShift, TypeOfEmployee.Driver, 1);
        else
            this.scheduleController.addConstraint(Functions.LocalDateToDate(date), TransportationShift, TypeOfEmployee.Driver, newDriverAmount + 1);
        int newStorageAmount = scheduleController.getNumOfConstraint(Functions.LocalDateToDate(date), TransportationShift, TypeOfEmployee.Storage);
        if (newStorageAmount == -1)
            this.scheduleController.addConstraint(Functions.LocalDateToDate(date), TransportationShift, TypeOfEmployee.Storage, 1);
        else
            this.scheduleController.addConstraint(Functions.LocalDateToDate(date), TransportationShift, TypeOfEmployee.Storage, newStorageAmount + 1);
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

    public Response<Boolean> markTransportAsDelivered(int transportID) {
        try {
            Tra.getTransport(transportID).setDelivered();
            return new Response(true);
        }
        catch (IllegalArgumentException e){
            return new Response(e);
        }
    }
}
