package DataAccessLayer;

import DTO.CategoryDTO;
import DTO.ContractDTO;

import javax.sql.RowSet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractDAO extends DAO<ContractDTO> {
    String SupplierIDCol="SupplierID",PricePerUnitCol="PricePerUnit",CatalogueIDbySupplierCol="CatalogueIDbySupplier",ItemIDCol="ItemID",
            INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,SupplierIDCol,PricePerUnitCol,CatalogueIDbySupplierCol,ItemIDCol),
            UPDATE_SQL=String.format("Update %s SET %s=? WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName,PricePerUnitCol),
            DELETE_SQL=String.format("DELETE FROM %s WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName),
            GET_SQL=String.format("SELECT * FROM %s WHERE CatalogueIDbySupplier=? AND SupplierID=? AND ItemID=?",tableName);

    public ContractDAO(){
        super("Contract");
    }
    @Override
    public int insert(ContractDTO dto) {
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1,String.valueOf(dto.supplierID));
            ps.setString(2,String.valueOf(dto.pricePerUnit));
            ps.setString(3, String.valueOf(dto.catalogueID));
            ps.setString(4, String.valueOf(dto.storeID));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        for(Map.Entry<Integer,Integer> entry:dto.discountByQuantity.entrySet()){
            executeQuery(String.format("INSERT INTO ContractDiscounts (CatalogueID,ItemID,SupplierID,Quantity,Discount) VALUES (%s,%s,%s,%s,%s);"
                    ,dto.catalogueID,dto.storeID,dto.supplierID,entry.getKey(),entry.getValue()));
        }
        return 1; //irrelevant
    }

    @Override
    public int update(ContractDTO dto) {
        int rowsAffected=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(UPDATE_SQL);
            ps.setInt(1,dto.catalogueID);
            ps.setInt(2, dto.supplierID);
            ps.setInt(3,dto.storeID);
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
            ps.setInt(1,CatalogueIDbySupplier);
            ps.setInt(2,SupplierID);
            ps.setInt(3,ItemID);
            rs=ps.executeQuery();

            if(rs.isClosed()){//if the result set is empty
                Repository.getInstance().closeConnection(con);
                return null;
            }

            String query=String.format("SELECT Quantity ,Discount FROM ContractDiscounts WHERE SupplierID=%s AND ItemID=%s",SupplierID,ItemID);
            stmt=con.createStatement();
            discounts=stmt.executeQuery(query);
            while (discounts.next()){
                discountByQuantity.putIfAbsent(discounts.getInt(1),discounts.getInt(2));
            }
            output=new ContractDTO(rs.getDouble(PricePerUnitCol),rs.getInt(CatalogueIDbySupplierCol),rs.getInt(ItemIDCol),discountByQuantity,SupplierID);
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
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public int removeDiscount(ContractDTO contractDTO, int quantity) {
        return executeQuery(String.format("DELETE FROM ContractDiscounts WHERE\n" +
                "CatalogueID=%s AND ItemID=%s AND SupplierID=%s AND Quantity=%s",contractDTO.catalogueID,contractDTO.storeID,contractDTO.supplierID,quantity));
    }
    public int insertDiscount(ContractDTO contractDTO, int quantity,int discount)
    {
        String CatalogueID = "CatalogueID";
        String ItemID = "ItemID";
        String SupplierID = "SupplierID";
        String Quantity = "Quantity";
        String Discount = "Discount";
        INSERT_SQL=String.format("INSERT INTO ContractDiscounts (%s,%s,%s,%s,%s) VALUES(?,?,?,?,?)",CatalogueID,ItemID,SupplierID,Quantity,Discount);
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setInt(1,contractDTO.catalogueID);
            ps.setInt(2,contractDTO.storeID);
            ps.setInt(3, contractDTO.supplierID);
            ps.setInt(4, quantity);
            ps.setInt(5,discount);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return 1; //irrelevant
    }
    public List<ContractDTO> getAllContracts() {
            Connection con=Repository.getInstance().connect();
            ResultSet rs=super.getAll(con);
            ArrayList<ContractDTO> output=new ArrayList<>();
            try {
                while (rs.next()) {
                    Map<Integer,Integer> discountByQuantity=new HashMap<>();
                    String query = String.format("SELECT Quantity ,Discount FROM ContractDiscounts WHERE SupplierID=%s AND ItemID=%s", rs.getInt(SupplierIDCol), rs.getInt(ItemIDCol));
                    Statement stmt = con.createStatement();
                    ResultSet discounts = stmt.executeQuery(query);
                    while (discounts.next()) {
                        discountByQuantity.putIfAbsent(discounts.getInt("Quantity"), discounts.getInt("Discount"));
                    }
                    output.add(new ContractDTO(rs.getDouble(PricePerUnitCol),rs.getInt(CatalogueIDbySupplierCol),rs.getInt(ItemIDCol),discountByQuantity,rs.getInt(SupplierIDCol)));
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            finally {
                Repository.getInstance().closeConnection(con);
            }
            return output;
    }
}
