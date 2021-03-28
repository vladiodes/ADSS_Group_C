package BusinessLayer;

import com.sun.deploy.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Supplier{
    private String supplierName;
    private List<Integer> fixedDays;
    private boolean selfPickUp;
    private int SupplierID;
    private String bankAccountNumber;
    private int paymentMethod;
    private List<String> categories;
    private List<String> manufacturers;
    private Map<String,String> contactInfo;
    private Map<Double,Integer> discountsByPrice;
    private Set<Product> suppliedProducts;
    private Set<Order> ordersFromSupplier;
    private List<Contract> supplierContracts;

    public Supplier(int SupplierID,String supplierName, List<Integer>supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, List<String> categories, List<String> manufactures, Map<String,String>contactInfo, Map<Double,Integer>discounts){
        this.SupplierID=SupplierID;
        this.supplierName=supplierName;
        this.fixedDays=supplyingDays;
        this.selfPickUp=selfPickup;
        this.bankAccountNumber=bankAccount;
        this.paymentMethod=paymentMethod;
        this.categories=categories;
        this.manufacturers=manufactures;
        this.contactInfo=contactInfo;
        this.discountsByPrice=discounts;
        //TODO:need to check legality of parameters.a function for each data member.add comments
    }

    public String toString(){
        return "supplier name: "+supplierName+'\n'+
                "fixed days: "+fixedDaysToString()+'\n'+
                "self pick up: "+selfPickUp+'\n'+
                "id: "+SupplierID+'\n'+
                "bank account number: "+bankAccountNumber+'\n'+
                "categories: "+ categoriesToString()+'\n'+
                "manufacturers: "+manufacturersToString()+'\n'+
                "contact info: "+'\n'+
                contactInfoToString()+'\n'+
                "discounts: "+'\n'+
                discountToString();
                //need to return more?
    }
    //maybe extract those functions to a supplier util class.
    private String fixedDaysToString(){
        if(fixedDays==null){
            return "none";
        }
        return StringUtils.join(fixedDays,",");
    }

    private String categoriesToString(){
        return StringUtils.join(categories,",");
    }

    private String manufacturersToString(){
        return StringUtils.join(manufacturers,",");
    }

    private String contactInfoToString(){
        return convertWithStream(contactInfo);
    }

    private String discountToString(){
        return convertWithStream(discountsByPrice);
    }

    private String convertWithStream(Map<?, ?> map) {
        String mapAsString = map.keySet().stream()
                .map(key -> key + "=" + map.get(key))
                .collect(Collectors.joining(", "+'\n', "{", "}"));
        return mapAsString;
    }
}