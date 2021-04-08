package tests;

import Business.Controllers.ScheduleController;
import Business.Controllers.StaffController;
import Business.Shift;
import Business.TypeOfEmployee;
import Business.TypeOfShift;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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

        //add existing shift
        try
        {
            this.scheduleController.addShift(date,type);
        }
        catch (Exception e)
        {
            assertEquals("Shift already exists", e.getMessage());
        }


    }

    @Test
    public void removeShift_doesntExist() {
        try
        {
            this.scheduleController.removeShift(getDate(), TypeOfShift.Evening);
        }
        catch (Exception e)
        {
            assertEquals("Shift doesnt exist", e.getMessage());
        }

    }

    @Test
    public void addEmployeeToShift() {
    }

    @Test
    public void removeEmployeeFromShift() {

        try
        {
            this.scheduleController.removeEmployeeFromShift("209012384", getDate(), TypeOfShift.Evening);
        }
        catch (Exception e)
        {
            assertEquals("shift doesn't contain this employee", e.getMessage());
        }
    }

    @Test
    public void addConstraint_NegativeConstraint() {

        //generate shift
        this.scheduleController.addShift(getDate(), TypeOfShift.Morning);
        try
        {
            this.scheduleController.addConstraint(getDate(), TypeOfShift.Morning,TypeOfEmployee.Cashier, -1 );
        }
        catch (Exception e)
        {
            assertEquals("amount of Employees must be positive", e.getMessage());
        }
    }

    @Test
    public void addConstraint_shiftDoesntExist() {
        try
        {
            this.scheduleController.addConstraint(getDate(), TypeOfShift.Morning, TypeOfEmployee.Storage, 5);
        }
        catch (Exception e)
        {
            assertEquals("no such shift", e.getMessage());
        }

    }
    @Test
    public void addConstraint_shiftManagerConstraint() {

        this.scheduleController.addShift(getDate(), TypeOfShift.Morning);
        try
        {
            this.scheduleController.addConstraint(getDate(), TypeOfShift.Morning, TypeOfEmployee.ShiftManager, 0);
        }
        catch (Exception e)
        {
            assertEquals("Constraint of type ShiftManager must be 1 or greater", e.getMessage());
        }

    }

    @Test
    public void removeConstraint() {

        this.scheduleController.addShift(getDate(), TypeOfShift.Morning);
        try
        {
            this.scheduleController.removeConstraint(getDate(), TypeOfShift.Morning, TypeOfEmployee.ShiftManager);
        }
        catch (Exception e)
        {
            assertEquals("Number of ShiftManagers in a shift must be restricted", e.getMessage());
        }
    }
}