package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Item;
import DTO.ItemDTO;
import DataAccessLayer.ItemDAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsMapper {
    private static ItemsMapper instance=null;
    private HashMap<Integer, Item> itemsMapper;
    private ItemDAO dao;

    private ItemsMapper(){
        itemsMapper=new HashMap<>();
        dao=new ItemDAO();
    }

    public static ItemsMapper getInstance(){
        if(instance==null)
            instance=new ItemsMapper();
        return instance;
    }

    public Item buildItem(ItemDTO dto){
        Item item=new Item(dto.getID(),dto.getName(),dto.getLocation(),dto.getProducer(),dto.getStorageAmount(),dto.getShelfAmount(),
                dto.getMinAmount(),dto.getExpDate(),dto.getSellingPrice(),dto.getCategoryID());
        itemsMapper.put(item.getId(),item);
        return item;
    }

    public int addItem(Item item){
        int id=dao.insert(new ItemDTO(item));
        itemsMapper.put(id,item);
        return id;
    }

    public void updateItem(Item item){
        dao.update(new ItemDTO(item));
    }

    /**
     * returns all items instances as appears in the ids list
     * @param itemIDS
     * @return
     */
    public List<Item> getItems(List<Integer> itemIDS) {
        ArrayList<Item> items=new ArrayList<>();
        for(int id:itemIDS)
            items.add(getItem(id));
        return items;
    }

    public Item getItem(int id) {
        if(itemsMapper.containsKey(id))
            return itemsMapper.get(id);
        ItemDTO dto=dao.get(id);
        if(dto==null)
            throw new IllegalArgumentException("No such item in the database");
        return buildItem(dto);
    }
}
