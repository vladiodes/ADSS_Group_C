package Data.DTO;

public class ShiftConstraintsDTO {

    public Integer ShiftID;
    public String typeOfEmp;
    public Integer amount;


    public ShiftConstraintsDTO(Integer shiftID, String typeOfEmp, Integer amount) {
        this.ShiftID = shiftID;
        this.typeOfEmp = typeOfEmp;
        this.amount = amount;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\")", Integer.toString(this.ShiftID), this.typeOfEmp, Integer.toString(this.amount));
    }
}
