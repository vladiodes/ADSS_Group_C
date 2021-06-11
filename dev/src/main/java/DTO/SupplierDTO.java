package DTO;
import BusinessLayer.SuppliersModule.DayOfWeek;
import BusinessLayer.SuppliersModule.PaymentAgreement;
import BusinessLayer.SuppliersModule.Supplier;

import java.util.*;

public class SupplierDTO {
    public String supplierName;
    public Set<DayOfWeek> fixedDays;
    public boolean selfPickUp;
    private int SupplierID;
    public String bankAccount;
    public PaymentAgreement paymentMethod;
    public Set<String> categories;
    public Set<String> manufacturers;
    public Map<String,String> contactInfo;
    public Map<Double,Integer> discountsByPrice;
    public List<Integer> orderIDs;//for loading from database
    public Map<Integer,Integer> contracts;
    public String siteDestination;
    public SupplierDTO(Supplier supplier) {
        supplierName=supplier.getSupplierName();
        fixedDays=supplier.getFixedDays();
        selfPickUp= supplier.isSelfPickUp();
        SupplierID= supplier.getSupplierID();
        bankAccount=supplier.getBankAccount();
        paymentMethod=supplier.getPaymentMethod();
        categories=supplier.getCategories();
        manufacturers=supplier.getManufacturers();
        contactInfo=supplier.getContactInfo();
        discountsByPrice=supplier.getDiscountsByPrice();
        siteDestination=supplier.getSiteDestination();
    }

    public SupplierDTO(String name, Set<DayOfWeek> fixedDays, Boolean selfpickup, int id, String bankAccount, String paymentMethod, Set<String> categories, Set<String> manufacturures, Map<String, String> contactInfo, Map<Double, Integer> discountsByPrice, List<Integer> orderIDs,Map<Integer,Integer> contracts,String siteDestination) {
        this.supplierName=name;
        this.fixedDays=fixedDays;
        this.selfPickUp=selfpickup;
        this.SupplierID=id;
        this.bankAccount=bankAccount;
        if(paymentMethod=="PerOrder"){
            this.paymentMethod=PaymentAgreement.PerOrder;
        }
        else {
            this.paymentMethod=PaymentAgreement.Monthly;
        }
        this.categories=categories;
        this.contactInfo=contactInfo;
        this.manufacturers=manufacturures;
        this.discountsByPrice=discountsByPrice;
        this.orderIDs=orderIDs;
        this.contracts = contracts;
        this.siteDestination=siteDestination;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Supplier's ID: " + getSupplierID());
        builder.append("\nSupplier's name: " + supplierName);
        builder.append("\nSupplier's site destination: " + siteDestination);
        builder.append("\nSupplier's fixed days: ");
        for(DayOfWeek day:fixedDays)
            builder.append(day.toString() + " ");
        builder.append("\nSelf pick up: " + selfPickUp);
        builder.append("\nContact info: ");
        for(String contact: contactInfo.keySet())
            builder.append(contact + " - " + contactInfo.get(contact) + "\n");
        return builder.toString();
    }
}
