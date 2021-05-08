package Data.DAO;

import Data.Repository;

import java.sql.Connection;
import java.sql.Statement;

public class EmployeesSkillsDAO {
    public int addSkill(String empID, String skillToAdd)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(empID == null || skillToAdd == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\",%s);", "EmployeeSkills", empID, skillToAdd, null);
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
                "WHERE %s=\"%s\" AND %s=\"%s\";", "EmployeeSkills", "EmployeeID", empID,"TypeOfEmployee" ,skillToRemove);
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
}
