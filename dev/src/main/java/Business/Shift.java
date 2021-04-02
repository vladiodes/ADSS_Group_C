package Business;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Business.TypeOfEmployee.*;

public class Shift {
    private TypeOfShift type;
    private Date date;
    private Map<TypeOfEmployee, Integer> constraints;
    private List<Pair<Employee,TypeOfEmployee>> currentShiftEmployees;


    public Shift(TypeOfShift type, Date date, Map<TypeOfEmployee, Integer> constraints, List<Pair<Employee,TypeOfEmployee>> currentShiftEmployees) throws Exception
    {
        this.type = type;
        this.date = date;
        if(currentShiftEmployees == null)
        {
            throw new Exception("currentShiftEmployees list is null");
        }
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
        this.currentShiftEmployees = currentShiftEmployees;

    }

    public Shift(TypeOfShift type, Date date)
    {
        this.type = type;
        this.date = date;
        this.constraints = new HashMap<>(); //init
        this.constraints.put(ShiftManager, 1); //Default constraint
    }


    public void addEmployeeToShift()
    {

    }
    private boolean checkConstraints()
    {
        throw new NotImplementedException();
    }







}
