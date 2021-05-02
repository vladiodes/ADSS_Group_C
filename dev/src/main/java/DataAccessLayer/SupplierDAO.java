package DataAccessLayer;

import DTO.SupplierDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDAO extends DAO<SupplierDTO> {
    //no paymentmethod col in supplier table?
    //using repo for get?
    String IDCol="ID",NameCol="Name",SelfPickUpCol="selfPickUp",BankAccountCol="bankAccount",PaymentMethodCol="PaymentMethod",
    INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,NameCol,SelfPickUpCol,BankAccountCol),
    UPDATE_SQL=String.format("Update %s SET %s=?,%s=?,%s=? WHERE ID=?",tableName,NameCol,SelfPickUpCol,BankAccountCol);

    public  SupplierDAO(){super("Supplier");}
    @Override
    public int insert(SupplierDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1, dto.supplierName);
            ps.setString(2,String.valueOf(dto.selfPickUp));
            ps.setString(3,dto.bankAccount);
            ps.executeUpdate();
            id=getInsertedID(con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return id;
    }

    @Override
    public int update(SupplierDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1,dto.supplierName);
            ps.setString(2,String.valueOf(dto.selfPickUp));
            ps.setString(3,dto.bankAccount);
            ps.setString(4,String.valueOf(dto.getSupplierID()));
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public SupplierDTO get(int id){
        ResultSet rs=get("ID",String.valueOf(id));
        SupplierDTO supplier=null;
        try {
            int fetchSize=rs.getFetchSize();
            if(fetchSize!=0){

            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return supplier;
    }

    public int delete(SupplierDTO dto) {
        return delete("ID",String.valueOf(dto.getSupplierID()));
    }
}
