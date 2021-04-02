package Business;

import java.util.HashMap;
import java.util.Map;

public class StaffController { //Singleton

    private Map<String, Employee> employees;
    private static StaffController instance;

    private StaffController()
    {
        this.employees = new HashMap<>();
    }

    public StaffController getInstance()
    {
        if(this.instance == null)
        {
            this.instance = new StaffController();
        }
        return this.instance;
    }


}
