package DataAccessLayer;

import DTO.EmployeeDTO;
import Misc.Pair;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class EmployeeDAO extends DAOV2<EmployeeDTO> {

    private AvailableShiftForEmployeeDAO availableShiftForEmployeeDAO;
    private EmployeesSkillsDAO employeesSkillsDAO;

    public EmployeeDAO() {
        this.tableName = "Employees";
        availableShiftForEmployeeDAO = new AvailableShiftForEmployeeDAO();
        employeesSkillsDAO = new EmployeesSkillsDAO();
    }

    //=====================================Employee===================================
    @Override
    public int insert(EmployeeDTO Ob) {
        int ans = 0;
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertEmp = Ob.fieldsToString();
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(toInsertEmp));
            int resAs = insertToAvailableShifts(Ob);
            int resES = insertToEmployeeSkills(Ob);
            if (resAs + resES == 2) //If both inserts worked
                ans = 1;
            else {
                ans = 0;
            }
        } catch (Exception e) {
            ans = 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return ans;
    }

    private int insertToAvailableShifts(EmployeeDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        for (int index = 0; index < Ob.getNumberOfAvailableShifts(); index++) {
            String toInsertAvailableShifts = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "AvailableShiftsForEmployees", Ob.getAvailableShifts(index));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(toInsertAvailableShifts);
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }

    private int insertToEmployeeSkills(EmployeeDTO Ob) {
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

    @Override
    public EmployeeDTO makeDTO(ResultSet RS) {
        EmployeeDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            String id = RS.getString(3);
            List<String> skills = getSkillsList(id, conn);
            if (skills == null) {
                return null;
            }
            List<Pair<Date, String>> availableShifts = getAvailableShiftList(RS.getString(3)/*id*/, conn);
            if (availableShifts == null) {
                return null;
            }
            output = new EmployeeDTO(/*first name*/RS.getString(1), /*last name*/RS.getString(2), /*Id*/RS.getString(3),
                    /*bank account number*/RS.getString(4), /*salary*/RS.getInt(5),/*empConditions*/ RS.getString(6),
                    /*start working date*/new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(7)), skills, availableShifts);
        } catch (Exception e) {
            output = null;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    private List<Pair<Date, String>> getAvailableShiftList(String empId, Connection conn) {
        List<Pair<Date, String>> ans = new LinkedList<>();
        ResultSet rs = get("AvailableShiftsForEmployees", "EmpID", empId, conn);
        try {
            while (rs.next()) {
                String dateSTR = rs.getString(2);
                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(dateSTR);
                String type = rs.getString(3);
                Pair<Date, String> p = new Pair<>(date, type);//have to check
                ans.add(p);
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    private List<String> getSkillsList(String empId, Connection conn) {
        List<String> ans = new LinkedList<>();
        ResultSet rs = get("EmployeeSkills", "EmployeeID", empId, conn);
        try {
            while (rs.next()) {
                ans.add(rs.getString(2));//have to check
            }
        } catch (Exception e) {
            return null;
        }
        return ans;
    }

    @Override
    public int update(EmployeeDTO updatedOb)//not allowed to change ID
    {
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"FirstName\"= \"%s\", \"LastName\"= \"%s\" " +
                        ", \"BankAccountNumber\"=\"%s\", \"Salary\"=%s,  \"EmpConditions\"=\"%s\", \"StartWorkingDate\"=\"%s\" " +
                        "WHERE \"ID\" == \"%s\";",
                tableName, updatedOb.firstName, updatedOb.lastName,
                updatedOb.bankAccountNumber, updatedOb.salary, updatedOb.empConditions, updatedOb.startWorkingDate, updatedOb.id);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        } catch (Exception e) {
            return 0;
        }
    }

    //=====================================EmployeeSkills===================================
    public int addSkill(String empID, String skillToAdd) {
        return employeesSkillsDAO.addSkill(empID, skillToAdd);
    }

    public int removeSkill(String empID, String skillToRemove) {
        return employeesSkillsDAO.removeSkill(empID, skillToRemove);
    }

    //=====================================AvailableShift===================================
    public int addAvailableShifts(String empID, Date date, String typeOfShift) {
        return availableShiftForEmployeeDAO.addAvailableShifts(empID, date, typeOfShift);
    }

    public int removeAvailableShifts(String empID, Date date, String typeOfShift) {
        return availableShiftForEmployeeDAO.removeAvailableShifts(empID, date, typeOfShift);
    }
}
