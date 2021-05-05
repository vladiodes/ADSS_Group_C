package Data.DTO;

public class ShiftConstraintsDTO {

    private Integer ShiftID;
    private String typeOfEmp;
    private Integer amount;

    public ShiftConstraintsDTO(Integer shiftID, String typeOfEmp, Integer amount)
    {
        this.ShiftID = shiftID;
        this.typeOfEmp = typeOfEmp;
        this.amount = amount;

    }

    public String fieldsToString()
    {
        return String.format("(\"%s\",\"%s\",\"%s\")", Integer.toString(this.ShiftID),this.typeOfEmp, Integer.toString(this.amount));
    }
}
