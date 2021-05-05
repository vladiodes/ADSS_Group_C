package Data.DAO;

import Data.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SectionsDAO extends  DAO<String> {

    public SectionsDAO() {
        this.tableName = "Sections";
    }

    public int insert(String Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String Values = String.format("(\"%s\")", Ob);
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(Values));
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public int update(String updatedOb) { // irrelevant for Sections
        return 0;
    }

    public String makeDTO(ResultSet RS) {
        String output = null;
        try {
            output = RS.getString(1);
        } catch (Exception e) {
            output = null;
        }
        return output;
    }

    public int delete(String ob) {
        Connection conn = Repository.getInstance().connect();
        if (ob == null) return 0;
        String delString = String.format("DELETE FROM Trucks WHERE \"Name\" == %s;", ob);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(delString);
        } catch (Exception e) {
            return 0;
        }
    }
}
