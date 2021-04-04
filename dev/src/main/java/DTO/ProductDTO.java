package DTO;

import BusinessLayer.Product;

public class ProductDTO {
    public int storeID;
    public String productName;

    public ProductDTO(Product p) {
        storeID=p.getID();
        productName=p.getName();
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Product name: " + productName);
        builder.append("\nProduct id in store: " + storeID);
        return builder.toString();
    }
}
