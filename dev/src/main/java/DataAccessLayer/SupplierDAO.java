package DataAccessLayer;

import BusinessLayer.SuppliersModule.Supplier;
import DTO.SupplierDTO;

import java.sql.*;
import java.util.*;

public class SupplierDAO extends DAO<SupplierDTO> {
    String IDCol="ID",NameCol="Name",SelfPickUpCol="selfPickUp",BankAccountCol="bankAccount",PaymentMethodCol="paymentMethod",
    INSERT_SQL=String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?)",tableName,NameCol,SelfPickUpCol,BankAccountCol,PaymentMethodCol),
    UPDATE_SQL=String.format("Update %s SET %s=?,%s=?,%s=?,%s=? WHERE ID=?",tableName,NameCol,SelfPickUpCol,BankAccountCol,PaymentMethodCol,IDCol);

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
            ps.setString(4,String.valueOf(dto.paymentMethod));
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
            ps.setString(4,String.valueOf(dto.paymentMethod));
            ps.setString(5,String.valueOf(dto.getSupplierID()));
            rowsAffected=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        return rowsAffected;
    }

    public SupplierDTO get(int id){
        SupplierDTO output=null;
        Connection con=Repository.getInstance().connect();
        ResultSet rs=null;
        ResultSet temprs=null;
        String name;
        String bankAccount;
        String paymentMethod;
        boolean selfpickup;
        Set<String> categories=new LinkedHashSet<>();
        Set<String> manufacturures=new LinkedHashSet<>();
        Map<Double,Integer> discountsByPrice=new HashMap<>();
        Map<String,String> contactInfo=new HashMap<>();
        Map<Integer,Integer> contracts=new HashMap<>();
        List<Integer> orderIDs=new LinkedList<>();
        List<Integer> fixedDays=new LinkedList<>();
        Statement stmt=null;
        try {
            rs=get("ID",String.valueOf(id),con);
            if(!rs.next()){
                Repository.getInstance().closeConnection(con);
                return null;
            }
            rs.first();
            //need to get : contracts
            name=rs.getString(1);
            selfpickup=rs.getBoolean(2);
            bankAccount=rs.getString(3);
            paymentMethod=rs.getString(4);
            String query=String.format("SELECT Name FROM SupplierCatagories WHERE SupplierID=?",id);
            stmt=con.createStatement();
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                categories.add(temprs.getString(0));
            }
            query=String.format("SELECT Name FROM SupplierManufactures WHERE SupplierID=?",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                manufacturures.add(temprs.getString(0));
            }
            query=String.format("SELECT Price,Discount FROM SupplierDiscountsByPrice WHERE SupplierID=?",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                discountsByPrice.put(temprs.getDouble(0),temprs.getInt(1));
            }
            query=String.format("SELECT Name, PhoneNumber FROM SupplierContact WHERE SupplierID=?",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                contactInfo.put(temprs.getString(0),temprs.getString(1));
            }
            query=String.format("SELECT ID FROM Order WHERE SupplierID=?",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                orderIDs.add(temprs.getInt(0));
            }
            query=String.format("SELECT Day FROM SupplierFixedDays WHERE SupplierID=?",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                fixedDays.add(temprs.getInt(0));
            }
            query=String.format("SELECT ItemID,CatalogueIDbySupplier FROM Contract WHERE SupplierID=?",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                contracts.put(temprs.getInt(0),temprs.getInt(1));
            }
            output=new SupplierDTO(name,fixedDays,selfpickup,id,bankAccount,paymentMethod,categories,manufacturures,contactInfo,discountsByPrice,orderIDs,contracts);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            Repository.getInstance().closeConnection(con);
        }
        return output;
    }

    public int delete(SupplierDTO dto) {
        return  delete("ID",String.valueOf(dto.getSupplierID()));
    }
}
