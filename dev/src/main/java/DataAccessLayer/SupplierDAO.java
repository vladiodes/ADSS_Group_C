package DataAccessLayer;

import BusinessLayer.SuppliersModule.DayOfWeek;
import DTO.CategoryDTO;
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
        //@TODO: add all values to the assocation table (contact info, discounts, etc)
        int id=-1;
        Connection con=Repository.getInstance().connect();
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(INSERT_SQL);
            ps.setString(1, dto.supplierName);
            ps.setBoolean(2,dto.selfPickUp);
            ps.setString(3,dto.bankAccount);
            ps.setString(4,String.valueOf(dto.paymentMethod));
            ps.executeUpdate();
            id=getInsertedID(con);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Repository.getInstance().closeConnection(con);
        }
        insertSupplierCategories(id,dto.categories);
        insertSupplierContacts(id,dto.contactInfo);
        insertSupplierDiscounts(id,dto.discountsByPrice);
        inserSupplierFixedDays(id,dto.fixedDays);
        insertSupplierManufactures(id,dto.manufacturers);

        return id;
    }

    private void insertSupplierManufactures(int supplierID, Set<String> manufacturers) {
        for(String manu:manufacturers){
            executeQuery(String.format("INSERT INTO SupplierManufactures (SupplierID,Name) VALUES (%s,\"%s\");",supplierID,manu));
        }
    }

    private void inserSupplierFixedDays(int supplierID, Set<DayOfWeek> fixedDays) {
        for(DayOfWeek day:fixedDays){
            executeQuery(String.format("INSERT INTO SupplierFixedDays (SupplierID,Day) VALUES (%s,\"%s\");",supplierID,day.toString()));
        }
    }

    private void insertSupplierDiscounts(int supplierID, Map<Double, Integer> discountsByPrice) {
        for(Map.Entry<Double,Integer> entry:discountsByPrice.entrySet()){
            executeQuery(String.format("INSERT INTO SupplierDiscountsByPrice (SupplierID,Price,Discount) VALUES (%s,%s,%s);",
                    supplierID,entry.getKey(),entry.getValue()));
        }
    }

    private void insertSupplierContacts(int supplierID, Map<String, String> contactInfo) {
        for(Map.Entry<String,String> entry:contactInfo.entrySet()) {
            executeQuery(String.format("INSERT INTO SupplierContact (SupplierID,Name,PhoneNumber) VALUES (%s,\"%s\",%s);",
                    supplierID, entry.getKey(), entry.getValue()));
        }
    }

    private void insertSupplierCategories(int supplierID, Set<String> categories) {
        for(String cat:categories){
            executeQuery(String.format("INSERT INTO SupplierCatagories (SupplierID,Name) VALUES (%s,\"%s\");",supplierID,cat));
        }
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
        executeQuery("DELETE FROM SupplierFixedDays WHERE SupplierID=" + dto.getSupplierID() + ";");
        executeQuery("DELETE FROM SupplierContact WHERE SupplierID=" + dto.getSupplierID() + ";");
        executeQuery("DELETE FROM SupplierCatagories WHERE SupplierID=" + dto.getSupplierID() + ";");
        executeQuery("DELETE FROM SupplierManufactures WHERE SupplierID=" + dto.getSupplierID() + ";");
        insertSupplierCategories(dto.getSupplierID(),dto.categories);
        insertSupplierContacts(dto.getSupplierID(),dto.contactInfo);
        inserSupplierFixedDays(dto.getSupplierID(),dto.fixedDays);
        insertSupplierManufactures(dto.getSupplierID(),dto.manufacturers);

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
        Set<DayOfWeek> fixedDays=new HashSet<>();
        Statement stmt=null;
        try {
            rs=get("ID",String.valueOf(id),con);
            if(rs.isClosed()){
                Repository.getInstance().closeConnection(con);
                return null;
            }
            //need to get : contracts
            name=rs.getString(NameCol);
            selfpickup=rs.getBoolean(SelfPickUpCol);
            bankAccount=rs.getString(BankAccountCol);
            paymentMethod=rs.getString(PaymentMethodCol);
            String query=String.format("SELECT * FROM SupplierCatagories WHERE SupplierID=%s",id);
            stmt=con.createStatement();
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                categories.add(temprs.getString("Name"));
            }
            query=String.format("SELECT * FROM SupplierManufactures WHERE SupplierID=%s",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                manufacturures.add(temprs.getString("Name"));
            }
            query=String.format("SELECT * FROM SupplierDiscountsByPrice WHERE SupplierID=%s",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                discountsByPrice.put(temprs.getDouble("Price"),temprs.getInt("Discount"));
            }
            query=String.format("SELECT * FROM SupplierContact WHERE SupplierID=%s",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                contactInfo.put(temprs.getString("Name"),temprs.getString("PhoneNumber"));
            }
            query=String.format("SELECT * FROM Orders WHERE SupplierID=%s",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                orderIDs.add(temprs.getInt("ID"));
            }
            query=String.format("SELECT Day FROM SupplierFixedDays WHERE SupplierID=%s",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                fixedDays.add(DayOfWeek.valueOf(temprs.getString("Day")));
            }
            query=String.format("SELECT ItemID,CatalogueIDbySupplier FROM Contract WHERE SupplierID=%s",id);
            temprs=stmt.executeQuery(query);
            while (temprs.next()){
                contracts.put(temprs.getInt("ItemID"),temprs.getInt("CatalogueIDbySupplier"));
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

    public List<SupplierDTO> getAllSuppliers() {
            Connection con=Repository.getInstance().connect();
            ResultSet rs=super.getAll(con);
            ArrayList<SupplierDTO> output=new ArrayList<>();
            try {
                while (rs.next())
                    output.add(get(rs.getInt("ID")));
            }
            catch (SQLException e){
                e.printStackTrace();
            }
            finally {
                Repository.getInstance().closeConnection(con);

            }
            return output;
    }

    public int removeDiscount(int supplierID, double price) {
        return executeQuery(String.format("DELETE FROM SupplierDiscountsByPrice\n" +
                "WHERE SupplierID=%s AND Price=%s;",supplierID,price));

    }

    public int addDiscount(int supplierID, double price, int discountPerecentage) {
        return executeQuery(String.format("INSERT into SupplierDiscountsByPrice (SupplierID,Price,Discount) VALUES (%s,%s,%s);",
                supplierID,price,discountPerecentage));
    }
}
