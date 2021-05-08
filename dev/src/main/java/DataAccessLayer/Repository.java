package DataAccessLayer;

import DTO.ItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    //@TODO: add a DAL to the system
    private static Repository instance = null;

    private Repository() {
        createTables();
    }

    public static Repository getInstance() {
        if (instance == null)
            instance = new Repository();
        return instance;
    }

    protected Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:database.db";
            conn = DriverManager.getConnection(url);

        } catch (SQLException e) {
            e.printStackTrace();}
        return conn;
    }

    protected void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public List<Integer> getIds(String query){
        ResultSet rs = null;
        Statement stmt=null;
        ArrayList<Integer> Ids = new ArrayList<>();
        Connection con = connect();
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (rs == null) return null;
        try {
            while (rs.next())
                Ids.add(rs.getInt("ID"));
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection(con);
        }
        return Ids;
    }


    private void createTables() {
        String categoriesTable = "CREATE TABLE IF NOT EXISTS \"Category\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\t\"ParentCategoryID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"ParentCategoryID\") REFERENCES \"Category\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String contractsTable = "CREATE TABLE IF NOT EXISTS \"Contract\" (\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"PricePerUnit\"\tREAL NOT NULL,\n" +
                "\t\"CatalogueIDbySupplier\"\tINTEGER NOT NULL,\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"CatalogueIDbySupplier\",\"SupplierID\",\"ItemID\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String contractDiscountsTable = "CREATE TABLE IF NOT EXISTS \"ContractDiscounts\" (\n" +
                "\t\"CatalogueID\"\tINTEGER NOT NULL,\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"Quantity\"\tINTEGER NOT NULL,\n" +
                "\t\"Discount\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"CatalogueID\",\"ItemID\",\"SupplierID\",\"Quantity\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"CatalogueID\") REFERENCES \"Contract\"(\"CatalogueIDbySupplier\") ON DELETE CASCADE\n" +
                ");";
        String ItemsTable = "CREATE TABLE IF NOT EXISTS \"Item\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"Location\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\t\"minAmmount\"\tINTEGER NOT NULL,\n" +
                "\t\"Producer\"\tTEXT NOT NULL,\n" +
                "\t\"SellingPrice\"\tINTEGER NOT NULL,\n" +
                "\t\"CategoryID\"\tINTEGER NOT NULL,\n" +
                "\t\"Alert\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"CategoryID\") REFERENCES \"Category\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String ItemsInReport = "CREATE TABLE IF NOT EXISTS \"ItemsInReport\" (\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\t\"ReportID\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"ItemID\",\"ReportID\"),\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"ReportID\") REFERENCES \"Report\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String OrdersTable = "CREATE TABLE IF NOT EXISTS \"Orders\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"DateOfOrder\"\tDateTime NOT NULL,\n" +
                "\t\"ShipmentStatus\"\tINTEGER NOT NULL,\n" +
                "\t\"PriceBeforeDiscount\"\tREAL NOT NULL,\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"PriceAfterDiscount\"\tREAL NOT NULL,\n" +
                "\t\"isFixed\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String PIOTable = "CREATE TABLE IF NOT EXISTS \"ProductsInOrder\" (\n" +
                "\t\"OrderID\"\tINTEGER NOT NULL,\n" +
                "\t\"Quantity\"\tINTEGER NOT NULL,\n" +
                "\t\"TotalPrice\"\tINTEGER NOT NULL,\n" +
                "\t\"CatalogueID\"\tINTEGER NOT NULL,\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"CatalogueID\") REFERENCES \"Contract\"(\"CatalogueIDbySupplier\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"OrderID\") REFERENCES \"Order\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"OrderID\",\"CatalogueID\",\"SupplierID\",\"ItemID\")\n" +
                ");";
        String ReportTable = "CREATE TABLE IF NOT EXISTS \"Report\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"StartDate\"\tDateTime NOT NULL,\n" +
                "\t\"EndDate\"\tDateTime NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");";
        String SaleTable = "CREATE TABLE IF NOT EXISTS \"Sale\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"Quantity\"\tINTEGER NOT NULL,\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\t\"SaleDate\"\tDateTime NOT NULL,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");";
        String SaleReportTable = "CREATE TABLE IF NOT EXISTS \"SaleReport\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"StartDate\"\tDateTime NOT NULL,\n" +
                "\t\"EndDate\"\tDateTime NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");";
        String SalesInReportTable = "CREATE TABLE IF NOT EXISTS \"SalesInReport\" (\n" +
                "\t\"SaleID\"\tINTEGER NOT NULL,\n" +
                "\t\"SaleReportID\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"SaleID\",\"SaleReportID\"),\n" +
                "\tFOREIGN KEY(\"SaleID\") REFERENCES \"Sale\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"SaleReportID\") REFERENCES \"SaleReport\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String SuppliersTable = "CREATE TABLE IF NOT EXISTS \"Supplier\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"selfPickUp\"\tBoolean NOT NULL,\n" +
                "\t\"bankAccount\"\tTEXT NOT NULL,\n" +
                "\t\"paymentMethod\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");";
        String SupplierCat = "CREATE TABLE IF NOT EXISTS \"SupplierCatagories\" (\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"Name\",\"SupplierID\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String SupplierContact = "CREATE TABLE IF NOT EXISTS \"SupplierContact\" (\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\t\"PhoneNumber\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"SupplierID\",\"Name\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String SupplierDiscounts = "CREATE TABLE IF NOT EXISTS \"SupplierDiscountsByPrice\" (\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"Price\"\tREAL NOT NULL,\n" +
                "\t\"Discount\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"SupplierID\",\"Price\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String SupplierFixedDays = "CREATE TABLE IF NOT EXISTS \"SupplierFixedDays\" (\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"Day\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"Day\",\"SupplierID\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String SupplierManu = "CREATE TABLE IF NOT EXISTS \"SupplierManufactures\" (\n" +
                "\t\"SupplierID\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"Name\",\"SupplierID\"),\n" +
                "\tFOREIGN KEY(\"SupplierID\") REFERENCES \"Supplier\"(\"ID\") ON DELETE CASCADE\n" +
                ");";
        String specificItem="CREATE TABLE IF NOT EXISTS \"SpecificItems\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"expDate\"\tDateTime NOT NULL,\n" +
                "\t\"storageAmount\"\tINTEGER NOT NULL,\n" +
                "\t\"shelfAmount\"\tINTEGER NOT NULL,\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\"),\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");";

        Connection conn = connect();
        if (conn == null) return;
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(categoriesTable);
            stmt.execute(contractsTable);
            stmt.execute(contractDiscountsTable);
            stmt.execute(ItemsTable);
            stmt.execute(ItemsInReport);
            stmt.execute(OrdersTable);
            stmt.execute(PIOTable);
            stmt.execute(ReportTable);
            stmt.execute(SaleTable);
            stmt.execute(SaleReportTable);
            stmt.execute(SuppliersTable);
            stmt.execute(SupplierCat);
            stmt.execute(SupplierContact);
            stmt.execute(SupplierDiscounts);
            stmt.execute(SupplierFixedDays);
            stmt.execute(SupplierManu);
            stmt.execute(SalesInReportTable);
            stmt.execute(specificItem);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}
