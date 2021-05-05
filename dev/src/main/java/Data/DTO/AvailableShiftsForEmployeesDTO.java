package Data.DTO;

import java.util.Date;

public class AvailableShiftsForEmployeesDTO {
    private String EmpID;
    private Date date;
    private String type;


    public AvailableShiftsForEmployeesDTO( String ID, Date date, String type)
    {
        this.EmpID = ID;
        this.date = date;
        this.type = type;

    }
    public String fieldsToString()
    {
        return String.format("(\"%s\",\"%s\",\"%s\")", this.EmpID, this.date.toString(), this.type);
    }
}
