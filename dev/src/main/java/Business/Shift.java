package Business;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static Business.TypeOfEmployee.*;

public class Shift {
    private TypeOfShift type;
    private Date date;
    private Map<TypeOfEmployee, Integer> constraints;

    public Shift(TypeOfShift type, Date date, Map<TypeOfEmployee, Integer> constraints) throws Exception
    {
        this.type = type;
        this.date = date;
        if(constraints == null)
        {
            throw new Exception("Constraints list is null");
        }
        else
        {
            if((!constraints.containsKey(ShiftManager)))
            {
                throw new Exception("Shift must contain a shift manager constraint");
            }
            else if((constraints.get(ShiftManager)<1))
            {
                throw new Exception("Number of shift managers in a shift must be at least 1");
            }
            this.constraints = constraints;
        }
    }

    public Shift(TypeOfShift type, Date date)
    {
        this.type = type;
        this.date = date;
        this.constraints = new HashMap<>(); //init
        this.constraints.put(ShiftManager, 1); //Default constraint
    }








}
