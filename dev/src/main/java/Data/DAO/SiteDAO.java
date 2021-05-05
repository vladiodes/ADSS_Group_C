package Data.DAO;

import Data.DTO.SiteDTO;
import Data.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SiteDAO extends DAO<SiteDTO> {

    public SiteDAO() {
        this.tableName = "Sites";
    }

    public int insert(SiteDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String Values = String.format("(\"%s\",\"%s\",\"%s\",\"%s\")", Ob.contact, Ob.phoneNumber, Ob.address, Ob.Section);
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(Values));
            return 1;
        } catch (Exception e) {
            return 0;
        } finally {
            Repository.getInstance().closeConn(conn);
        }
    }

    public int update(SiteDTO updatedOb) {
        Connection conn = Repository.getInstance().connect();
        if (updatedOb == null) return 0;
        String updateString = String.format("UPDATE %s" +
                        " SET \"Contact\"= \"%s\", \"Phone Number\"= \"%s\", \"Address\"= \"%s\" ,\"Section\"= \"%s\" " +
                        "WHERE \"Address\" == \"%s\";",
                tableName, updatedOb.contact, updatedOb.phoneNumber, updatedOb.address, updatedOb.Section, updatedOb.address);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(updateString);
        } catch (Exception e) {
            return 0;
        } finally {
            Repository.getInstance().closeConn(conn);
        }
    }

    public SiteDTO makeDTO(ResultSet RS) { //String address, String contact, String phoneNumber, String section
        SiteDTO output = null;
        try {
            output = new SiteDTO(RS.getString(3), RS.getString(1), RS.getString(2), RS.getString(4));
        } catch (Exception e) {
            output = null;
        }
        return output;
    }

    public int delete(SiteDTO ob) {
        Connection conn = Repository.getInstance().connect();
        if (ob == null) return 0;
        String delString = String.format("DELETE FROM Sites WHERE \"Address\" == \"%s\";", ob.address);
        Statement s;
        try {
            s = conn.createStatement();
            return s.executeUpdate(delString);
        } catch (Exception e) {
            return 0;
        } finally {
            Repository.getInstance().closeConn(conn);
        }
    }
}
