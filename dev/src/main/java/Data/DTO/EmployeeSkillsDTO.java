package Data.DTO;

public class EmployeeSkillsDTO {
<<<<<<< Updated upstream
    private String EmpID;
    private String type;
=======

    public String EmpID;
    public String type;
>>>>>>> Stashed changes

    public EmployeeSkillsDTO(String EmpID, String type) {
        this.EmpID = EmpID;
        this.type = type;
    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\")", this.EmpID, this.type);
    }
}
