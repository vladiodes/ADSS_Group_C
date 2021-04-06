package DTO;
import BusinessLayer.DayOfWeek;
import BusinessLayer.PaymentAgreement;
import BusinessLayer.Supplier;

import java.util.Map;
import java.util.Set;

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
