package BusinessLayer;

import java.util.Map;

public class Contract{
    private double pricePerUnit;
    private int catalogueIDBySupplier;
    private Map<Integer,Integer> discountByQuantity;
    private Supplier supplier;
    private Product product;

    public Contract(double pricePerUnit,int catalogueIDBySupplier, Map<Integer,Integer> discountByQuantity,Supplier supplier,Product product){
        this.pricePerUnit=pricePerUnit;
        this.catalogueIDBySupplier=catalogueIDBySupplier;
        this.discountByQuantity=discountByQuantity;
        this.supplier=supplier;
        this.product=product;
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

    //this function receives a quantity which is a minimum buying amount in order to receive some discount according to the
    //contract. it searches for the map entry which corresponds to the discount mentioned. if it finds it then this discount
    //(entry) is deleted from the map. otherwise an exception is thrown.
    public void deleteDiscount(int quantity) {
        for (Integer minQuantityForDiscount:
             discountByQuantity.keySet()) {
            if(minQuantityForDiscount==quantity)
                discountByQuantity.remove(minQuantityForDiscount);
        }
        throw new IllegalArgumentException("there's no discount starting from the given quantity.");
    }
}