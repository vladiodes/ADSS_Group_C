package Data.DAO;

import Misc.Pair;
import DTO.DriverDTO;
import Data.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static Misc.Functions.DateToString;

public class DriverDAO extends DAO<DriverDTO> {

    private AvailableShiftForEmployeeDAO availableShiftForEmployeeDAO;
    private EmployeesSkillsDAO employeesSkillsDAO;
    public DriverDAO() {

        super.tableName = "Drivers";
        availableShiftForEmployeeDAO=new AvailableShiftForEmployeeDAO();
        employeesSkillsDAO=new EmployeesSkillsDAO();
    }

    @Override
    public int insert(DriverDTO Ob) {
        int ans=0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmp =InsertStatement( Ob.fieldsToString());

        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(toInsertEmp);
            int resAs = insertToAvailableShifts(Ob);
            int resES = insertToEmployeeSkills(Ob);
            if (resAs + resES == 2) //If both inserts worked
                ans= 1;
            else
            {
                ans=0;
            }

        } catch (Exception e) {
            ans= 0;
        }
        finally {
            Repository.getInstance().closeConn(conn);
        }
        return ans;

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
                s.executeUpdate(toInsertSkills);
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }

    public int update(DriverDTO updatedOb)//not allowed to change ID
    {
        Connection conn = Repository.getInstance().connect();
        if(updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"FirstName\"= \"%s\", \"LastName\"= \"%s\" " +
                        ", \"BankAccountNumber\"=\"%s\", \"Salary\"=%s,  \"EmpConditions\"=\"%s\", \"StartWorkingDate\"=\"%s\", \"License\"=%s " +
                        "WHERE \"ID\" == \"%s\";",
                tableName,updatedOb.firstName,updatedOb.lastName,
                updatedOb.bankAccountNumber,updatedOb.salary, updatedOb.empConditions, updatedOb.startWorkingDate, updatedOb.id, updatedOb.License);
        Statement s;
        try {
            s = conn.createStatement();
            return  s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }

    public int addAvailableShifts(String empID, Date date, String typeOfShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || date == null || typeOfShift == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (%s,\"%s\",\"%s\",\"%s\");", "AvailableShiftsForEmployees", null, DateToString(date),typeOfShift,empID);
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
                "WHERE %s=\"%s\" AND %s=\"%s\" AND %s=\"%s\";", "AvailableShiftsForEmployees", "DriverID", empID,"Date" ,date, "Type", typeOfShift);
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

    public int addSkill(String empID, String skillToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || skillToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (%s,\"%s\",\"%s\");", "EmployeeSkills", null, skillToAdd, empID);
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
                "WHERE %s=\"%s\" AND %s=\"%s\";", "EmployeeSkills", "DriverID", empID,"TypeOfEmployee" ,skillToRemove);
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
        DriverDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            String id=RS.getString(3);
            List<String> skills = getSkillsList(id, conn);
            if (skills == null) {
                return null;
            }
            List<Pair<Date, String>> availableShifts = getavailableShiftList(RS.getString(3)/*id*/, conn);
            if (availableShifts == null) {
                return null;
            }
            output = new DriverDTO(/*first name*/RS.getString(1), /*last name*/RS.getString(2), /*Id*/RS.getString(3),
                    /*bank account number*/RS.getString(4), /*salary*/RS.getInt(5),/*empConditions*/ RS.getString(6),
                    /*start working date*/new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(7)), RS.getInt(8), skills, availableShifts);
        } catch (Exception e) {
            output = null;
        }
        finally {
            Repository.getInstance().closeConn(conn);
        }

        return output;
    }

    private List<Pair<Date, String>> getavailableShiftList(String driverId, Connection conn) {
        List<Pair<Date, String>> ans = new LinkedList<>();
        ResultSet rs = get("AvailableShiftsForEmployees", "DriverID", driverId,conn);
        try {
            while (rs.next()) {
                Pair<Date, String> p = new Pair<>(new SimpleDateFormat("dd/MM/yyyy").parse(rs.getString(2)), rs.getString(3));//have to check
                ans.add(p);
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    private List<String> getSkillsList(String driverId,Connection conn) {
        List<String> ans = new LinkedList<>();
        ResultSet rs = get("EmployeeSkills", "DriverID", driverId, conn);
        try {
            while (rs.next()) {
                ans.add(rs.getString(2));//have to check
            }
        } catch (Exception e) {
            return null;
        }
        return ans;

    }


}
