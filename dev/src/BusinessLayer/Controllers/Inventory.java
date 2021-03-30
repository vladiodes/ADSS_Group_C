package BusinessLayer.Controllers;

import BusinessLayer.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Inventory{
    private Map<Integer, Product> productMap;
    private int currProductID;

    public Inventory(){
        productMap=new HashMap<>();
        currProductID=0;
    }

    public Integer addItemToStore(String productName){
        if(alreadyExistsInInventory(productName)){
            throw new IllegalArgumentException("there is allready some product with that name in the inventory.");
        }
        productMap.put(currProductID,new Product(currProductID,productName));
        currProductID++;
        return currProductID-1;
    }

    private boolean alreadyExistsInInventory(String productName) {
        List<Product> collection=(productMap.values().stream().filter(product -> product.getName().equals(productName))).collect(Collectors.toList());
        return collection.size()!=0;
    }

    public Product getProductByID(int storeProductID) {
        Product product=productMap.get(storeProductID);
        if(product==null){
            throw new IllegalArgumentException("no product with such id in the store.");
        }
        return product;
    }
}