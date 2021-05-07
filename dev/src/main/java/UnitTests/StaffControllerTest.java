package UnitTests;

import Business.Controllers.ScheduleController;
import Business.Controllers.StaffController;
import Misc.Pair;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import org.junit.After;
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

    @After
    public void tearDown() throws Exception {
        staffController = new StaffController(TypeOfEmployee.HRManager);
        scheduleController = new ScheduleController(TypeOfEmployee.HRManager, staffController);
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
        int sizeOfEmp = this.staffController.getEmployees().size();
        this.staffController.addEmployee("Ofri","Arad", "123456789", "777/23", 12000, "Sick days :6", new Date(),skills );
        //check if new employee was added to the hash map
        assertEquals(sizeOfEmp, this.staffController.getEmployees().size()-1);

    }


    @Test
    public void editFirstName() {
        skills=new LinkedList<>();
        skills.add(TypeOfEmployee.HRManager);
        // add new Employee
        this.staffController.addEmployee("Leo","Messi", "101010101", "777/23", 12000, "Sick days :6", new Date(),skills );
        //try edit wrong first name - sole letter name
        String expected = "Invalid First Name";
        String actual = this.staffController.editFirstName("101010101", "C");
        assertEquals(expected, actual);
    }


    @Test
    public void removeAvailableShift_DoesntExist() {
        String expected = "Available shift doesn't exist";
        String actual = this.staffController.removeAvailableShift("209012384", new Pair<Date, TypeOfShift>(getDate(), TypeOfShift.Morning));
        assertEquals(expected, actual);
    }
    @Test
    public void addAvailableShift_WrongDate() {
        String expected = "Date of available shift cant be in the past";
        String actual = this.staffController.addAvailableShift("209012384", new Pair<Date, TypeOfShift>(getWrongDate(), TypeOfShift.Morning));
        assertEquals(expected, actual);
    }
}