package BusinessLayer.SuppliersModule;

public class Product{
    private int productID;
    private String productName;

    public Product(int productID,String productName){
        setProductID(productID);
        setProductName(productName);
    }

    /**
     * Checks if 2 products are equal, checks if both ids and names are the same
     * @param product
     * @return
     */
    public boolean equals(Product product) {
        return product!=null && productName.equals(product.productName) & productID==product.productID;
    }
    //simple getters
    public String getName() {
        return productName;
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