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

    public void orderMore(Contract contract,int quantity){
        this.quantity+=quantity;
        this.totalPrice+=quantity*contract.getPricePerUnit();
    }

    public String toString(){
        return "name: "+product.getName()+'\n'+
                "total quantity: "+quantity+'\n'+
                "catalogue ID by supplier: "+catalogueIDBySupplier+'\n'+
                "total price: "+totalPrice;
    }
}