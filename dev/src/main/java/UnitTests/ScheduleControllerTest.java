package UnitTests;

import Business.Controllers.ScheduleController;
import Business.Controllers.StaffController;
import Misc.TypeOfEmployee;
import Misc.TypeOfShift;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ScheduleControllerTest {


    private StaffController staffController;
    private ScheduleController scheduleController;
    @Before
    public void setUp() throws Exception{
        staffController = new StaffController(TypeOfEmployee.HRManager);
        scheduleController = new ScheduleController(TypeOfEmployee.HRManager, staffController);
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

    @Test
    public void addShift() {
        Date date=getDate();
        TypeOfShift type = TypeOfShift.Morning;
        Map<TypeOfEmployee, Integer> constraints= new HashMap<>();
        constraints.put(TypeOfEmployee.ShiftManager, 1);
        this.scheduleController.addShift(date,type);
        assertEquals(1, scheduleController.getSchedule().size());
    }

    @Test
    public void addExistingShift() {
        Date date=getDate();
        TypeOfShift type = TypeOfShift.Morning;
        Map<TypeOfEmployee, Integer> constraints= new HashMap<>();
        constraints.put(TypeOfEmployee.ShiftManager, 1);
        this.scheduleController.addShift(date,type);
        String expected = "Shift already exists";
        String actual = this.scheduleController.addShift(date,type);
        assertEquals(expected, actual);
    }

    @Test
    public void removeShift_doesntExist() {
        String expected = "Shift doesn't exist";
        String actual = this.scheduleController.removeShift(getDate(),TypeOfShift.Evening);
        assertEquals(expected, actual);
    }



    @Test
    public void removeEmployeeFromShift() {
            String expected = "Shift doesn't exist";
            String actual = this.scheduleController.removeEmployeeFromShift("209012384", getDate(), TypeOfShift.Evening);
            assertEquals(expected, actual);
    }

    @Test
    public void addConstraint_NegativeConstraint() {
        //generate shift
        this.scheduleController.addShift(getDate(), TypeOfShift.Morning);
        String expected = "Amount of Employees must be positive";
        String actual = this.scheduleController.addConstraint(getDate(), TypeOfShift.Morning,TypeOfEmployee.Cashier, -1 );
        assertEquals(expected, actual);
    }

    @Test
    public void addConstraint_shiftDoesntExist() {
        String expected = "No such shift";
        String actual = this.scheduleController.addConstraint(getDate(), TypeOfShift.Morning, TypeOfEmployee.Storage, 5);
        assertEquals(expected, actual);
    }
    @Test
    public void addConstraint_shiftManagerConstraint() {
        this.scheduleController.addShift(getDate(), TypeOfShift.Morning);
        String expected = "Constraint of type ShiftManager must be 1 or greater";
        String actual = this.scheduleController.addConstraint(getDate(), TypeOfShift.Morning, TypeOfEmployee.ShiftManager, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void removeConstraint() {
        this.scheduleController.addShift(getDate(), TypeOfShift.Morning);
        String expected = "Number of ShiftManagers in a shift must be restricted";
        String actual = this.scheduleController.removeConstraint(getDate(), TypeOfShift.Morning, TypeOfEmployee.ShiftManager);
        assertEquals(expected, actual);
    }
}