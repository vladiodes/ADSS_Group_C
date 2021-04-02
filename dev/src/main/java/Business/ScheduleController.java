package Business;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleController { //Singleton


    private Map<Date,DailySchedule[]> schedule;
    private StaffController staffController; //Add to documentation
    private static ScheduleController instance;

    private ScheduleController()
    {
        this.schedule = new HashMap<>();
        this.staffController = StaffController.getInstance();
    }

    public static ScheduleController getInstance()
    {
        if(instance == null)
        {
            instance = new ScheduleController();
        }
        return instance;
    }

    public void addShift(Date date, TypeOfShift type, Map<TypeOfEmployee, Integer> constraints)
    {
        //Only HRManager can add shifts
    }

    public void removeShift(Date date, TypeOfShift type)
    {
        //Only HRManager can remove shifts
    }

    public Map<Date,DailySchedule[]> getSchedule()
    {
        return this.schedule;
    }

    public void addEmployeeToShift(String id,TypeOfEmployee toSkill, Date date, TypeOfShift type)
    {
        //Check if employee is set to a skill he owns and if the shift constraint isn't broken
    }

    public void removeEmployeeToShift(String id,Date date, TypeOfShift type)
    {

    }


}
