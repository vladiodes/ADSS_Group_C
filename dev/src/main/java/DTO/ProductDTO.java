package DTO;

import BusinessLayer.SuppliersModule.Contract;

public class ProductDTO {
    public int storeID;
    public int catalogueID;
    public double pricePerUnit;
    public String productName;

    public ProductDTO(Contract c) {
        storeID=c.getProduct().getID();
        productName=c.getProduct().getName();
        pricePerUnit=c.getPricePerUnit();
        catalogueID=c.getCatalogueIDBySupplier();
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Product name: " + productName);
        builder.append("\nProduct id in store: " + storeID);
        builder.append("\nProduct id in supplier's catalogue: " + catalogueID);
        builder.append("\nPrice per unit " + pricePerUnit);
        return builder.toString();
    }
}
