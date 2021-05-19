package BusinessLayer.Mappers;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.InventoryModule.SpecificItem;
import BusinessLayer.SuppliersModule.Contract;
import DTO.ItemDTO;
import DTO.specificItemDTO;
import DataAccessLayer.DAO;
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

    public Item buildItem(ItemDTO dto) {
        Item item = new Item(dto);
        itemsMapper.put(item.getId(), item);
        for (Contract c : ContractMapper.getInstance().getItemContracts(dto.getID()))
            item.addContract(c);
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
        if (itemsMapper.containsKey(id))
            return itemsMapper.get(id);
        ItemDTO dto = dao.get(DAO.idCol, id);
        if (dto == null)
            return null;
        return buildItem(dto);
    }

    public Item getItemByLocation(int location) {
        for (Item i : itemsMapper.values()) {
            if (i.getLocation() == location)
                return i;
        }
        ItemDTO dto = dao.get(dao.LocationCol, location);
        if (dto == null)
            return null;
        return buildItem(dto);
    }

    public boolean deleteItem(Item item){
        itemsMapper.remove(item.getId());
        return dao.delete(new ItemDTO(item))>0;
    }

    public int addSpecificItem(Item item, SpecificItem newItem) {
        return dao.insertSpecificItem(new specificItemDTO(newItem,item.getId()));
    }

    public void deleteSpecificItem(Item item,SpecificItem sItem) {
        dao.deleteSpecific(new specificItemDTO(sItem,item.getId()));
    }

    public void updateSpecificItem(SpecificItem specificItem) {
        dao.updateSpecificItem(new specificItemDTO(specificItem,specificItem.getGeneralItemID()));
    }
}
