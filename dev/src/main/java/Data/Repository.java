package Data;
import java.sql.*;


public class Repository {
    private static Repository Instance = null;

    private Repository(){
        generateTables();
    }

    public Connection connect(){
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return conn;
    }

    public void closeConn(Connection conn)
    {
        if (conn == null) return;
        try
        {
            conn.close();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    private void generateTables(){
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
        String DriversTable = "CREATE TABLE IF NOT EXISTS \"Drivers\" (\n" +
                "\t\"ID\"\tTEXT,\n" +
                "\t\"License\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\")\n" +
                ");";
        String TransportsTable = "CREATE TABLE IF NOT EXISTS \"Transports\" (\n" +
                "\t\"Weight\"\tINTEGER,\n" +
                "\t\"Date\"\tTEXT,\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"Truck\"\tINTEGER,\n" +
                "\t\"Source\"\tTEXT,\n" +
                "\t\"Driver\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Truck\") REFERENCES \"Trucks\"(\"Plate Num\"),\n" +
                "\tFOREIGN KEY(\"Driver\") REFERENCES \"Drivers\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Source\") REFERENCES \"Sites\"(\"Address\")\n" +
                ");";
        String ItemcontractsTable = "CREATE TABLE IF NOT EXISTS \"ItemContracts\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"TransportID\"\tINTEGER,\n" +
                "\t\"Destination\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\",\"TransportID\"),\n" +
                "\tFOREIGN KEY(\"Destination\") REFERENCES \"Sites\"(\"Address\")\n" +
                ");";
        String ItemsTable = "CREATE TABLE IF NOT EXISTS \"ItemsInItemcontracts\" (\n" +
                "\t\"Count\"\tINTEGER,\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"ItemContractID\"\tINTEGER,\n" +
                "\t\"ItemContractTransportID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"ID\",\"ItemContractID\",\"ItemContractTransportID\"),\n" +
                "\tFOREIGN KEY(\"ItemContractID\") REFERENCES \"ItemContracts\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"ItemContractTransportID\") REFERENCES \"ItemContracts\"(\"TransportID\")\n" +
                ");";
        Connection conn = connect();
        if (conn == null) return;
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(SectionsTable);
            stmt.execute(SitesTable);
            stmt.execute(TrucksTable);
            stmt.execute(DriversTable);
            stmt.execute(DriversTable);
            stmt.execute(TransportsTable);
            stmt.execute(ItemcontractsTable);
            stmt.execute(ItemsTable);
        } catch (SQLException exception) {
            System.out.println("SQLException");
        } finally {
            closeConn(conn);
        }
    }

    public static Repository getInstance(){
        if( Instance == null)
            Instance = new Repository();
        return Instance;
    }
    
}
