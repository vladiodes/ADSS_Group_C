package BusinessLayer.Mappers;

import BusinessLayer.SuppliersModule.Contract;
import BusinessLayer.SuppliersModule.Order;
import BusinessLayer.SuppliersModule.ProductInOrder;
import DTO.OrderDTO;
import DataAccessLayer.OrderDAO;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

public class OrderMapper {
    private static OrderMapper instance=null;
    private HashMap<Integer, Order> orderMapper;//id to order mapping
    private OrderDAO dao;

    private OrderMapper(){
        orderMapper=new HashMap<>();
        dao=new OrderDAO();
    }

    public static OrderMapper getInstance(){
        if(instance==null){
            instance=new OrderMapper();
        }
        return instance;
    }

    public Order buildOrder(OrderDTO dto){
        if(dto==null){
            return null;
        }
        HashMap<Integer, Pair<Integer,Integer>> productsInOrderIDs=dto.productIds;
        Set<ProductInOrder> productsInOrder=new LinkedHashSet<>();
        for (Integer storeID:
             productsInOrderIDs.keySet()) {
            int itemID=storeID;
            int supplierID=dto.supplierID;
            int catalogID=productsInOrderIDs.get(storeID).getKey();
            int quantity=productsInOrderIDs.get(storeID).getValue();
            Contract contract=ContractMapper.getInstance().getContract(supplierID,catalogID,itemID);
            productsInOrder.add(new ProductInOrder(quantity,contract));
        }
        Order order=new Order(dto.dateOfOrder,dto.orderID,dto.shipmentStatus,dto.priceBeforeDiscount,dto.priceAfterDiscount,dto.totalQuantity,productsInOrder,dto.isFixed);
        return order;
    }

    public Order getOrder(int id){
        if(id<=0){
            return null;
        }
        if(orderMapper.containsKey(id)){
            return orderMapper.get(id);
        }
        OrderDTO order=dao.get(id);
        return buildOrder(order);
    }

    public int addOrder(Order order,int supplierID){
        int id=dao.insert(new OrderDTO(order,supplierID));
        orderMapper.put(order.getOrderID(),order);
        return id;
    }
}
