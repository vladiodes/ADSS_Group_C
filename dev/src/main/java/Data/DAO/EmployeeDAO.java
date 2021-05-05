package Data.DAO;

import Business.Misc.Pair;
import Data.DTO.EmployeeDTO;
import Data.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAO extends DAO<EmployeeDTO> {

    public EmployeeDAO()
    {
        this.tableName = "Employees";
    }

    @Override
    public int insert(EmployeeDTO Ob)
    {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        String toInsertEmp = this.InsertStatement(Ob.fieldsToString());

        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resAs = insertToAvailableShifts(Ob);
            int resES = insertToEmployeeSkills(Ob);
            if(resAs+resES==2) //If both inserts worked
                return 1;
            return 0;
        }
        catch (Exception e ){
            return 0;
        }
    }
    private int insertToAvailableShifts(EmployeeDTO Ob)
    {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        for(int index = 0 ; index<Ob.getNumberOfAvailableShifts(); index++)
        {
            String toInsertAvailableShifts =String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "AvailableShiftsForEmployees", Ob.getAvailableShifts(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(InsertStatement(toInsertAvailableShifts));
            }
            catch (Exception e ){
                return 0;
            }
        }
        return 1;
    }

    private int insertToEmployeeSkills(EmployeeDTO Ob)
    {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        for(int index = 0 ; index<Ob.getNumberOfSkills(); index++)
        {
            String toInsertSkills =String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeeSkills", Ob.getSkills(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(InsertStatement(toInsertSkills));
            }
            catch (Exception e ){
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int update(EmployeeDTO updatedOb)
    {
        return 0;
    }


    @Override
    public EmployeeDTO makeDTO(ResultSet RS) {
        EmployeeDTO output = null;
        try {
            List<String> skills = getSkillsList(RS.getString(2)/*id*/);
            if(skills==null)
            {
                return null;
            }
            List<Pair<Date, String>> availableShifts = getavailableShiftList(RS.getString(2)/*id*/);
            if(availableShifts==null)
            {
                return null;
            }
            output = new EmployeeDTO(/*first name*/RS.getString(0), /*last name*/RS.getString(1), /*Id*/RS.getString(2),
                        /*bank account number*/RS.getString(3), /*salary*/RS.getInt(4),/*empConditions*/ RS.getString(5),
                    /*start working date*/new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(6)) , skills,availableShifts) ;
        }
        catch (Exception e)
        {
            output = null;
        }
        return output;
    }

    private List<Pair<Date, String>> getavailableShiftList(String empId) {
        List<Pair<Date, String>> ans=new LinkedList<>();
        ResultSet rs=get("AvailableShiftsForEmployees","EmpID",empId  );
        try
        {
            while (rs.next()) {

                Pair<Date, String> p= new Pair<>(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(1)),rs.getString(2));//have to check
                ans.add(p);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return ans;
    }

    private List<String> getSkillsList(String empId) {
        List<String> ans=new LinkedList<>();
        ResultSet rs=get("EmployeeSkills","EmployeeID", empId );
        try
        {
            while (rs.next()) {
                ans.add(rs.getString(1));//have to check
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return ans;

    }

    @Override
    public int delete(EmployeeDTO ob) {
        return 0;
    }
}
