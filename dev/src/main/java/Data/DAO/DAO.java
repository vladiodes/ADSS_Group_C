package Data.DAO;
import Data.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {
    protected String tableName;

    public abstract int insert(T Ob);

    public abstract int update(T updatedOb);

    public List<T> getAll() {
        String statement = "SELECT * FROM " + tableName + ";";
        Connection conn = Repository.getInstance().connect();
        ResultSet RS = null;
        List<T> output = new ArrayList<T>();
        try {
            Statement S = conn.createStatement();
            RS = S.executeQuery(statement);
            while (RS.next())
                output.add(makeDTO(RS));
        } catch (Exception e) {
        } finally {
            Repository.getInstance().closeConn(conn);
        }
        return output;
    }

    public abstract T makeDTO(ResultSet RS);

    public int delete(String colName,String value)
    {
        String DELETE_SQL=String.format("Delete From %s WHERE %s=\"%s\"",tableName,colName,value);
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        try {
            Statement stmt=con.createStatement();
            rowsAffected=stmt.executeUpdate(DELETE_SQL);
        } catch (SQLException e) {
        } finally {
            Repository.getInstance().closeConn(con);
        }
        return rowsAffected;
    }
    protected String InsertStatement(String Values) {
        return String.format("INSERT INTO %s \n" +
                "VALUES %s;", tableName, Values);
    }

    public ResultSet get(String nameOfTable, String colName, String value, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE \"%s\"=\"%s\"", nameOfTable, colName, value);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }

        return rs;
    }

    protected ResultSet getWithInt(String nameOfTable, String colName, int value, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE %s=%s", nameOfTable, colName, value);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }
        return rs;
    }

    protected ResultSet get2int(String nameOfTable, String colName1, int value1, String colName2, int value2, Connection con) {
        String SELECT_SQL = String.format("SELECT * FROM %s WHERE %s=%d AND %s=%d", nameOfTable, colName1, value1, colName2, value2);
        ResultSet rs = null;
        try {
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(SELECT_SQL);
        } catch (SQLException e) {
        }
        return rs;
    }
}
