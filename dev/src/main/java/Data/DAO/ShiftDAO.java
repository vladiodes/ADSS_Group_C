package Data.DAO;

import Business.Misc.Pair;
import Data.DTO.EmployeeDTO;
import Data.DTO.ShiftDTO;
import Data.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

public class ShiftDAO extends DAO<ShiftDTO> {

    int idAI=0;
    public ShiftDAO() {
        this.tableName = "Shifts";
    }
    @Override
    public int insert(ShiftDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String toInsertShift = this.InsertStatement(Ob.fieldsToString(idAI));
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(toInsertShift);
            int resConstraints = insertToShiftConstraints(Ob,idAI );
            int resEmpInShift = insertToEmployeeInShift(Ob, idAI);
            idAI++;
            if (resConstraints + resEmpInShift == 2) {
                return 1;
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
        finally {
            Repository.getInstance().closeConn(conn);
        }
    }

    private int insertToShiftConstraints(ShiftDTO Ob, int shiftId) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        try {
            for (String type : Ob.getConstraintsMap().keySet()) {
                String toInsertConstraints = String.format("INSERT INTO %s \n" +
                        "VALUES %s;", "ShiftConstraints", Ob.getConstraint(type,idAI ));

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

    private int insertToEmployeeInShift(ShiftDTO Ob, int shiftId) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        for (int index = 0; index < Ob.getNumberOfEmpInShift(); index++) {
            String toInsert = String.format("INSERT INTO %s \n" +
                    "VALUES %s;", "EmployeesInShift", Ob.getEmployees(index,shiftId));
            Statement s;
            try {
                s = conn.createStatement();
                s.executeUpdate(toInsert);
            } catch (Exception e) {
                return 0;
            }
        }
        return 1;
    }


    @Override

    public int update(ShiftDTO updatedOb)//not allowed to change ID
    {
        Connection conn = Repository.getInstance().connect();
        if(updatedOb == null) return 0;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String updateString = String.format("UPDATE %s" +
                        " SET  Date= %s " +
                        ", TypeOfShift=\"%s\", IsSealed=\"%s\"" +
                        " WHERE Date == \"%s\" AND TypeOfShift == \"%s\";",
                tableName,formatter.format(updatedOb.date),updatedOb.type,
                updatedOb.isSealed ? 1 : 0, formatter.format(updatedOb.date), updatedOb.type);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }

    public int updateConstraint(int ShiftID,String TypeOfEmployee, int amount)
    {
        Connection conn = Repository.getInstance().connect();
        if(TypeOfEmployee == null || ShiftID < 0 || amount<0) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET  \"ShiftID\"= %s " +
                        ", \"TypeOfEmployee\"=\"%s\",  \"Amount\"=\"%s\"" +
                        "WHERE \"ShiftID\" == \"%s\" AND \"TypeOfEmployee\" == \"%s\";",
                "ShiftConstraints",ShiftID,TypeOfEmployee,
                amount, ShiftID, TypeOfEmployee);
        Statement s;
        try {
            s = conn.createStatement();
            return  s.executeUpdate(updateString);
        }
        catch (Exception e ){
            return 0;
        }
    }


    public int insertEmployeeToShift(String EmployeeID,int ShiftID,String RoleInShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(EmployeeID == null || ShiftID < 0 || RoleInShift == null) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\",\"%s\");", "EmployeesInShift", EmployeeID, ShiftID,RoleInShift);
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


    public int removeEmployeeToShift(String EmployeeID,int ShiftID,String RoleInShift)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(EmployeeID == null || ShiftID < 0 || RoleInShift==null) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=%s AND %s=%s AND %s=%s;", "EmployeesInShift", "EmployeeID", EmployeeID,"ShiftID" ,ShiftID, "RoleInShift", RoleInShift);
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



    public int insertConstraints(int ShiftID,String TypeOfEmployee, int amount)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(TypeOfEmployee == null || ShiftID < 0 || amount<0) return 0;
        updateString= String.format("INSERT INTO %s \n" +
                "VALUES (\"%s\",\"%s\",\"%s\");", "ShiftConstraints", ShiftID, TypeOfEmployee,amount);
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



    public int removeConstraints(int ShiftID,String TypeOfEmployee)
    {
        Connection conn = Repository.getInstance().connect();
        String updateString;
        if(TypeOfEmployee == null || ShiftID < 0 ) return 0;
        updateString= String.format("DELETE FROM %s \n" +
                "WHERE %s=%s AND %s=%s;", "ShiftConstraints", "ShiftID", ShiftID,"TypeOfEmployee" ,TypeOfEmployee);
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



    @Override

    public ShiftDTO makeDTO(ResultSet RS)
    {
        ShiftDTO output ;
        try {
            Map<String, Integer> constraints = getconstraintsList(RS.getInt(1)/*id*/);
            if (constraints == null) {
                return null;
            }
            List<Pair<String/*empId*/, String/*typeOfEmployee*/>> currentShiftEmployees = getcurrentShiftEmployeesList(RS.getInt(1)/*id*/);
            if (currentShiftEmployees == null) {
                return null;
            }
            /*
             public ShiftDTO(Integer Id,String type, Date date)
             */
            output = new ShiftDTO(RS.getInt(1),/*type*/RS.getString(2),/*date*/new SimpleDateFormat("dd/MM/yyyy").parse(RS.getString(3)), constraints, currentShiftEmployees);
        } catch (Exception e) {
            output = null;
        }
        return output;
    }


    private List<Pair<String, String>> getcurrentShiftEmployeesList(int shiftId) {
        List<Pair<String, String>> ans =new LinkedList<>();
        Connection conn = Repository.getInstance().connect();
        ResultSet rs=getWithInt("EmployeesInShift","ShiftID", shiftId, conn  );
        try
        {
            while (rs.next())
            {
                Pair<String, String> p = new Pair<>(rs.getString(1),rs.getString(3)/*type of employee*/);
                ans.add(p);
            }
        } catch (Exception e) {
            return null;
        } finally {
            Repository.getInstance().closeConn(conn);
        }
        return ans;
    }

    private Map<String, Integer> getconstraintsList(int shiftId) {
        Connection conn = Repository.getInstance().connect();
        Map<String, Integer> ans= new HashMap<>();
        ResultSet rs=getWithInt("ShiftConstraints","ShiftID", shiftId,  conn);
        try
        {
            while (rs.next())
            {
                ans.put(rs.getString(2)/*type of employee*/,rs.getInt(4)/*amount*/);

            }
        } catch (Exception e) {
            return null;
        } finally{
            Repository.getInstance().closeConn(conn);
        }
        return ans;
    }

    /*
    //for auto increment
    protected int getInsertedID(){
        Connection conn = Repository.getInstance().connect();
        int output=-1;
        try {
            String query=String.format("SELECT seq FROM sqlite_sequence Where name=\"%s\";",tableName);
            Statement stmt= conn.createStatement();
            ResultSet rs=stmt.executeQuery(query);
            output=rs.getInt("seq");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConn(conn);
        }
        return output;
    }

     */

}
