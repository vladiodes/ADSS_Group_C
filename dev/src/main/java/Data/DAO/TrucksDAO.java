package Data.DAO;

import Data.DTO.TruckDTO;

import java.sql.ResultSet;

public class TrucksDAO extends DAO<TruckDTO> {
    public int insert(TruckDTO Ob) {
        return 0;
    }

    public int update(TruckDTO updatedOb) {
        return 0;
    }

    public TruckDTO makeDTO(ResultSet RS) { //int plateNum, String model, int maxWeight, String type, int factoryWeight
        TruckDTO output = null;
        try {
            output = new TruckDTO(RS.getInt(0),RS.getString(3),RS.getInt(2),RS.getString(4), RS.getInt(1));
        }
        catch (Exception e)
        {
            output = null;
        }
        return output;
    }

    public int delete(TruckDTO ob) {
        return 0;
    }
}
