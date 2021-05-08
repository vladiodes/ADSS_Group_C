package Data;
import Data.DAO.*;
import Data.DTO.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import Misc.Pair;
import org.sqlite.SQLiteConfig;

public class Repository {
    private static Repository Instance = null;

    private Repository() {
        generateTables();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:database.db";
            // create a connection to the database

            SQLiteConfig config = new SQLiteConfig();
            config.enforceForeignKeys(true);

            conn = DriverManager.getConnection(url,config.toProperties());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }/* finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }*/
        return conn;
    }

    public void closeConn(Connection conn) {
        if (conn == null) return;
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void generateTables() {

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
                "\t\"Source\"\tTEXT,\n" +
                "\t\"Driver\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Truck\") REFERENCES \"Trucks\"(\"Plate Num\"),\n" +
                "\tFOREIGN KEY(\"Driver\") REFERENCES \"Drivers\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Source\") REFERENCES \"Sites\"(\"Address\")\n" +
                ");";

        String ItemcontractsTable = "CREATE TABLE IF NOT EXISTS \"ItemContracts\" (\n" +
                "\t\"ID\"\tINTEGER,\n" +
                "\t\"TransportID\"\tINTEGER,\n" +
                "\t\"Destination\"\tTEXT,\n" +
                "\t\"Passed\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"ID\",\"TransportID\"),\n" +
                "\tFOREIGN KEY(\"TransportID\") REFERENCES \"Transports\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"Destination\") REFERENCES \"Sites\"(\"Address\")\n" +
                ");";

        String ItemsTable = "CREATE TABLE IF NOT EXISTS \"ItemsInItemcontracts\" (\n" +
                "\t\"Count\"\tINTEGER,\n" +
                "\t\"Name\"\tTEXT,\n" +
                "\t\"ItemContractID\"\tINTEGER,\n" +
                "\t\"ItemContractTransportID\"\tINTEGER,\n" +
                "\tPRIMARY KEY(\"Name\",\"ItemContractID\",\"ItemContractTransportID\"),\n" +
                "\tFOREIGN KEY(\"ItemContractID\") REFERENCES \"ItemContracts\"(\"ID\"),\n" +
                "\tFOREIGN KEY(\"ItemContractTransportID\") REFERENCES \"ItemContracts\"(\"TransportID\")\n" +
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
            //Transportation tables--------------------
            Statement stmt = conn.createStatement();
            stmt.execute(SectionsTable);
            stmt.execute(SitesTable);
            stmt.execute(TrucksTable);
            stmt.execute(DriversTable);
            stmt.execute(TransportsTable);
            stmt.execute(ItemcontractsTable);
            stmt.execute(ItemsTable);
            //Employees tables------------------------
            stmt.execute(EmployeesTable);
            stmt.execute(ShiftsTable);
            stmt.execute(AvailableShiftsForEmployees);
            stmt.execute(EmployeesInShiftTable);
            stmt.execute(EmployeeSkillsTable);
            stmt.execute(ShiftConstraintsTable);
            //stmt.execute("INSERT INTO sqlite_sequence VALUES (\"Shifts\",1)");

        } catch (SQLException exception) {
            System.out.println("SQLException");
        } finally {
            closeConn(conn);
        }
    }

    public static Repository getInstance() {
        if (Instance == null)
            Instance = new Repository();
        return Instance;
    }

    public static void main(String[] args) {
       Repository r = getInstance();
        r.generateTables();
       /*
        EmployeeDAO emp = new EmployeeDAO();
        ShiftDAO shift = new ShiftDAO();
        Date date = null;
        try
        {
            date = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022");
        }
        catch (Exception ee)
        {
            System.out.println("Invalid date");
        }
        Date date2 = null;
        try
        {
            date2 = new SimpleDateFormat("dd/MM/yyyy").parse("22/05/2022");
        }
        catch (Exception ee)
        {
            System.out.println("Invalid date");
        }
        List<String> skills = new LinkedList<>();
        skills.add("HRManager");
        List<Pair<Date, String>> ava = new LinkedList<>();
        ava.add(new Pair<Date, String>(date, "Evening"));
        EmployeeDTO eDTO = new EmployeeDTO("Oded", "Gal", "316327923", "234234", 10000, "sdfsdf", date,skills, ava);
        int x= emp.insert(eDTO);
        int x3 = emp.addAvailableShifts("316327923", date, "Morning");
        int x5 = emp.addSkill("316327923", "Driver");

        Map<String,Integer> constraints = new HashMap<>();
        List<Pair<String, String>> currEmp = new LinkedList<>();
        currEmp.add(new Pair<String, String>("316327923","HRManager"));
        ShiftDTO shiftDTO1 = new ShiftDTO(0,"Morning", date,0,constraints,currEmp);
        ShiftDTO shiftDTO2 = new ShiftDTO(1,"Evening", date2,1,constraints,currEmp);
        //ShiftDTO updatedShiftDTO = new ShiftDTO(0,"Evening", date,constraints,currEmp);
        //int y1 = e.insert(shiftDTO);
        int y1=shift.insert(shiftDTO1);
        int y2=shift.insert(shiftDTO2);
        int y3=shift.updateConstraint(date,"Morning","ShiftManager", 5 );
        //int count=e.getShiftIdByDateAndType(date2, "Evening");
        //int count=e.removeEmployeeFromShift("316327923",date, "Evening", "kjsdhfkjds" );
        //int xy=e.removeConstraints(date,"Evening", "Casjutg");
        //System.out.println(xy);
        */


    }
}
