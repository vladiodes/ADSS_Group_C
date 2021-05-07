package Data.DAO;

import Data.DTO.ItemContractDTO;
import Data.DTO.SiteDTO;
import Data.DTO.TransportDTO;
import Data.Repository;
import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransportsDAO extends DAO<TransportDTO> {
    public TransportsDAO() {
        this.tableName = "Transports";
    }

    public int insert(TransportDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String Values = String.format("(%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")", Ob.weight, Ob.date, Ob.ID, Ob.truck, Ob.source, Ob.driver);
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InsertStatement(Values));
            for(ItemContractDTO tempContract : Ob.Contracts)
            {
                s = conn.createStatement();
                String currStatement = String.format("INSERT INTO ItemContracts VALUES (%d,%d,\"%s\",\"%s\");", tempContract.ID, Ob.ID, tempContract.destination, tempContract.passed);
                s.executeUpdate(currStatement);
                for (Map.Entry<String, Integer> currItem : tempContract.items.entrySet()) {
                    s = conn.createStatement();
                    String itemStatement = String.format("INSERT INTO ItemsInItemcontracts VALUES (%d,\"%s\",%d,%d);", currItem.getValue(), currItem.getKey(), tempContract.ID, Ob.ID);
                    s.executeUpdate(itemStatement);
                }
            }
            return 1;
        } catch (Exception e) {
            System.out.print(e.getMessage());
            return 0;
        } finally {
            Repository.getInstance().closeConn(conn);
        }
    }

    public int update(TransportDTO updatedOb) {
        return 0;
    }

    public TransportDTO makeDTO(ResultSet RS) { //String date, int weight, String driver, String truck, List<ItemContractDTO> contracts, String source,int ID
        TransportDTO output = null;
        Connection conn = Repository.getInstance().connect();
        try {
            String date = RS.getString(2);
            int weight = RS.getInt(1);
            String driverID = RS.getString(6);
            String Truckplate = RS.getString(4);
            String Source = RS.getString(5);
            int transportID = RS.getInt(3);
            List<ItemContractDTO> ItemContracts = new ArrayList<ItemContractDTO>();
            ResultSet contractsRS = getWithInt("ItemContracts", "TransportID", transportID,conn);
            while (contractsRS.next()) {
                int itemContractID = contractsRS.getInt(1);
                String destination = contractsRS.getString(3);
                ResultSet itemsRS = get2int("ItemsInItemcontracts", "ItemContractID", itemContractID, "ItemContractTransportID", transportID, conn);
                Boolean passed = contractsRS.getBoolean(4);
                HashMap<String, Integer> items = new HashMap<>();
                while (itemsRS.next()) {
                    int count = itemsRS.getInt(2);
                    String name = itemsRS.getString(1);
                    items.put(name, count);
                }
                ItemContracts.add(new ItemContractDTO(itemContractID, destination, items, passed));
            }
            output = new TransportDTO(date, weight, driverID, Truckplate, ItemContracts, Source, transportID);
        } catch (Exception e) {
            output = null;
        }
        finally{
            Repository.getInstance().closeConn(conn);
        }
        return output;
    }

    public int delete(TransportDTO ob) {
        return 0;
    }
    
}
