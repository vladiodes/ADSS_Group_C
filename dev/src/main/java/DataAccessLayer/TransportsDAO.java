package DataAccessLayer;

import DTO.OrderDTO;
import DTO.TransportDTO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransportsDAO extends DAOV2<TransportDTO> {
    public TransportsDAO() {
        this.tableName = "Transports";
    }

    public int insert(TransportDTO Ob) {
        Connection conn = Repository.getInstance().connect();
        if (Ob == null) return 0;
        String Values = String.format("(%d,\"%s\",\"%s\",\"%s\",\"%s\", \"%s\")", Ob.weight,Ob.date, Ob.ID, Ob.truck, Ob.driver, Ob.wasDelivered);
        Statement s;
        try {
            s = conn.createStatement();
            String StatementS = InsertStatement(Values);
            s.executeUpdate(StatementS);
            for(OrderDTO currOrd : Ob.orders)
            {
                s = conn.createStatement();
                String currStatement = String.format("INSERT INTO TransportsOrders VALUES (%d,\"%s\");", currOrd.orderID, Ob.ID);
                s.executeUpdate(currStatement);
            }
            return 1;
        } catch (Exception e) {
            return 0;
        } finally {
            Repository.getInstance().closeConnection(conn);
        }
    }

    public int update(TransportDTO updatedOb) {
        if(updatedOb == null)
            return 0;
        Connection conn = Repository.getInstance().connect();
        Statement s;
        try {
            //updating transport core stats
            s = conn.createStatement();
            s.executeUpdate(String.format("UPDATE Transports SET Weight = %d, Date = \"%s\", Truck=\"%s\", Driver = \"%s\", wasDelivered = %s WHERE ID =\"%s\";",updatedOb.weight, updatedOb.date,updatedOb.truck,updatedOb.driver,updatedOb.wasDelivered, updatedOb.ID));
            //update orders
            s = conn.createStatement();
            s.executeUpdate(String.format("DELETE FROM TransportsOrders WHERE TransportID =  \"%s\";", updatedOb.ID));
            for(OrderDTO ord : updatedOb.orders)
            {
                s.executeUpdate(String.format("INSERT INTO TransportsOrders VALUES (%d,\"%s\");", ord.orderID, updatedOb.ID));
            }
        } catch (Exception e) {
            System.out.println("yeepeeti doo this happend to you: "+e.getMessage());
        }
        finally{
            Repository.getInstance().closeConnection(conn);
        }
        return 1;
    }

    public TransportDTO makeDTO(ResultSet RS) { //String date, int weight, String driver, String truck, List<ItemContractDTO> contracts, String source,int ID
       TransportDTO output = null;
       Connection conn = Repository.getInstance().connect();
       OrderDAO OrderDAO = new OrderDAO();
        try {
            String date = RS.getString(2);
            int weight = RS.getInt(1);
            String driverID = RS.getString(5);
            String Truckplate = RS.getString(4);
            int transportID = RS.getInt(3);
            List<OrderDTO> Orders = new ArrayList<OrderDTO>();
            boolean wasDel = RS.getBoolean(6);
            ResultSet contractsRS = getWithInt("TransportsOrders", "TransportID", transportID,conn);
            while (contractsRS.next()) {
                int OrderID = contractsRS.getInt(1);
                Orders.add(OrderDAO.get(OrderID));
            }
            output = new TransportDTO(date, weight, driverID, Truckplate, Orders, wasDel, transportID);
        } catch (Exception e) {
            output = null;
        }
        finally{
            Repository.getInstance().closeConnection(conn);
        }
        return output;
    }

    public int delete(TransportDTO ob) {
        return 0;
    }
    
}
