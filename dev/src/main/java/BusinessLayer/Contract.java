package main.java.BusinessLayer;

import java.util.Map;

public class Contract{
    private double pricePerUnit;
    private int catalogueIDBySupplier;
    private Map<Integer,Integer> discountByQuantity;
    private Supplier supplier;
    private Product product;
}