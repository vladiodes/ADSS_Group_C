package BusinessLayer;

public class ProductInOrder{
    private int quantity;
    private double totalPrice;
    private int catalogueIDBySupplier;
    private Order order;
    private Product product;


    public ProductInOrder(int quantity, double totalPrice, int catalogueIDBySupplier, Order order, Product product){
        this.quantity=quantity;
        this.totalPrice=totalPrice;
        this.catalogueIDBySupplier=catalogueIDBySupplier;
        this.order=order;
        this.product=product;
    }

    public Product getProduct() {
        return product;
    }

    public void orderMore(int quantity){
        if(quantity<1){
            throw new IllegalArgumentException("when ordering more units of a product,the amount of added units must be positive.");
        }
        this.quantity+=quantity;
        this.totalPrice+=quantity*(totalPrice/this.quantity);
    }

    public String toString(){
        return "name: "+product.getName()+'\n'+
                "total quantity: "+quantity+'\n'+
                "catalogue ID by supplier: "+catalogueIDBySupplier+'\n'+
                "total price: "+totalPrice;
    }

    public int getQuantity(){
        return quantity;
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    //these functions are used to check the validity of the constructor arguments

    private void setQuantity(int quantity){
        if(quantity<1){
            throw new IllegalArgumentException("cannot order 0 units of a product in an order.");
        }
        this.quantity=quantity;
    }

    private void setTotalPrice(int totalPrice){
        if(totalPrice<0){
            throw new IllegalArgumentException("the total price of a product in order cannot be negative.");
        }
        this.totalPrice=totalPrice;
    }

    private void setCatalogueIDBySupplier(int catalogueIDBySupplier){
        if(catalogueIDBySupplier<0){
            throw new IllegalArgumentException("the id of a product cannot be negative.");
        }
        this.catalogueIDBySupplier=catalogueIDBySupplier;
    }

    private void setOrder(Order order){
        if(order==null){
            throw new IllegalArgumentException("a product in order must be related to some order. product field cannot be Null.");
        }
        this.order=order;
    }

    private void setProduct(Product product){
        if(product==null){
            throw new IllegalArgumentException("a product in order must be related to some product. product field cannot be Null.");
        }
        this.product=product;
    }
}