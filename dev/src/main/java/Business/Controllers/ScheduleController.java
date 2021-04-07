package Business.Controllers;


import Business.*;

import java.util.*;
import static Business.TypeOfEmployee.*;
public class ScheduleController {




    private TypeOfEmployee typeOfLoggedIn;
    private Map<Date, DailySchedule> schedule;
    private StaffController staffController; //Add to documentation
    private static ScheduleController instance;

    public ScheduleController(TypeOfEmployee type, StaffController sc)
    {
        this.staffController = sc;
        this.typeOfLoggedIn =type;
        this.schedule = new HashMap<>();
    }


    public String addShift(Date date, TypeOfShift type, Map<TypeOfEmployee, Integer> constraints)
    {
        //Only HRManager can add shifts
        if(this.typeOfLoggedIn!=HRManager)
        {
            return "Only HRManager can add shifts";
        }
        if(isShiftExists(date, type))
        {
            return "Shift already exists";
        }
        try
        {
            Shift toAddShift = new Shift(type, date, constraints, new LinkedList<Pair<Employee, TypeOfEmployee>>());
            if(!schedule.containsKey(date))
            {
                DailySchedule dailySchedule = new DailySchedule(toAddShift);
                this.schedule.put(date,dailySchedule);
            }
            else
            {
                schedule.get(date).addShift(toAddShift);
            }
        }
        catch(Exception e)
        {
            return e.getMessage();
        }
        return "Shift added successfully";
    }

    private boolean isShiftExists(Date date, TypeOfShift type) {
        if(!schedule.containsKey(date))
        {
            return false;
        }
        DailySchedule cur = schedule.get(date);
        return cur.isTypeOfShiftExists(type);

    }

    public String removeShift(Date date, TypeOfShift type)
    {
        //Only HRManager can remove shifts
        if(this.typeOfLoggedIn!=HRManager)
        {
            return "Only HRManager can remove shifts";
        }
        if(!isShiftExists(date,type))
        {
            return "Shift doesnt exist";
        }
        DailySchedule dailySchedule = this.schedule.get(date);
        dailySchedule.removeShift(date,type);
        return "Shift was removed successfully";
    }


    public String addEmployeeToShift(String id,TypeOfEmployee toSkill, Date date, TypeOfShift type)
    {
        if(!isShiftExists(date, type))
        {
            return "Shift doesnt exist";
        }
        try
        {
            Shift s= getShift(date,type);
            s.addEmployeeToShift(staffController.getEmployeeByID(id), toSkill);
        }
        catch(Exception e)
        {
            return  e.getMessage();
        }

        return "Employee added successfully to shift";
    }

    public String removeEmployeeFromShift(String id,Date date, TypeOfShift type)
    {
        if(!isShiftExists(date,type))
        {
            return "Shift doesnt exist";
        }
        Shift s= getShift(date,type);
        if(!s.isEmployeeInShift(id)) //Check if the employee is in the shift
        {
            return "shift doesn't contain this employee";
        }

        s.removeEmployee(id);
        return "employee removed successfully from shift";
    }
    public Shift getShift(Date date,TypeOfShift type)
    {
        DailySchedule ds = schedule.get(date);
        Shift s = ds.getShift(type);
        return s;
    }
    //-----------------------------------------------------getters-----------------------------------------------

    public TypeOfEmployee getTypeOfLoggedIn() {
        return typeOfLoggedIn;
    }

    public StaffController getStaffController() {
        return staffController;
    }

    public Map<Date,DailySchedule> getSchedule()
    {
        return this.schedule;
    }
    //---------------------------------------------------setters--------------------------------------------------
    public void setTypeOfLoggedIn(TypeOfEmployee typeOfLoggedIn) {
        this.typeOfLoggedIn = typeOfLoggedIn;
    }

    public void setSchedule(Map<Date, DailySchedule> schedule) {
        this.schedule = schedule;
    }

    public void setStaffController(StaffController staffController) {
        this.staffController = staffController;
    }

}
