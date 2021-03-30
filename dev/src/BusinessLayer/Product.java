package BusinessLayer;

public class Product{
    private int productID;
    private String productName;

    public Product(int productID,String productName){
        this.productID=productID;
        this.productName=productName;
    }

    public boolean equals(Product product){
        return productName.equals(product.productName) & productID==product.productID;
    }

    public String getName() {
        return productName;
    }

    public String toString(){
        return "product name: "+productName+'\n'+
                "product id: "+productID;
    }

    public int getID() {
        return productID;
    }
}