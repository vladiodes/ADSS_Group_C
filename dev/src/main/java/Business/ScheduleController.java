package Business;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ScheduleController { //Singleton


    private Map<Date,DailySchedule[]> schedule;
    private static ScheduleController instance;

    private ScheduleController()
    {
        this.schedule = new HashMap<>();
    }

    public ScheduleController getInstance()
    {
        if(this.instance == null)
        {
            this.instance = new ScheduleController();
        }
        return this.instance;
    }

}
