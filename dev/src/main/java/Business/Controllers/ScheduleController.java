package Business.Controllers;


import Business.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import static Business.TypeOfEmployee.*;
public class ScheduleController {

    //========================================================Fields====================================================
    private TypeOfEmployee typeOfLoggedIn;
    private Map<Date, DailySchedule> schedule;
    private StaffController staffController;

    //========================================================Constructor====================================================

    public ScheduleController(TypeOfEmployee type, StaffController sc)
    {
        this.staffController = sc;
        this.typeOfLoggedIn =type;
        this.schedule = new HashMap<>();
    }
    //========================================================Methods====================================================


    /**
     * Creates and adds a new shift to the and daily schedule and adds the new daily schedule to the schedule
     * Only an HRManager can add shifts
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String addShift(Date date, TypeOfShift type)
    {

        if(this.typeOfLoggedIn!=HRManager)//Only HRManager can add shifts
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

    /**
     * Removes the shift at date "date" and of type "type" from the schedule
     * Only a HRManager can remove shifts
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String removeShift(Date date, TypeOfShift type)
    {

        if(this.typeOfLoggedIn!=HRManager)//Only HRManager can remove shifts
        {
            return "Only HRManager can remove shifts";
        }
        if(!isShiftExists(date,type))
        {
            return "Shift doesn't exist";
        }
        DailySchedule dailySchedule = this.schedule.get(date);
        dailySchedule.removeShift(date,type);
        return "Shift was removed successfully";
    }


    /**
     * Adds employee with id "id" to the shift with date "date" and of type "type"
     * @param id
     * @param toSkill
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String addEmployeeToShift(String id,TypeOfEmployee toSkill, Date date, TypeOfShift type)
    {
        if(!isShiftExists(date, type)) //Cant add an employee to a shift that doesn't exist
        {
            return "Shift doesn't exist";
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

    /**
     * Remove employee with id "id" from shift with date "date" and of type "type"
      * @param id
     * @param date
     * @param type
     * @return Success/Fail message
     */
    public String removeEmployeeFromShift(String id,Date date, TypeOfShift type)
    {
        if(!isShiftExists(date,type))
        {
            return "Shift doesn't exist";
        }
        Shift s= getShift(date,type);
        if(!s.isEmployeeInShift(id)) //Check if the employee is in the shift
        {
            return "Shift doesn't contain this employee";
        }

        if (!s.removeEmployee(id))
        {
            return "Employee was not removed from shift";
        }
        return "Employee removed successfully from shift";
    }

    /**
     * Adds a constraint/Edits an existing constraint to a specific shift
     * Only HRManager can add constraints
     * @param date
     * @param typeOfShift
     * @param typeOfEmployee
     * @param numOfEmp
     * @return Success/Fail message
     */
    public String addConstraint(Date date, TypeOfShift typeOfShift, TypeOfEmployee typeOfEmployee, Integer numOfEmp) {
        if (typeOfLoggedIn!= HRManager)
        {
            return "Only a HR Manager is allowed to modify number and type of employees in a shift";
        }
        try
        {
            if (!this.schedule.containsKey(date))
            {
                return "No such shift";
            }
            DailySchedule dailySchedule=this.schedule.get(date);
            if (!dailySchedule.isTypeOfShiftExists(typeOfShift))
            {
                return "No such shift";
            }
            Shift shift = dailySchedule.getShift(typeOfShift);
            shift.addConstraint(typeOfEmployee, numOfEmp);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
        return "Constraint added successfully";
    }

    /**
     * Removes a constraint from a specific shift
     * @param date
     * @param typeOfShift
     * @param typeOfEmployee
     * @return Success/Fail message
     */
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

    public Shift getShift(Date date,TypeOfShift type)
    {
        DailySchedule ds = schedule.get(date);
        Shift s = ds.getShift(type);
        return s;
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


    public List<Shift> getShiftWithEmp(String id) {
        List<Shift> toReturn = new LinkedList<>();

        for (Date d:schedule.keySet()) {
            DailySchedule daily = schedule.get(d);
            for (Shift s:daily.getShifts()) {
                if (s.isEmployeeInShift(id))
                {
                    toReturn.add(s);
                }
            }

        }
        return toReturn;
    }
}
