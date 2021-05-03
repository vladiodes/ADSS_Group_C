package Data.DAO;

import Data.DTO.ItemContractDTO;
import Data.DTO.SiteDTO;
import Data.DTO.TransportDTO;

import java.sql.ResultSet;
import java.util.List;

public class TransportsDAO extends DAO<TransportDTO>{

    public TransportsDAO(){
        this.tableName = "Transports";
    }

    public int insert(TransportDTO Ob) {
        return 0;
    }

    public int update(TransportDTO updatedOb) {
        return 0;
    }

    public TransportDTO makeDTO(ResultSet RS) { //String date, int weight, String driver, String truck, List<ItemContractDTO> contracts, String source,int ID
        TransportDTO output = null;
        try {
            List<ItemContractDTO> ItemContracts = null; //TODO this
            output = new TransportDTO(RS.getString(1), RS.getInt(0), RS.getString(5), RS.getString(3), ItemContracts, RS.getString(4),RS.getInt(2));
        }
        catch (Exception e)
        {
            output = null;
        }
        return output;
    }

    public int delete(TransportDTO ob) {
        return 0;
    }
}
