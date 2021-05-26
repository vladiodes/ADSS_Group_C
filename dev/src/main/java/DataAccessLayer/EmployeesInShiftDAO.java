package DataAccessLayer;

import java.sql.Connection;
import java.sql.Statement;

public class EmployeesInShiftDAO {
    public int addEmployeeToShift(String EmployeeID,int ShiftID ,String RoleInShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(EmployeeID == null || ShiftID < 0 || RoleInShift == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",%s,\"%s\",%s);", "EmployeesInShift", EmployeeID, ShiftID,RoleInShift, null);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }

    }
    public int addDriverToShift(String EmployeeID,int ShiftID ,String RoleInShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(EmployeeID == null || ShiftID < 0 || RoleInShift == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (%s,%s,\"%s\",\"%s\");", "EmployeesInShift",null, ShiftID,RoleInShift,  EmployeeID);
        Statement s;
        try
        {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
        finally {
            Repository.getInstance().closeConnection(conn);
        }

    }


    public int removeEmployeeFromShift( String EmployeeID,int ShiftID,String RoleInShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(EmployeeID == null || ShiftID < 0 || RoleInShift==null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%s\" AND %s=%s AND %s=\"%s\";", "EmployeesInShift", "EmployeeID", EmployeeID,"ShiftID" ,ShiftID, "RoleInShift", RoleInShift);
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
        finally {
            Repository.getInstance().closeConnection(conn);
        }

    }
    public int removeDriverFromShift( String EmployeeID,int ShiftID,String RoleInShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(EmployeeID == null || ShiftID < 0 || RoleInShift==null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=\"%s\" AND %s=%s AND %s=\"%s\";", "EmployeesInShift", "DriverID", EmployeeID,"ShiftID" ,ShiftID, "RoleInShift", RoleInShift);
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
        finally {
            Repository.getInstance().closeConnection(conn);
        }

    }

}
