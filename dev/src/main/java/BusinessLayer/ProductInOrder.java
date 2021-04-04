package BusinessLayer;

import java.util.Map;

public class ProductInOrder{
    private int quantity;
    private double totalPrice;
    private int catalogueIDBySupplier;
    private Product product;



    public ProductInOrder(int quantity, Contract productContract){
        setQuantity(quantity);
        setCatalogueIDBySupplier(productContract.getCatalogueIDBySupplier());
        setProduct(productContract.getProduct());
        calculatePrice(productContract.getPricePerUnit(),productContract.getDiscountByQuantity());
    }

    /**
     * Calculates the price after discount, with the highest discount possible
     * @param pricePerUnit - original price per unit of an item
     * @param discountsByQuantity - a map of discounts - [quantity:discount]
     */
    private void calculatePrice(double pricePerUnit, Map<Integer,Integer> discountsByQuantity) {
        if (discountsByQuantity == null)
            totalPrice = pricePerUnit * quantity;
        else {
            int discount = -1;
            for (int quantity : discountsByQuantity.keySet()) {
                if (this.quantity > quantity)
                    discount = Math.max(discountsByQuantity.get(quantity), discount);
            }
            if (discount == -1)
                totalPrice = pricePerUnit * quantity;
            else{
                double discountSum=1-(discount*0.01);
                totalPrice = discountSum*pricePerUnit*quantity;
            }
        }
    }

    /**
     * Simple getters
     */
    public Product getProduct() {
        return product;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    /**
     * Orders more of the product
     * @param quantity how much more quantity
     * @param pricePerUnit original price per unit
     * @param discounts the discounts given in the contract of the supplier, a map of: [quantity:discount percentage]
     */
    public void orderMore(int quantity,double pricePerUnit,Map<Integer,Integer> discounts){
        if(quantity<0){
            throw new IllegalArgumentException("when ordering more units of a product,the amount of added units must be positive.");
        }
        this.quantity+=quantity;
        calculatePrice(pricePerUnit,discounts);
    }

    //these functions are used to check the validity of the constructor arguments
    private void setQuantity(int quantity){
        if(quantity<1){
            throw new IllegalArgumentException("cannot order 0 units of a product in an order.");
        }
        this.quantity=quantity;
    }

    private void setCatalogueIDBySupplier(int catalogueIDBySupplier){
        if(catalogueIDBySupplier<0){
            throw new IllegalArgumentException("the id of a product cannot be negative.");
        }
        this.catalogueIDBySupplier=catalogueIDBySupplier;
    }

    private void setProduct(Product product){
        if(product==null){
            throw new IllegalArgumentException("a product in order must be related to some product. product field cannot be Null.");
        }
        this.product=product;
    }
}