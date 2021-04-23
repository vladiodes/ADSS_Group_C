package BusinessLayer.SuppliersModule.Controllers;

import BusinessLayer.SuppliersModule.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * this class manages the products in the store inventory.it saves a map of the store products with the key being
 * their id's. it also saves a data member currProductID for issuing id's to new products.
 */


public class Inventory{
    private Map<Integer, Product> productMap;
    private int currProductID;

    public Inventory(){
        productMap=new HashMap<>();
        currProductID=0;
    }


    /**
     *    this functions receives a product name and tries to add a product with that name to the inventory.
     *    if a product with such a name already exist in the inventory the operation fails and an exception is thrown.
     *    otherwise it's added to the product map.
     * @param productName
     * @return
     */
    public Integer addItemToStore(String productName){
        if(alreadyExistsInInventory(productName)){
            throw new IllegalArgumentException("there is already some product with that name in the inventory.");
        }
        productMap.put(currProductID,new Product(currProductID,productName));
        currProductID++;
        return currProductID-1;
    }

    //this function searches for a product in the inventory by it's name.
    //returns true if a product with the given name exist and false otherwise.
    private boolean alreadyExistsInInventory(String productName) {
        List<Product> collection=(productMap.values().stream().filter(product -> product.getName().equals(productName))).collect(Collectors.toList());
        return collection.size()!=0;
    }

    /**
     *     this function receives the product id of some product in the store and returns the
     *     product object associated with that id. if there's no such product an exception is thrown.
     * @param storeProductID
     * @return
     */
    public Product getProductByID(int storeProductID) {
        Product product=productMap.get(storeProductID);
        if(product==null){
            throw new IllegalArgumentException("no product with such id in the store.");
        }
        return product;
    }
}