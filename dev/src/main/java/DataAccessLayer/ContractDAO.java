package DataAccessLayer;

import DTO.ContractDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContractDAO extends DAO<ContractDTO> {
    String SupplierIDCol="SupplierID",PricePerUnitCol="PricePerUnit",CatalogueIDbySupplierCol="CatalogueIDbySupplier",ItemIDCol="ItemID",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,SupplierIDCol,PricePerUnitCol,CatalogueIDbySupplierCol,ItemIDCol),
            UPDATE_SQL=String.format("Update %s SET %s=? WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName,PricePerUnitCol,CatalogueIDbySupplierCol,SupplierIDCol,ItemIDCol);

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
        return null;
    }

    public int delete(){return 0;}
}
