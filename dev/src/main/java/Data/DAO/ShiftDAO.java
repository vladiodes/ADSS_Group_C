package Data.DAO;

import Business.Misc.Pair;
import Data.DTO.EmployeeDTO;
import Data.DTO.ShiftDTO;
import Data.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShiftDAO extends DAO<ShiftDTO> {

    @Override
    public int insert(ShiftDTO Ob)
    {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        String toInsertShift = this.InsertStatement(Ob.fieldsToString());
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertShift));
            int resConstraints = insertToShiftConstraints(Ob);
            int resEmpInShift = insertToEmployeeInShift(Ob);
            if (resConstraints + resEmpInShift == 2)
            {
                return 1;
            }
            return 0;
        }
        catch (Exception e ){
            return 0;
        }
    }

    private int insertToShiftConstraints(ShiftDTO Ob)
    {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        for(String type: Ob.getConstraintsMap().keySet())
        {
            String toInsertConstraints =String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "ShiftConstraints", Ob.getConstraint(type));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(InsertStatement(toInsertConstraints));
            }
            catch (Exception e ){
                return 0;
            }
        }
        return 1;
    }

    private int insertToEmployeeInShift(ShiftDTO Ob)
    {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        for(int index = 0 ; index<Ob.getNumberOfEmpInShift(); index++)
        {
            String toInsert =String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesInShift", Ob.getEmployees(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(InsertStatement(toInsert));
            }
            catch (Exception e ){
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int update(ShiftDTO updatedOb)
    {
        return 0;
    }

    @Override
    public ShiftDTO makeDTO(ResultSet RS)
    {
        ShiftDTO output = null;
        try {
            Map<String, Integer> constraints = getconstraintsList(RS.getInt(0)/*id*/);
            if(constraints==null)
            {
                return null;
            }
            List<Pair<String/*empId*/,String/*typeOfEmployee*/>> currentShiftEmployees = getcurrentShiftEmployeesList(RS.getInt(0)/*id*/);
            if(currentShiftEmployees==null)
            {
                return null;
            }
            /*
             public ShiftDTO(Integer Id,String type, Date date)
             */
            output = new ShiftDTO(/*Id*/RS.getInt(0), /*type*/RS.getString(1),/*date*/new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(2)), constraints, currentShiftEmployees ) ;
        }
        catch (Exception e)
        {
            output = null;
        }
        return output;
    }

    //----------check the 2 functions

    private List<Pair<String, String>> getcurrentShiftEmployeesList(int shiftId) {
        List<Pair<String, String>> ans =new LinkedList<>();
        ResultSet rs=getWithInt("EmployeesInShift","ShiftID", shiftId  );
        try
        {
            while (rs.next())
            {
                Pair<String, String> p = new Pair<>(rs.getString(0),rs.getString(1)/*type of shift*/);
                ans.add(p);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return ans;


    }
/*
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"TypeOfEmployee\"\tTEXT,\n" +
                "\t\"Amount\"\tINTEGER,\n" +
* */

    private Map<String, Integer> getconstraintsList(int shiftId) {
        Map<String, Integer> ans= new HashMap<>();
        ResultSet rs=getWithInt("EmployeesInShift","ShiftID", shiftId  );
        try
        {
            while (rs.next())
            {
                ans.put(rs.getString(1)/*type of employee*/,rs.getInt(2)/*amount*/);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return ans;
    }

    @Override
    public int delete(ShiftDTO ob) {
        return 0;
    }
}
