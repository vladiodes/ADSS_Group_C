package Data.DTO;

public class EmployeesInShiftDTO {
    private Integer ShiftID;
    private Integer EmployeeID;

    public EmployeesInShiftDTO(Integer ShiftID, Integer EmployeeID)
    {
        this.ShiftID = ShiftID;
        this.EmployeeID = EmployeeID;
    }

    public String fieldsToString()
    {
        return String.format("(\"%s\",\"%s\")", Integer.toString(this.ShiftID), Integer.toString(this.EmployeeID));
    }
}
