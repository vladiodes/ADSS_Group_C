package BusinessLayer;

public class Product{
    private int productID;
    private String productName;

    public Product(int productID,String productName){
        setProductID(productID);
        setProductName(productName);
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

    //these functions are private setters for checking the validity of the constructor arguments

    private void setProductID(int productID){
        if(productID<0){
            throw new IllegalArgumentException("a product cannot have a negative id.");
        }
        this.productID=productID;
    }

    private void setProductName(String productName){
        if(productName==null || productName.length()==0){
            throw new IllegalArgumentException("a product must have a non empty name.");
        }
        this.productName=productName;
    }
}