package Data.DAO;

import Business.Misc.Pair;
import Data.DTO.DriverDTO;
import Data.DTO.EmployeeDTO;
import Data.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DriverDAO extends DAO<DriverDTO> {
    public int insert(DriverDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmp = this.InsertStatement(Ob.fieldsToString());
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resAs = insertToAvailableShifts(Ob);
            int resES = insertToEmployeeSkills(Ob);
            if (resAs + resES == 2) //If both inserts worked
                return 1;
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private int insertToAvailableShifts(DriverDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        for (int index = 0; index < Ob.getNumberOfAvailableShifts(); index++) {
            String toInsertAvailableShifts = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "AvailableShiftsForEmployees", Ob.getAvailableShifts(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(InsertStatement(toInsertAvailableShifts));
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }

    private int insertToEmployeeSkills(DriverDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        for (int index = 0; index < Ob.getNumberOfSkills(); index++) {
            String toInsertSkills = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeeSkills", Ob.getSkills(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(InsertStatement(toInsertSkills));
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int update(DriverDTO updatedOb) {
        Connection conn = Repository.getInstance().connect();
        if(updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"FirstName\"= \"%s\", \"LastName\"= %s " +
                        ", \"BankAccountNumber\"=\"%s\", \"Salary\",  \"EmpConditions\"=\"%s\", \"StartWorkingDate\"=\"%s\", \"License\"=\"%s\"  " +
                        "WHERE \"ID\" == \"%s\";",
                tableName,updatedOb.firstName,updatedOb.lastName,
                updatedOb.bankAccountNumber,updatedOb.salary, updatedOb.empConditions, updatedOb.startWorkingDate,updatedOb.License , updatedOb.id);
        Statement s;
        try {
            s = conn.createStatement();
            return  s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }


    public int insertAvailableShifts(String empID, Date date, String typeOfShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || date == null || typeOfShift == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\",\"%s\");", "AvailableShiftsForEmployees", empID, date,typeOfShift);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }

    public int removeAvailableShifts(String empID, Date date, String typeOfShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || date == null || typeOfShift==null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=%s AND %s=%s AND %s=%s;", "AvailableShiftsForEmployees", "EmpID", empID,"Date" ,date, "Type", typeOfShift);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e )
        {
            return 0;
        }
    }

    public int insertSkill(String empID, String skillToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || skillToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\");", "EmployeeSkills", empID, skillToAdd);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }
    public int removeSkill(String empID, String skillToRemove)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || skillToRemove == null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=%s AND %s=%s;", "EmployeeSkills", "EmployeeID", empID,"TypeOfEmployee" ,skillToRemove);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }

    }

    @Override
    public DriverDTO makeDTO(ResultSet RS) {
        Connection conn = Repository.getInstance().connect();
        DriverDTO output = null;
        try {
            List<String> skills = getSkillsList(RS.getString(2)/*id*/,conn);
            if (skills == null) {
                return null;
            }
            List<Pair<Date, String>> availableShifts = getavailableShiftList(RS.getString(2)/*id*/, conn);
            if (availableShifts == null) {
                return null;
            }
            /*
             public DriverDTO(String firstName, String lastName, String id, String bankAccountNumber, int salary, String empConditions,
                     Date startWorkingDate,int License, List<String> skills, List<Pair<Date, String>> availableShifts)
            */
            output = new DriverDTO(/*first name*/RS.getString(0), /*last name*/RS.getString(1), /*Id*/RS.getString(2),
                    /*bank account number*/RS.getString(3), /*salary*/RS.getInt(4),/*empConditions*/ RS.getString(5),
                    /*start working date*/new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(6)), RS.getInt(7), skills, availableShifts);
        } catch (Exception e) {
            output = null;
        }
        return output;
    }

    private List<Pair<Date, String>> getavailableShiftList(String empId, Connection conn) {
        List<Pair<Date, String>> ans = new LinkedList<>();
        ResultSet rs = get("AvailableShiftsForEmployees", "EmpID", empId);
        try {
            while (rs.next()) {
                Pair<Date, String> p = new Pair<>(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(1)), rs.getString(2));//have to check
                ans.add(p);
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    private List<String> getSkillsList(String empId,Connection conn) {
        List<String> ans = new LinkedList<>();
        ResultSet rs = get("EmployeeSkills", "EmployeeID", empId);
        try {
            while (rs.next()) {
                ans.add(rs.getString(1));//have to check
            }
        } catch (Exception e) {
            return null;
        }
        return ans;

    }


}
