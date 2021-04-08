package tests;

import Business.Controllers.ScheduleController;
import Business.Controllers.StaffController;
import Business.Pair;
import Business.TypeOfEmployee;
import Business.TypeOfShift;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class StaffControllerTest {

    private StaffController staffController;
    private ScheduleController scheduleController;
    private List<TypeOfEmployee> mainSkills;
    private List<TypeOfEmployee> skills;
    @Before
    public void setUp() throws Exception{
        staffController = new StaffController(TypeOfEmployee.HRManager);
        scheduleController = new ScheduleController(TypeOfEmployee.HRManager, staffController);
        mainSkills=new LinkedList<>();
        mainSkills.add(TypeOfEmployee.HRManager);
        this.staffController.addEmployee("Tom","Nisim", "209012384", "777/23", 13000, "Sick days :0", new Date(),mainSkills );
    }

    private Date getDate()
    {
        Date date= null;
        try
        {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022");
        }
        catch (Exception e)
        {
            System.out.println("Invalid date");
        }
        return date;
    }
    private Date getWrongDate()
    {
        Date date= null;
        try
        {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2019");
        }
        catch (Exception e)
        {
            System.out.println("Invalid date");
        }
        return date;
    }
    @Test
    public void addEmployee() {

        skills=new LinkedList<>();
        skills.add(TypeOfEmployee.HRManager);
        this.staffController.addEmployee("Ofri","Arad", "123456789", "777/23", 12000, "Sick days :6", new Date(),skills );
        //check if new employee was added to the hash map
        assertEquals(1, this.staffController.getEmployees().size());

    }

    @Test
    public void removeEmployee() {

    }

    @Test
    public void editFirstName() {
        skills=new LinkedList<>();
        skills.add(TypeOfEmployee.HRManager);
        // add new Employee
        this.staffController.addEmployee("Leo","Messi", "101010101", "777/23", 12000, "Sick days :6", new Date(),skills );
        //try edit wrong first name - sole letter name
        try
        {
            this.staffController.editFirstName("101010101", "C");
        }
        catch (Exception e)
        {
            assertEquals("Invalid First Name", e.getMessage());
        }

    }

    @Test
    public void editLastName() {
    }

    @Test
    public void editID() {
    }

    @Test
    public void editBankAccountNumber() {
    }

    @Test
    public void editSalary() {
    }

    @Test
    public void editEmpConditions() {
    }

    @Test
    public void addSkill() {
    }

    @Test
    public void removeSkill() {
    }

    @Test
    public void addAvailableShift() {
    }

    @Test
    public void removeAvailableShift_DoesntExist() {
        try
        {
            this.staffController.removeAvailableShift("209012384", new Pair<Date, TypeOfShift>(getDate(), TypeOfShift.Morning));
        }
        catch (Exception e)
        {
            assertEquals("available shift already exist", e.getMessage());
        }

    }
    @Test
    public void removeAvailableShift_WrongDate() {
        try
        {
            this.staffController.removeAvailableShift("209012384", new Pair<Date, TypeOfShift>(getWrongDate(), TypeOfShift.Morning));
        }
        catch (Exception e)
        {
            assertEquals("date of available shift cant be in the past", e.getMessage());
        }

    }
}