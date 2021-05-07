package Data.DTO;

import Misc.Pair;

import java.text.SimpleDateFormat;
import java.util.*;

public class ShiftDTO {
    //==================================================================Fields==================================================================

    public int shiftId;
    public String type;
    public Date date;
    public Map<String, Integer> constraints;
    public List<Pair<String/*empID*/,String/*typeOfEmployee*/>> currentShiftEmployees;//----------------------------change EmployeeDTO to String(emp Id)
    public boolean isSealed;


    //==================================================================Constructor==============================================================
    public ShiftDTO(int shiftId,String type, Date date, int isSealed, Map<String, Integer> constraints, List<Pair<String/*empID*/, String/*typeOfEmployee*/>> currentShiftEmployees) {
        this.shiftId=shiftId;
        this.type = type;
        this.date = date;
        this.currentShiftEmployees = currentShiftEmployees;
        this.constraints = constraints;
        this.constraints.put("ShiftManager", 1); //Default constraint
        this.isSealed = isSealed==1 ? true : false;

    }

    public String fieldsToString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("(%s,\"%s\",\"%s\",%s)", this.shiftId, formatter.format(date),this.type, isSealed ? 1 : 0);
    }

    public int getNumberOfEmpInShift() {
        return this.currentShiftEmployees.size();
    }

    public String getEmployees(int index ) {
        return String.format("(\"%s\",%s,\"%s\")", this.currentShiftEmployees.get(index).first, this.shiftId, this.currentShiftEmployees.get(index).second);
    }

    public String getConstraint(String type) {
        Integer amount = this.constraints.get(type);
        return String.format("(%s,\"%s\",%s)",this.shiftId, type, amount);

    }

    public Map<String, Integer> getConstraintsMap() {
        return this.constraints;
    }
}
