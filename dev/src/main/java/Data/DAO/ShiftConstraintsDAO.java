package Data.DAO;

import DTO.ShiftDTO;
import Data.Repository;

import java.sql.Connection;
import java.sql.Statement;

public class ShiftConstraintsDAO {

    public int insertToShiftConstraints(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        try {
            for (String type : Ob.getConstraintsMap().keySet()) {
                String toInsertConstraints = String.format("INSERT INTO %s \n" +
                        "VALUES %s;", "ShiftConstraints", Ob.getConstraint(type));

                Statement s;

                s = conn.createStatement();
                s.executeUpdate(toInsertConstraints);


            }
        } catch (Exception e) {
            return 0;
        }
        finally {
            Repository.getInstance().closeConn(conn);
        }



        return 1;
    }
    public int insertToEmployeeInShift(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        try {
            for (int index = 0; index < Ob.getNumberOfEmpInShift(); index++) {
                String toInsert = String.format("INSERT INTO %s \n" +
                        "VALUES %s;", "EmployeesInShift", Ob.getEmployees(index));
                Statement s;

                s = conn.createStatement();
                s.executeUpdate(toInsert);

            }
        } catch (Exception e) {
            return 0;
        }
        finally {
            Repository.getInstance().closeConn(conn);
        }

        return 1;
    }

    public int addConstraints(int ShiftID,String TypeOfEmployee, int amount)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(TypeOfEmployee == null || ShiftID < 0 || amount<0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (%s,\"%s\",%s);", "ShiftConstraints", ShiftID, TypeOfEmployee,amount);
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
        finally
        {
            Repository.getInstance().closeConn(conn);
        }

    }

    public int removeConstraints(int ShiftID,String TypeOfEmployee)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(TypeOfEmployee == null || ShiftID < 0 ) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=%s AND %s=\"%s\";", "ShiftConstraints", "ShiftID", ShiftID,"TypeOfEmployee" ,TypeOfEmployee);
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
        finally
        {
            Repository.getInstance().closeConn(conn);
        }

    }
}
