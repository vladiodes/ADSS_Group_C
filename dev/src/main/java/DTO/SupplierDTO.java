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
    }

    public SupplierDTO(String name, List<Integer> fixedDays, Boolean selfpickup, int id, String bankAccount, String paymentMethod, Set<String> categories, Set<String> manufacturures, Map<String, String> contactInfo, Map<Double, Integer> discountsByPrice, List<Integer> orderIDs,Map<Integer,Integer> contracts) {
        this.supplierName=name;
        this.fixedDays=new LinkedHashSet<>();
        for (Integer day:
             fixedDays) {
            if(day==1){
                this.fixedDays.add(DayOfWeek.Sunday);
            }
            else if(day==2){
                this.fixedDays.add(DayOfWeek.Sunday);
            }
            else if(day==3){
                this.fixedDays.add(DayOfWeek.Sunday);
            }
            else if(day==4){
                this.fixedDays.add(DayOfWeek.Sunday);
            }
            else if(day==5){
                this.fixedDays.add(DayOfWeek.Sunday);
            }
            else if(day==6){
                this.fixedDays.add(DayOfWeek.Sunday);
            }
        }
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
    }

    public int getSupplierID() {
        return SupplierID;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder();
        builder.append("Supplier's ID: " + getSupplierID());
        builder.append("\nSupplier's name: " + supplierName);
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
