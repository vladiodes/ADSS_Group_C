package Data.DAO;

import Data.DTO.ItemContractDTO;
import Data.DTO.SiteDTO;
import Data.DTO.TransportDTO;
import Data.Repository;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class TransportsDAO extends DAO<TransportDTO> {

    public TransportsDAO() {
        this.tableName = "Transports";
    }

    public int insert(TransportDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if(Ob == null) return 0;
        String Values = String.format("(%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\")",Ob.weight,Ob.date,Ob.ID,Ob.truck,Ob.source,Ob.driver);
        Statement s;
        try {
            s = conn.createStatement();
            s.executeUpdate(InstertStatement(Values));
            for(ItemContractDTO tempContract : Ob.Contracts)
            {
                s = conn.createStatement();
                String currStatement = String.format("INSERT INTO ItemContracts VALUES (%d,%d,\"%s\",\"%s\");",tempContract.ID,Ob.ID,tempContract.destination, tempContract.passed);
                s.executeUpdate(currStatement);
                for(Map.Entry<String,Integer> currItem : tempContract.items.entrySet())
                {
                    s = conn.createStatement();
                    String itemStatement = String.format("INSERT INTO ItemsInItemcontracts VALUES (%d,\"%s\",%d,%d);",currItem.getValue(),currItem.getKey(),tempContract.ID,Ob.ID);
                    s.executeUpdate(itemStatement);
                }
            }
            return 1;
        }
        catch (Exception e ){
            return 0;
        }
    }

    public int update(TransportDTO updatedOb) {
        return 0;
    }

    public TransportDTO makeDTO(ResultSet RS) { //String date, int weight, String driver, String truck, List<ItemContractDTO> contracts, String source,int ID
        TransportDTO output = null;
        try {
            List<ItemContractDTO> ItemContracts = null; //TODO this
            output = new TransportDTO(RS.getString(1), RS.getInt(0), RS.getString(5), RS.getString(3), ItemContracts, RS.getString(4), RS.getInt(2));
        } catch (Exception e) {
            output = null;
        }
        return output;
    }

    public int delete(TransportDTO ob) {
        return 0;
    }
}
