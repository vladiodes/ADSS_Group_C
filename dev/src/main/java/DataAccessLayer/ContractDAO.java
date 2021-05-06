package DataAccessLayer;

import DTO.CategoryDTO;
import DTO.ContractDTO;

import javax.sql.RowSet;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ContractDAO extends DAO<ContractDTO> {
    String SupplierIDCol="SupplierID",PricePerUnitCol="PricePerUnit",CatalogueIDbySupplierCol="CatalogueIDbySupplier",ItemIDCol="ItemID",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,SupplierIDCol,PricePerUnitCol,CatalogueIDbySupplierCol,ItemIDCol),
            UPDATE_SQL=String.format("Update %s SET %s=? WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName,PricePerUnitCol,CatalogueIDbySupplierCol,SupplierIDCol,ItemIDCol),
            DELETE_SQL=String.format("DELETE FROM %s WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName,CatalogueIDbySupplierCol,SupplierIDCol,ItemIDCol),
            GET_SQL=String.format("SELECT * FROM %s WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName,CatalogueIDbySupplierCol,SupplierIDCol,ItemIDCol);

    public ContractDAO(){
        super("Contract");
    }
    @Override
    public int insert(ContractDTO dto) {
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1,String.valueOf(dto.supplierID));
            ps.setString(2,String.valueOf(dto.pricePerUnit));
            ps.setString(3, String.valueOf(dto.catalogueID));
            ps.setString(4, String.valueOf(dto.storeID));
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
    public int update(ContractDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setString(1,String.valueOf(dto.pricePerUnit));
            ps.setString(2, String.valueOf(dto.catalogueID));
            ps.setString(3,String.valueOf(dto.supplierID));
            ps.setString(3, String.valueOf(dto.storeID));
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public ContractDTO get(int SupplierID,int CatalogueIDbySupplier,int ItemID) {
        ContractDTO output = null;
        Connection con = Repository.getInstance().connect();
        Statement stmt=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        ResultSet discounts=null;
        Map<Integer,Integer> discountByQuantity=new HashMap<>();
        try {
            ps=con.prepareStatement(GET_SQL);
            ps.setString(1,String.valueOf(CatalogueIDbySupplier));
            ps.setString(2,String.valueOf(SupplierID));
            ps.setString(3,String.valueOf(ItemID));
            rs=ps.executeQuery();

            if(!rs.next()){//if the result set is empty
                Repository.getInstance().closeConnection(con);
                return null;
            }

            rs.first();
            String query=String.format("SELECT Quantity ,Discount FROM ContractDiscounts WHERE SupplierID=? AND ItemID=?",SupplierID,ItemID);
            stmt=con.createStatement();
            discounts=stmt.executeQuery(query);
            while (discounts.next()){
                discountByQuantity.putIfAbsent(discounts.getInt(0),discounts.getInt(1));
            }
            output=new ContractDTO(rs.getInt(1),rs.getInt(2),rs.getInt(3),discountByQuantity);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }

    public int delete(int SupplierID,int CatalogueIDbySupplier,int ItemID){
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps=con.prepareStatement(DELETE_SQL);
            ps.setString(1,String.valueOf(CatalogueIDbySupplier));
            ps.setString(2,String.valueOf(SupplierID));
            ps.setString(3,String.valueOf(ItemID));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }
}
