package Data;
import Data.DAO.*;
import Data.DTO.*;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import Misc.Pair;

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
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

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
        //Foreign keys skills and availableShift

        String ShiftsTable = "CREATE TABLE IF NOT EXISTS \"Shifts\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY ,\n" +
                "\t\"Date\"\tDate,\n" +
                "\t\"TypeOfShift\"\tTEXT,\n" +
                "\t\"IsSealed\"\tINTEGER\n" +

                ");";

        //Foreign Keys current employee and constraints

        String AvailableShiftsForEmployees = "CREATE TABLE IF NOT EXISTS \"AvailableShiftsForEmployees\" (\n" +
                "\t\"EmpID\"\tTEXT,\n" +
                "\t\"Date\"\tDATE,\n" +
                "\t\"Type\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmpID\",\"Date\",\"Type\"),\n" +
                "\tFOREIGN KEY(\"EmpID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
                ");";

        String EmployeesInShiftTable = "CREATE TABLE IF NOT EXISTS \"EmployeesInShift\" (\n" +
                "\t\"EmployeeID\"\tTEXT,\n" +
                "\t\"ShiftID\"\tINTEGER,\n" +
                "\t\"RoleInShift\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"ShiftID\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE,\n" +
                "\tFOREIGN KEY(\"ShiftID\") REFERENCES \"Shifts\"(\"ID\") ON DELETE CASCADE \n" +
                ");";

        String EmployeeSkillsTable = "CREATE TABLE IF NOT EXISTS \"EmployeeSkills\" (\n" +
                "\t\"EmployeeID\"\tTEXT,\n" +
                "\t\"TypeOfEmployee\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"EmployeeID\",\"TypeOfEmployee\"),\n" +
                "\tFOREIGN KEY(\"EmployeeID\") REFERENCES \"Employees\"(\"ID\") ON DELETE CASCADE\n" +
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
        //EmployeeDAO e = new EmployeeDAO();
        ShiftDAO e = new ShiftDAO();
        Date date = null;
        try
        {
           date = new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022");
        }
        catch (Exception ee)
        {
            System.out.println("Invalid date");
        }
        //List<String> skills = new LinkedList<>();
        //skills.add("HRManager");
        //List<Pair<Date, String>> ava = new LinkedList<>();
        //ava.add(new Pair<Date, String>(date, "Evening"));
        //EmployeeDTO eDTO = new EmployeeDTO("Oded", "Gal", "316327923", "234234", 10000, "sdfsdf", date,skills, ava);
        //int x= e.insert(eDTO);
        //int x2 = e.update(new EmployeeDTO("Oded", "GALLLLL", "316327923", "234234", 9000, "sdfsdf", date,skills, ava));
        //int x3 = e.addAvailableShifts("316327923", date, "Morning");
        //int x4 = e.removeAvailableShifts("316327923", date, "Morning");
        //int x5 = e.insertSkill("316327923", "Driver");
        //int x6 = e.removeSkill("316327923", "Driver");
        //ResultSet rs = e.get("Employees", "ID", "316327923");
        //EmployeeDTO eee = e.makeDTO(rs);
        //e.delete("ID", "316327923");
        //System.out.println(x6);
        //System.out.println(x4);
        //System.out.println(x4);
        //System.out.println(x4);
        Map<String,Integer> constraints = new HashMap<>();
        List<Pair<String, String>> currEmp = new LinkedList<>();
        currEmp.add(new Pair<String, String>("316327923","HRManager"));
        ShiftDTO shiftDTO1 = new ShiftDTO(0,"Morning", date,constraints,currEmp);
        ShiftDTO shiftDTO2 = new ShiftDTO(1,"Evening", date,constraints,currEmp);
        ShiftDTO updatedShiftDTO = new ShiftDTO(1,"Evening", date,constraints,currEmp);
        //int y1 = e.insert(shiftDTO);
        int y1=e.insert(shiftDTO1);
        int y2=e.insert(shiftDTO2);
        //int y1=e.update(updatedShiftDTO);
        //System.out.println(y1);

    }
}
