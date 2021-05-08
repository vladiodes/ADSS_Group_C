package BusinessLayer.SuppliersModule;

import BusinessLayer.InventoryModule.Item;
import BusinessLayer.Mappers.ContractMapper;
import BusinessLayer.Mappers.OrderMapper;
import BusinessLayer.Mappers.SupplierMapper;
import BusinessLayer.SuppliersModule.Controllers.SuppliersController;
import DTO.SupplierDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Supplier{
    private String supplierName;
    private Set<DayOfWeek> fixedDays;
    private boolean selfPickUp;
    private int SupplierID=-1;
    private String bankAccount;
    private PaymentAgreement paymentMethod;
    private Set<String> categories;
    private Set<String> manufacturers;
    private Map<String,String> contactInfo;
    private Map<Double,Integer> discountsByPrice;
    private List<Order> ordersFromSupplier;
    private List<Contract> supplierContracts;

    public List<Contract> getSupplierContracts() {
        return supplierContracts;
    }


    public Supplier(SupplierDTO dto){
        supplierName=dto.supplierName;
        fixedDays=dto.fixedDays;
        selfPickUp=dto.selfPickUp;
        SupplierID=dto.getSupplierID();
        bankAccount=dto.bankAccount;
        paymentMethod=dto.paymentMethod;
        categories=dto.categories;
        manufacturers=dto.manufacturers;
        contactInfo= dto.contactInfo;
        discountsByPrice=dto.discountsByPrice;
        ordersFromSupplier=new LinkedList<>();
        supplierContracts=new LinkedList<>();
    }
    public Supplier(String supplierName, Set<DayOfWeek>supplyingDays, boolean selfPickup, String bankAccount, PaymentAgreement paymentMethod, Set<String> categories, Set<String> manufactures, Map<String,String>contactInfo, Map<Double,Integer>discounts) {
        setSupplierName(supplierName);
        setFixedDays(supplyingDays);
        setSelfPickUp(selfPickup);
        setBankAccount(bankAccount);
        setPaymentMethod(paymentMethod);
        setCategories(categories);
        setManufacturers(manufactures);
        setContactInfo(contactInfo);
        setDiscountsByPrice(discounts);
        this.ordersFromSupplier = new LinkedList<>();
        this.supplierContracts = new LinkedList<>();
    }

    private void setDiscountsByPrice(Map<Double, Integer> discounts) {
        if(discounts==null)
            discountsByPrice=new HashMap<>();
        else {
            for(double price:discounts.keySet()){
                if(price<0)
                    throw new IllegalArgumentException("Can't enter negative price as discount");
                if(discounts.get(price)<0)
                    throw new IllegalArgumentException("Can't enter negative discount");
            }
            discountsByPrice=discounts;
        }
    }

    /**
     * Updates the supplier fields according to a dto object
     * @param dto - dto object
     */
    public void updateSupplier(SupplierDTO dto){
        setSupplierName(dto.supplierName);
        setBankAccount(dto.bankAccount);
        setCategories(dto.categories);
        setContactInfo(dto.contactInfo);
        setFixedDays(dto.fixedDays);
        setManufacturers(dto.manufacturers);
        setPaymentMethod(dto.paymentMethod);
        setSelfPickUp(dto.selfPickUp);
        SupplierMapper.getInstance().update(this);
    }

    //the setters are used for checking the validity of the constructor arguments and implementing the Facade interface.
    public void setSupplierID(int supplierID) {
        if (this.SupplierID == -1)
            this.SupplierID = supplierID;
    }

    private void setSupplierName(String supplierName){
        if(supplierName==null || supplierName.length()==0){
            throw new IllegalArgumentException("a supplier must have a non empty name.");
        }
        this.supplierName=supplierName;
    }

    private void setBankAccount(String bankAccount){
        if(bankAccount==null || bankAccount.length()==0){
            throw new IllegalArgumentException("the bank account details of a supplier must be non empty.");
        }
        this.bankAccount=bankAccount;
    }

    //a setter for the fixedDays field. we check that there are no more than 6 elements in the set as we can only receive
    //orders from sunday to friday and we check that all elements are in the range of 1 to 6 so they will match to
    //week days.
    private void setFixedDays(Set<DayOfWeek> newFixedDays){
        if(newFixedDays==null || newFixedDays.size()==0) {
            fixedDays = new HashSet<>();
            fixedDays.add(DayOfWeek.None);
            return;
        }
        fixedDays=newFixedDays;
    }

    private void setPaymentMethod(PaymentAgreement method){
        paymentMethod=method;
    }

    private void setSelfPickUp(boolean selfPickUp) { this.selfPickUp = selfPickUp; }

    private void setCategories(Set<String> categories){
        if(!checkNonEmptyString(categories)){
            throw new IllegalArgumentException("each category of a supplier must be non empty");
        }
            this.categories=categories;
    }

    private void setManufacturers(Set<String> manufacturers){
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

    private void setContactInfo(Map<String,String> contactInfo){
        for (String key:
             contactInfo.keySet()) {
            if((key==null||key.length()==0)||(contactInfo.get(key)==null||contactInfo.get(key).length()==0)){
                throw new IllegalArgumentException("the contact info of a supplier cannot be empty.");
            }
        }
        this.contactInfo=contactInfo;
    }

    /**
     * Adds a new discount to the supplier. If the discount already exists - throws an exception
     * @param price the price
     * @param discountPerecentage the discount in percentage
     */
    public void addDiscount(double price, int discountPerecentage){
        if(price<0 | discountPerecentage<0){
            throw new IllegalArgumentException("tried to add an illegal discount to a supplier. the discount starting price" +
                    "and the discount percentage have to be non negative.");
        }
        if(discountPerecentage>100)
            throw new IllegalArgumentException("Can't add a discount above 100%");
        if(discountsByPrice.containsKey(price))
            throw new IllegalArgumentException("A discount for that price already exists, you should delete it first, and then add a new one");
        discountsByPrice.put(price,discountPerecentage);
        calculateOrdersPrices();
    }

    /**
     *  this method is invoked once a new discount has been added or removed,
     *  calculates the new updated price for each order
     */
    private void calculateOrdersPrices() {
        for(Order o:ordersFromSupplier) {
            o.calculateDiscount(discountsByPrice);
        }
    }

    /**
     * Adds a new order to the supplier
     * @param date the date issued with the order
     * @param isFixed is the order fixed (can be re-ordered)
     */
    public Order addOrder(LocalDate date, Boolean isFixed){
        Order toAdd=new Order(date,isFixed,SupplierID);
        ordersFromSupplier.add(toAdd);
        return toAdd;
    }

    //when building the supplier object from db
    public void addOrder(Order order){
        if(order != null)
        {
            ordersFromSupplier.add(order);
        }
    }

    /**
     * Reorders a fixed order (makes a deep copy of it)
     * @param originalOrderID the id of the original order
     * @param date the date issued with the new order
     */
    public Order reOrder(int originalOrderID, LocalDate date) {
        Order toReturn = new Order(findOrder(originalOrderID), date,SupplierID);
        ordersFromSupplier.add(toReturn);
        return toReturn;
    }

    /**
     * Adds a new item to a given order
     * @param orderId the id of the order
     * @param quantity the quantity of items to add
     * @param supplierProductId the id of the product as it appears in the supplier's catalogue
     */
    public void addItemToOrder(int orderId, int quantity, int supplierProductId){
        Order order = findOrder(orderId);
        if(order.getisFixed() && order.getDateOfOrder().compareTo(LocalDate.now().plusDays(1)) >= 0)
        {
            order.addItem(findContract(supplierProductId),quantity,discountsByPrice);
        }
        else if(!order.getisFixed())
            order.addItem(findContract(supplierProductId),quantity,discountsByPrice);
        else
            throw new IllegalArgumentException("Need to update order at least one day before arrival");
    }


    /**
     *
     * @param id the id of the requested order
     * @return returns the order if exists, throws an exception otherwise
     */
    private Order findOrder(int id){
        Order order=null;
        for (Order o:
                ordersFromSupplier) {
            if(o.getOrderID()==id){
                order=o;
                break;
            }
        }
        if(order==null){
            throw new IllegalArgumentException("no order with such id.");
        }
        return order;
    }

    /**
     *
     * @param supplierProductId the requested catalogue id
     * @return the contract if exists, or throws an exception if doesn't
     */
    private Contract findContract(int supplierProductId){
        Contract contract=null;
        for (Contract c:
             supplierContracts) {
                if(c.getCatalogueIDBySupplier()==supplierProductId){
                    contract=c;
                    break;
                }
        }
        if(contract==null)
            throw new IllegalArgumentException("No such contract with this supplier");
        return contract;
    }

    /**
     * Gets an order with the requested order id
     * @param orderID the id of the order
     * @return returns the order that was requested
     */
    public Order getOrder(int orderID) {
        return findOrder(orderID);
    }

    /**
     * Receives an order (changes its delivery status)
     * @param orderID the id of the requested order
     */
    public void receiveOrder(int orderID) {
        Order o=findOrder(orderID);
        o.receive();
        OrderMapper.getInstance().update(o,SupplierID);
        if(o.getisFixed()) //if a fixed order, reordering it once again for the next week
            SuppliersController.getInstance().reOrder(SupplierID,o.getOrderID(),LocalDate.now().plusWeeks(1));
    }

    /**
     *
     * @return returns a list of all the orders from the supplier
     */
    public List<Order> getOrders() {
        List<Order> orderIds=new LinkedList<>();
        for (Order order:
             ordersFromSupplier) {
            orderIds.add(order);
        }
        return orderIds;
    }

    /**
     *
     * @return returns a list of all the products (contracts) supplied by the supplier
     */
    public List<Contract> getSuppliedItems() {
        List<Contract> contracts = new LinkedList<>();
        for (Contract c : supplierContracts)
            contracts.add(c);
        return contracts;
    }

    /**
     * Adds a new contract to the supplier - throws an exception if there's already an issued contract with the given catalogue id
     * or there's already a contract issued with the given product
     * @param product the product for which the new contract relates to
     * @param supplierProductID the catalogue id of the product in the supplier's catalogue
     * @param price the price the supplier sells it
     * @param quantityAgreement a quantity agreement that describes the discounts given by the supplier
     */
    public Contract addContract(Item product, int supplierProductID, double price, Map<Integer, Integer> quantityAgreement) {
        for(Contract c:supplierContracts) {
            if (c.getCatalogueIDBySupplier() == supplierProductID)
                throw new IllegalArgumentException("There's already a contract issued with the given catalogue id");
            if(c.getProduct().equals(product))
                throw new IllegalArgumentException("There's already a contract issued with the given product");
        }
        Contract contract=new Contract(price,this.SupplierID,supplierProductID,quantityAgreement,product);
        supplierContracts.add(contract);
        return contract;
    }

    public void addContract(Contract contract){
        supplierContracts.add(contract);
    }

    /**
     * Removes a contract from the supplier - if the product is delivered by an ordered that wasn't delivered yet,
     * throws an exception.
     * @param supplierProductID the catalogue id of the product to remove
     */
    public Contract removeContract(int supplierProductID) {
        for (Contract c: supplierContracts) {
            if(c.getCatalogueIDBySupplier()==supplierProductID){
                for(Order order:ordersFromSupplier){
                    if(order.checkIfProductExists(c.getProduct()))
                        throw new IllegalArgumentException("There's an order waiting for delivery with the given product, can't delete it");
                }
                supplierContracts.remove(c);
                c.getProduct().removeContract(c);
                return c;
            }
        }
        throw new IllegalArgumentException("the supplier has no contract for a product with the given id.");
    }

    /**
     * Cancels an order - actually deletes it,
     * if doesn't exist throws an exception
     * @param orderID the id of the order to cancel
     */
    public void cancelOrder(int orderID) {
        Order order=findOrder(orderID);
        ordersFromSupplier.remove(order);
        OrderMapper.getInstance().remove(order);
    }

    /**
     * removes the discount given by the supplier starting from the given price.
     *  if there is no such discount an exception is thrown.
     * @param price the price the discount starts from
     */
    public void removeDiscount(double price) {
        for (Double minPriceForDiscount: discountsByPrice.keySet()) {
            if(minPriceForDiscount==price){
                discountsByPrice.remove(price);
                calculateOrdersPrices();
                return;
            }
        }
        throw new IllegalArgumentException("no discount starting from the given price.");
    }

    /**
     * Deletes a discount from a given product
     * @param catalogueID the id of the product - as appears in the catalogue
     * @param quantity
     */
    public void deleteProductDiscount(int catalogueID, int quantity) {
        Contract c=findContract(catalogueID);
        c.deleteDiscount(quantity);
        ContractMapper.getInstance().removeDiscount(c,quantity);
        calculateOrdersPrices();
    }

    /**
     * Deletes a product from an order
     * @param orderID the id of the order
     * @param productID the id of the product to delete as appears in the catalogue of the supplier
     */
    public void deleteProductFromOrder(int orderID, int productID) {
        findOrder(orderID).removeProduct(findContract(productID),discountsByPrice);
    }

    /**
     * Adds a discount to a contract
     * @param catalogueID the catalogue id of the product
     * @param quantity the quantity above the discount starts
     * @param discount the percentage of the discount
     */
    public void addDiscount(int catalogueID, int quantity, int discount) {
        findContract(catalogueID).addDiscount(quantity,discount);
        calculateOrdersPrices();
        ContractMapper.getInstance().addDiscount(findContract(catalogueID),quantity,discount);
    }

    //Following are simple getters
    public String getSupplierName() {
        return supplierName;
    }

    public Set<DayOfWeek> getFixedDays() {
        return fixedDays;
    }

    public boolean isSelfPickUp() {
        return selfPickUp;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public PaymentAgreement getPaymentMethod() {
        return paymentMethod;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public Set<String> getManufacturers() {
        return manufacturers;
    }

    public Map<String, String> getContactInfo() {
        return contactInfo;
    }

    public Map<Double, Integer> getDiscountsByPrice() {
        return discountsByPrice;
    }
}