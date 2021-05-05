package Data.DTO;

public class ShiftConstraintsDTO {
<<<<<<< Updated upstream
    private Integer ShiftID;
    private String typeOfEmp;
    private Integer amount;
=======

    public Integer ShiftID;
    public String typeOfEmp;
    public Integer amount;
>>>>>>> Stashed changes

    public ShiftConstraintsDTO(Integer shiftID, String typeOfEmp, Integer amount) {
        this.ShiftID = shiftID;
        this.typeOfEmp = typeOfEmp;
        this.amount = amount;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\")", Integer.toString(this.ShiftID), this.typeOfEmp, Integer.toString(this.amount));
    }
}
