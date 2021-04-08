package Business.Controllers;


import Business.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static Business.TypeOfEmployee.*;
public class ScheduleController {




    private TypeOfEmployee typeOfLoggedIn;
    private Map<Date, DailySchedule> schedule;
    private StaffController staffController; //Add to documentation

    public ScheduleController(TypeOfEmployee type, StaffController sc)
    {
        this.staffController = sc;
        this.typeOfLoggedIn =type;
        this.schedule = new HashMap<>();
    }


    public String addShift(Date date, TypeOfShift type)
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
            Shift toAddShift = new Shift(type, date);
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


    public String addConstraint(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee, Integer numOfEmp) {
        if (typeOfLoggedIn!= HRManager)
        {
            return "only a HR Manager is allowed to modify number and type of employees in a shift";
        }
        try
        {
            if (!this.schedule.containsKey(date))
            {
                return "no such shift";
            }
            DailySchedule dailySchedule=this.schedule.get(date);
            if (!dailySchedule.isTypeOfShiftExists(typeOfShift))
            {
                return "no such shift";
            }
            Shift shift = dailySchedule.getShift(typeOfShift);
            shift.addConstraint(typeOfEmployee, numOfEmp);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "constraint added successfully";
    }

    public String removeConstraint(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee)
    {
        if (typeOfLoggedIn!= HRManager)
        {
            return "only a HR Manager is allowed to modify number and type of employees in a shift";
        }
        try
        {
            if (!this.schedule.containsKey(date))
            {
                return "no such shift";
            }
            DailySchedule dailySchedule=this.schedule.get(date);
            if (!dailySchedule.isTypeOfShiftExists(typeOfShift))
            {
                return "no such shift";
            }
            Shift shift = dailySchedule.getShift(typeOfShift);
            shift.removeConstraint(typeOfEmployee);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "constraint removed successfully";
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder=new StringBuilder();

        for(Date d:schedule.keySet())
        {
            builder.append("\nDate Of Daily Schedule: " + dateFormat.format(d));
            builder.append("\n"+ schedule.get(d).toString());
        }
        builder.append("\n");
        return builder.toString();
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
