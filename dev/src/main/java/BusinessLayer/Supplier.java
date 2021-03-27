package BusinessLayer;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Supplier{
    private String supplierName;
    private List<Integer> fixedDays;
    private boolean selfPickUp;
    private int SupplierID;
    private String bankAccountNumber;
    private int paymentMethod;
    private List<String> categories;
    private List<String> manufacturures;
    private Map<String,String> contactInfo;
    private Map<Double,Integer> discountsByPrice;
    private Set<Product> suppliedProduct;
    private Set<Order> ordersFromSupplier;
    private List<Contract> supplierContracts;
}