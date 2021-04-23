package BusinessLayer.SuppliersModule;

import java.util.HashMap;
import java.util.Map;

public class Contract{
    private double pricePerUnit;
    private int catalogueIDBySupplier;
    private Map<Integer,Integer> discountByQuantity;
    private Product product;

    public Contract(double pricePerUnit,int catalogueIDBySupplier, Map<Integer,Integer> discountByQuantity,Product product){
        setPricePerUnit(pricePerUnit);
        setCatalogueIDBySupplier(catalogueIDBySupplier);
        setDiscountByQuantity(discountByQuantity);
        setProduct(product);
    }

    //simple getters
    public Map<Integer,Integer> getDiscountByQuantity(){
        return discountByQuantity;
    }

    public int getCatalogueIDBySupplier(){
        return catalogueIDBySupplier;
    }

    public Product getProduct() {
        return product;
    }

    public double getPricePerUnit(){
        return pricePerUnit;
    }


    /**
     *     this function receives a quantity which is a minimum buying amount in order to receive some discount according to the
     *     contract. it searches for the map entry which corresponds to the discount mentioned. if it finds it then this discount
     *     (entry) is deleted from the map. otherwise an exception is thrown.
     * @param quantity
     */
    public void deleteDiscount(int quantity) {
        for (Integer minQuantityForDiscount:
             discountByQuantity.keySet()) {
            if(minQuantityForDiscount==quantity) {
                discountByQuantity.remove(minQuantityForDiscount);
                return;
            }
        }
        throw new IllegalArgumentException("there's no discount starting from the given quantity.");
    }

    //these functions are private setters used to check the validity of the constructor arguments
    private void setPricePerUnit(double pricePerUnit){
        if(pricePerUnit<0){
            throw new IllegalArgumentException("a price of a product cannot be negative.");
        }
        this.pricePerUnit=pricePerUnit;
    }

    private void setCatalogueIDBySupplier(int catalogueIDBySupplier){
        if(catalogueIDBySupplier<0){
            throw new IllegalArgumentException("the id of a product cannot be negative.");
        }
        this.catalogueIDBySupplier=catalogueIDBySupplier;
    }

    private void setDiscountByQuantity(Map<Integer,Integer> discountByQuantity){
        if(discountByQuantity==null)
            this.discountByQuantity=new HashMap<>();
        else {
            for (Integer quantity :
                    discountByQuantity.keySet()) {
                if (quantity < 0 | discountByQuantity.get(quantity) < 0) {
                    throw new IllegalArgumentException("discount by quantity can only have a positive starting quantity and a positive discount percentage.");
                }
            }
            this.discountByQuantity = discountByQuantity;
        }
    }

    private void setProduct(Product product){
        if(product==null){
            throw new IllegalArgumentException("a contract must be related to some product. product field cannot be Null.");
        }
        this.product=product;
    }

    /**
     * Adds a new discount to the contract
     * throws an exception if added a negative discount or adding a discount that already exists
     * @param quantity the quantity that the discounts starts from
     * @param discount the discount in percentage
     */
    public void addDiscount(int quantity, int discount) {
        if(quantity<0 || discount<0)
            throw new IllegalArgumentException("Can't add a negative discount or a negative quantity");
        if(discount>100)
            throw new IllegalArgumentException("Can't add discount above 100%");
        if(discountByQuantity.containsKey(quantity))
            throw new IllegalArgumentException("A discount of this quantity already exists!");
        discountByQuantity.put(quantity,discount);
    }
}