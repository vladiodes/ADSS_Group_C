package BusinessLayer;

import com.sun.deploy.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Supplier{
    private String supplierName;
    private Set<Integer> fixedDays;
    private boolean selfPickUp;
    private int SupplierID;
    private String bankAccount;
    private int paymentMethod;
    private Set<String> categories;
    private Set<String> manufacturers;
    private Map<String,String> contactInfo;
    private Map<Double,Integer> discountsByPrice;
    private List<Order> ordersFromSupplier;
    private List<Contract> supplierContracts;

    public Supplier(int SupplierID,String supplierName, Set<Integer>supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, Set<String> categories, Set<String> manufactures, Map<String,String>contactInfo, Map<Double,Integer>discounts){
        setSupplierID(SupplierID);
        setSupplierName(supplierName);
        setFixedDays(supplyingDays);
        setSelfPickUp(selfPickup);
        setBankAccount(bankAccount);
        setPaymentMethod(paymentMethod);
        setCategories(categories);
        setManufacturers(manufactures);
        setContactInfo(contactInfo);
        this.discountsByPrice=discounts;
        this.ordersFromSupplier=new LinkedList<>();
        this.supplierContracts=new LinkedList<>();
    }

    //returns a string with the supplier details.
    public String toString(){
        return "supplier name: "+supplierName+'\n'+
                "fixed days: "+fixedDaysToString()+'\n'+
                "self pick up: "+selfPickUp+'\n'+
                "id: "+SupplierID+'\n'+
                "bank account: "+bankAccount+'\n'+
                "categories: "+ categoriesToString()+'\n'+
                "manufacturers: "+manufacturersToString()+'\n'+
                "contact info: "+'\n'+
                contactInfoToString()+'\n'+
                "discounts: "+'\n'+
                discountToString();
                //need to return more?
    }

    //the functions fixedDaysToString until convertWithStream are used to obtain a string representation of the supplier's
    //data members.
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

    //the setters are used for checking the validity of the constructor arguments and implementing the Facade interface.
    public void setSupplierID(int supplierID){
        if(supplierID<0){
            throw new IllegalArgumentException("a supplier can't have a negative id.");
        }
        this.SupplierID=supplierID;
    }

    public void setSupplierName(String supplierName){
        if(supplierName==null || supplierName.length()==0){
            throw new IllegalArgumentException("a supplier must have a non empty name.");
        }
        this.supplierName=supplierName;
    }

    public void setBankAccount(String bankAccount){
        if(bankAccount==null || bankAccount.length()==0){
            throw new IllegalArgumentException("the bank account details of a supplier must be non empty.");
        }
        this.bankAccount=bankAccount;
    }

    //a setter for the fixedDays field. we check that there are no more than 6 elements in the set as we can only receive
    //orders from sunday to friday and we check that all elements are in the range of 1 to 6 so they will match to
    //week days.
    public void setFixedDays(Set<Integer> newFixedDays){
        if(newFixedDays!=null){
            if(newFixedDays.size()==0){
                fixedDays=null;
                return;
            }
            if(newFixedDays.size()>6){
                throw new IllegalArgumentException("illegal fixed days of supplying, can supply on at most 6 days.");
            }
            for (Integer day:
                 newFixedDays) {
                if(!(day>=1 & day<=6)){
                    throw new IllegalArgumentException("illegal fixed days of supplying,can only supply between sunday and friday.");
                }
            }
        }
        fixedDays=newFixedDays;
    }

    public void setPaymentMethod(int method){
        if(method!=0 & method!=1){
            throw new IllegalArgumentException("payment method can only be represented by 0 or 1. 0 for monthly and 1 for per order.");
        }
        paymentMethod=method;
    }

    public void setSelfPickUp(Boolean selfPickUp){
        if(selfPickUp==null){
            throw new IllegalArgumentException("the self pick up status has to false or true so it can't have a null value.");
        }
        this.selfPickUp=selfPickUp;
    }

    public void setCategories(Set<String> categories){
        if(!checkNonEmptyString(categories)){
            throw new IllegalArgumentException("each category of a supplier must be non empty");
        }
            this.categories=categories;
    }

    public void setManufacturers(Set<String> manufacturers){
        if(!checkNonEmptyString(manufacturers)){
            throw new IllegalArgumentException("each manufacturer name related to a supplier must be non empty.");
        }
        this.manufacturers=manufacturers;
    }

    private boolean checkNonEmptyString(Set<String> stringSet){
        for (String str:
             stringSet) {
            if(str==null||str.length()==0)
                return false;
        }
        return true;
    }

    public void setContactInfo(Map<String,String> contactInfo){
        for (String key:
             contactInfo.keySet()) {
            if((key==null||key.length()==0)||(contactInfo.get(key)==null||contactInfo.get(key).length()==0)){
                throw new IllegalArgumentException("the contact info of a supplier cannot be empty.");
            }
        }
        this.contactInfo=contactInfo;
    }

    public void addDiscount(double price, int discountPerecentage){
        if(price<0 | discountPerecentage<0){
            throw new IllegalArgumentException("tried to add an illegal discount to a supplier. the discount starting price" +
                    "and the discount percentage have to be non negative.");
        }
        discountsByPrice.putIfAbsent(price,discountPerecentage);
    }

    //add an order to the supplier's orders
    public void addOrder(LocalDateTime date,Boolean isFixed,int ID){
        ordersFromSupplier.add(new Order(date,isFixed,ID));
    }

    /**/
    public void reOrder(int newOrderID,int originalOrderID, LocalDateTime date){
        Order order=findOrder(originalOrderID);
        if(order==null){
            throw new IllegalArgumentException("there is no order with the given ID.");
        }
        if(!order.getisFixed()){
            throw new IllegalArgumentException("the order with the given id isn't fixed so it can't be reordered.");
        }
         ordersFromSupplier.add(new Order(order,newOrderID,date));
    }

    /**/
    public void addItemToOrder(int orderId, int quantity, int supplierProductId){
        Order order=findOrder(orderId);
        if(order==null){
            throw new IllegalArgumentException("there is no order with the given ID.");
        }
        Contract contract=findContract(supplierProductId);
        if(contract==null){
            throw new IllegalArgumentException("the supplier has no contract for the given product id.");
        }
        order.addItem(contract.getProduct(),contract.getPricePerUnit(),contract.getCatalogueIDBySupplier(),quantity);
    }

    //the function receives an id and return the order from the supplier with that id if there is such an order.returns
    //null otherwise.
    public Order findOrder(int id){
        Order order=null;
        for (Order o:
                ordersFromSupplier) {
            if(o.getOrderID()==id){
                order=o;
                break;
            }
        }
        return order;
    }

    //this function receives a product id and if there is a contract for a product with such an id it returns the
    //contract for it. otherwise it returns null.
    private Contract findContract(int supplierProductId){
        Contract contract=null;
        for (Contract c:
             supplierContracts) {
                if(c.getCatalogueIDBySupplier()==supplierProductId){
                    contract=c;
                    break;
                }
        }
        return contract;
    }

    /**/
    public List<String> getOrder(int orderID) {
        Order order=findOrder(orderID);
        if(order==null){
            throw new IllegalArgumentException("no order with such id.");
        }
        return order.getOrderDetails();
    }

    /**/
    public void receiveOrder(int orderID) {
        Order order=findOrder(orderID);
        if(order==null){
            throw new IllegalArgumentException("no order with such id.");
        }
        order.receive();
    }

    public List<Integer> getOrdersIDs() {
        List<Integer> orderIds=new LinkedList<>();
        for (Order o:
             ordersFromSupplier) {
            orderIds.add(o.getOrderID());
        }
        return orderIds;
    }

    //this function returns a list of strings which describes all the supplied items by the supplier.
    // each element in the list describes one item.
    public List<String> getSuppliedItems() {
        List<String> items=new LinkedList<>();
        for (Contract c:
             supplierContracts) {
            items.add(c.getProduct().toString());
        }
        return items;
    }

    //adds a new contract for the supplier
    public void addContract(Product product,int supplierProductID, double price, Map<Integer, Integer> quantityAgreement) {
        supplierContracts.add(new Contract(price,supplierProductID,quantityAgreement,this,product));
    }

    //this function receives some product id (as saved by the supplier) and searches for a contract for that a product
    //with that id. if a contract is found it's removed. otherwise an exception is thrown.
    public void removeContract(int supplierProductID) {
        for (Contract c:
             supplierContracts) {
            if(c.getCatalogueIDBySupplier()==supplierProductID){
                supplierContracts.remove(c);
                return;
            }
        }
        throw new IllegalArgumentException("the supplier has no contract for a product with the given id.");
    }

    /**/
    public void cancelOrder(int orderID) {
        Order order=findOrder(orderID);
        if(order==null){
            throw new IllegalArgumentException("no order with such id.");
        }
        ordersFromSupplier.remove(order);
    }

    //removes the discount given by the supplier starting from the given price.
    //if there is no such discount an exception is thrown.
    public void removeDiscount(double price) {
        for (Double minPriceForDiscount:
             discountsByPrice.keySet()) {
            if(minPriceForDiscount==price){
                discountsByPrice.remove(price);
            }
        }
        throw new IllegalArgumentException("no discount starting from the given price.");
    }

    //this function deletes a discount for a specific product supplied by supplier.
    public void deleteProductDiscount(int productID, int quantity) {
        Contract contract=findContractByStoreID(productID);
        contract.deleteDiscount(quantity);
    }

    //this function receives an id of a product in the store and finds and searches for a contract for it.
    //if no such contract is found an exception is thrown.
    private Contract findContractByStoreID(int ID){
        for (Contract contract:
             supplierContracts) {
            if(contract.getProduct().getID()==ID)
                return contract;
        }
        throw new IllegalArgumentException("the supplier doesn't have a contract for a product with the given id.");
    }

    /**/
    public void deleteProductFromOrder(int orderID, int productID) {
        Order o=findOrder(orderID);
        o.removeProduct(productID);
    }
}