package Data.DTO;

import Business.Misc.Pair;
import Business.Misc.TypeOfEmployee;
import Business.Misc.TypeOfShift;
import Business.Objects.Employee;
import java.util.*;

import static Business.Misc.TypeOfEmployee.ShiftManager;

public class ShiftDTO {
    //==================================================================Fields==================================================================
<<<<<<< Updated upstream
    private Integer Id;
    private String type;
    private Date date;
    private Map<String, Integer> constraints;
    private List<Pair<String/*empID*/, String/*typeOfEmployee*/>> currentShiftEmployees;//----------------------------change EmployeeDTO to String(emp Id)
    private boolean isSealed;
=======
    public Integer Id;
    public String type;
    public Date date;
    public Map<String, Integer> constraints;
    public List<Pair<String/*empID*/,String/*typeOfEmployee*/>> currentShiftEmployees;//----------------------------change EmployeeDTO to String(emp Id)
    public boolean isSealed;
>>>>>>> Stashed changes


    //==================================================================Constructor==============================================================
    public ShiftDTO(Integer Id, String type, Date date, Map<String, Integer> constraints, List<Pair<String/*empID*/, String/*typeOfEmployee*/>> currentShiftEmployees) {
        this.Id = Id;
        this.type = type;
        this.date = date;
        this.currentShiftEmployees = currentShiftEmployees;
        this.constraints = constraints;
        this.constraints.put("ShiftManager", 1); //Default constraint
        this.isSealed = false;

    }

    public String fieldsToString() {
        return String.format("(\"%s\",\"%s\",\"%s\")", this.type, date.toString(), isSealed ? "True" : "False");
    }

    public int getNumberOfEmpInShift() {
        return this.currentShiftEmployees.size();
    }

    public String getEmployees(int index) {
        return String.format("(\"%s\",\"%s\")", this.Id, this.currentShiftEmployees.get(index).first);
    }

    public String getConstraint(String type) {
        Integer amount = this.constraints.get(type);
        return String.format("(\"%s\",\"%s\")", this.Id, type, amount);

    }

    public Map<String, Integer> getConstraintsMap() {
        return this.constraints;
    }
}
