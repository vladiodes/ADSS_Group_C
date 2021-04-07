package Business;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static Business.TypeOfShift.Morning;

public class DailySchedule {
    private List<Shift> shifts;
    public  DailySchedule()
    {
        shifts=new LinkedList<>();
    }
    public DailySchedule(Shift shift) throws Exception
    {
        shifts=new LinkedList<>();
        addShift(shift);
    }
    //-------------------------------------------------------methods----------------------------------------------------------------------------------
    public void addShift (Shift shift) throws  Exception
    {
        if(!shift.checkFull()) //Check if every shift constraint is satisfied
        {
            throw new Exception("");
        }
        this.shifts.add(shift);

    }


    public boolean isTypeOfShiftExists(TypeOfShift t) {
        for (Shift s: shifts)
        {
            if(s.getType()==t)
            {
                return true;
            }
        }

        return  false;
    }

    public void removeShift(Date date, TypeOfShift type) {
        int loc = getShiftLocation(type);
        if(loc!=-1)
        {
            this.shifts.remove(loc);
        }
    }

    private int getShiftLocation( TypeOfShift type)
    {
        for(int i=0; i<shifts.size(); i++)
        {
            if(this.shifts.get(i).getType() == type)
                return i;
        }
        return -1;
    }

    public Shift getShift(TypeOfShift type) { //Assumes that shift already exists
        int i = getShiftLocation(type);
        return shifts.get(i);
    }
}
