package Business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class DailySchedule {
    //==========================================================Fields===========================================================

    private List<Shift> shifts;

    //==========================================================Constructor===========================================================

    public  DailySchedule()
    {
        shifts=new LinkedList<>();
    }

    public DailySchedule(Shift shift)
    {
        shifts=new LinkedList<>();
        addShift(shift);
    }

    //==========================================================Methods===========================================================
    public void addShift (Shift shift)
    {
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

    /**
     * Doesnt do anything if shift doesn't exist
     * @param date
     * @param type
     */
    public void removeShift(Date date, TypeOfShift type) {
        int loc = getShiftLocation(type);
        if(loc!=-1)
        {
            this.shifts.remove(loc);
        }
    }

    /**
     * Returns -1 if shift doesn't exist
     * @param type
     * @return shift's location or -1
     */
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
        int location = getShiftLocation(type);
        return shifts.get(location);
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder builder=new StringBuilder();
        builder.append("Daily Schedule: \n\t");
        for(Shift s :shifts)
        {
            builder.append("\n\t" + s.toString());
        }
        builder.append("\n");
        return builder.toString();
    }
}
