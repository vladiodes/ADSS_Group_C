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

    public void deleteDiscount(int quantity) {
        for (Integer minQuantityForDiscount:
             discountByQuantity.keySet()) {
            if(minQuantityForDiscount==quantity)
                discountByQuantity.remove(minQuantityForDiscount);
        }
        throw new IllegalArgumentException("there's no discount starting from the given quantity.");
    }
}