package Data.DAO;
import Data.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DAO<T> {
    protected String tableName;
    public abstract int insert(T Ob);
    public abstract int update(T updatedOb);
    public List<T> getAll(){
        String statement= "SELECT * FROM "+tableName+";";
        Connection conn = Repository.getInstance().connect();
        ResultSet RS = null;
        List<T> output = new ArrayList<T>();
        try {
            Statement S = conn.createStatement();
            RS = S.executeQuery(statement);
            while(RS.next())
                output.add(makeDTO(RS));
        }
        catch (Exception e ){

        }
        finally {
            Repository.getInstance().closeConn(conn);
        }
        return output;
    }
    public abstract T makeDTO(ResultSet RS);
    public abstract int delete(T ob);
    protected String InstertStatement(String Values) {
        return String.format("INSERT INTO %s \n" +
                "VALUES %s;", tableName, Values);
    }
}
