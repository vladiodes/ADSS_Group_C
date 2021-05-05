package Data.DTO;

import Business.Misc.Pair;
import Business.Misc.TypeOfEmployee;
import Business.Misc.TypeOfShift;
import Business.Objects.Employee;

import java.util.*;

import static Business.Misc.TypeOfEmployee.ShiftManager;

public class ShiftDTO {
    //==================================================================Fields==================================================================
    private TypeOfShiftDTO type;
    private Date date;
    private Map<TypeOfEmployeeDTO, Integer> constraints;
    private List<Pair<EmployeeDTO,TypeOfEmployeeDTO>> currentShiftEmployees;
    private boolean isSealed;


    //==================================================================Constructor==============================================================
    public ShiftDTO(TypeOfShiftDTO type, Date date)
    {
        this.type = type;
        this.date = date;
        this.currentShiftEmployees = new LinkedList<>();
        this.constraints = new HashMap<>(); //init
        this.constraints.put(new TypeOfEmployeeDTO("ShiftManager"), 1); //Default constraint
        this.isSealed = false;
    }
}
