package DataAccessLayer;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {

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
                "\t\"Weight\"\tINTEGER NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"CategoryID\") REFERENCES \"Category\"(\"ID\") ON DELETE CASCADE\n" +
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
        String SaleTable = "CREATE TABLE IF NOT EXISTS \"Sale\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"Quantity\"\tINTEGER NOT NULL,\n" +
                "\t\"ItemID\"\tINTEGER NOT NULL,\n" +
                "\t\"SaleDate\"\tDateTime NOT NULL,\n" +
                "\tFOREIGN KEY(\"ItemID\") REFERENCES \"Item\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT)\n" +
                ");";
        String SuppliersTable = "CREATE TABLE IF NOT EXISTS \"Supplier\" (\n" +
                "\t\"ID\"\tINTEGER NOT NULL,\n" +
                "\t\"Name\"\tTEXT NOT NULL UNIQUE,\n" +
                "\t\"selfPickUp\"\tBoolean NOT NULL,\n" +
                "\t\"bankAccount\"\tTEXT NOT NULL,\n" +
                "\t\"paymentMethod\"\tTEXT NOT NULL,\n" +
                "\t\"Address\"\tTEXT NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"Address\") REFERENCES \"Sites\"(\"Address\")\n" +
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
        //Transportation tables--------------------------------------------------------------------------

        String SectionsTable = "CREATE TABLE IF NOT EXISTS \"Sections\" (\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Name\")\n" +
                ");";

        String SitesTable = "CREATE TABLE IF NOT EXISTS \"Sites\" (\n" +
                "\t\"Contact\"\tTEXT,\n" +
                "\t\"Phone Number\"\tTEXT,\n" +
                "\t\"Address\"\tTEXT,\n" +
                "\t\"Section\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Address\"),\n" +
                "\tFOREIGN KEY(\"Section\") REFERENCES \"Sections\"(\"Name\")\n" +
                ");";

        String TrucksTable = "CREATE TABLE IF NOT EXISTS \"Trucks\" (\n" +
                "\t\"Plate Num\"\tTEXT,\n" +
                "\t\"Factory Weight\"\tINTEGER,\n" +
                "\t\"Max Weight\"\tINTEGER,\n" +
                "\t\"Model\"\tTEXT,\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"Plate Num\")\n" +
                ");";

        String TransportsTable = "CREATE TABLE IF NOT EXISTS \"Transports\" (\n" +
                "\t\"Weight\"\tINTEGER,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"Truck\"\tTEXT,\n" +
                "\t\"Driver\"\tTEXT,\n" +
                "\t\"wasDelivered\"\tBoolean NOT NULL,\n" +
                "\tPRIMARY KEY(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Driver\") REFERENCES \"Drivers\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Truck\") REFERENCES \"Trucks\"(\"Plate Num\")\n" +
                ");";

        //Employees Tables ------------------------------------------------------------------------------------------------------------------

        String DriversTable = "CREATE TABLE IF NOT EXISTS \"Drivers\" (\n" +
                "\t\"FirstName\"\tTEXT,\n" +
                "\t\"LastName\"\tTEXT,\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"BankAccountNumber\"\tTEXT,\n" +
                "\t\"Salary\"\tINTEGER,\n" +
                "\t\"EmpConditions\"\tTEXT,\n" +
                "\t\"StartWorkingDate\"\tTEXT,\n" +
                "\t\"License\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
                ");";

        String EmployeesTable = "CREATE TABLE IF NOT EXISTS \"Employees\" (\n" +
                "\t\"FirstName\"\tTEXT,\n" +
                "\t\"LastName\"\tTEXT,\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"BankAccountNumber\"\tTEXT,\n" +
                "\t\"Salary\"\tINTEGER,\n" +
                "\t\"EmpConditions\"\tTEXT,\n" +
                "\t\"StartWorkingDate\"\tDATE,\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
                ");";

        String ShiftsTable = "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY ,\n" +
                "\t\"Date\"\tDate,\n" +
                "\t\"TypeOfShift\"\tTEXT,\n" +
                "\t\"IsSealed\"\tINTEGER\n" +

                ");";


        String AvailableShiftsForEmployees = "CREATE TABLE IF NOT EXISTS \"AvailableShiftsForEmployees\" (\n" +
                "\t\"EmpID\"\tTEXT,\n" +
                "\t\"Date\"\tDATE,\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\t\"DriverID\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmpID\",\"Date\",\"Type\"),\n" +
                "\tFOREIGN KEY(\"EmpID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" +
                ");";

        String EmployeesInShiftTable = "CREATE TABLE IF NOT EXISTS \"EmployeesInShift\" (\n" +
                "\t\"EmployeeID\"\tTEXT,\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"RoleInShift\"\tTEXT,\n" +
                "\t\"DriverID\"\tTEXT,\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ID\") ON DELETE CASCADE \n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE \n" +
                ");";

        String EmployeeSkillsTable = "CREATE TABLE IF NOT EXISTS \"EmployeeSkills\" (\n" +
                "\t\"EmployeeID\"\tTEXT,\n" +
                "\t\"TypeOfEmployee\"\tTEXT,\n" +
                "\t\"DriverID\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"TypeOfEmployee\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                "\tFOREIGN KEY(\"DriverID\") REFERENCES \"Drivers\"(\"ID\") ON DELETE CASCADE\n" +
                ");";

        String ShiftConstraintsTable = "CREATE TABLE IF NOT EXISTS \"ShiftConstraints\" (\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"TypeOfEmployee\"\tTEXT,\n" +
                "\t\"Amount\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ShiftID\",\"TypeOfEmployee\"),\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ID\") ON DELETE CASCADE\n" +
                ");";

        Connection conn = connect();
        if (conn == null) return;
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(categoriesTable);
            stmt.execute(contractsTable);
            stmt.execute(contractDiscountsTable);
            stmt.execute(ItemsTable);
            stmt.execute(OrdersTable);
            stmt.execute(PIOTable);
            stmt.execute(SaleTable);
            stmt.execute(SuppliersTable);
            stmt.execute(SupplierCat);
            stmt.execute(SupplierContact);
            stmt.execute(SupplierDiscounts);
            stmt.execute(SupplierFixedDays);
            stmt.execute(SupplierManu);
            stmt.execute(specificItem);

            //Transportation tables--------------------
            stmt.execute(SectionsTable);
            stmt.execute(SitesTable);
            stmt.execute(TrucksTable);
            stmt.execute(DriversTable);
            stmt.execute(TransportsTable);
            //Employees tables------------------------
            stmt.execute(EmployeesTable);
            stmt.execute(ShiftsTable);
            stmt.execute(AvailableShiftsForEmployees);
            stmt.execute(EmployeesInShiftTable);
            stmt.execute(EmployeeSkillsTable);
            stmt.execute(ShiftConstraintsTable);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

}
