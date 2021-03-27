
package BusinessLayer.Facade;
import java.util.*;

public interface ISuppliersFacade {
    /**
     * Adds a new supplier to the system
     * @param supplierId - the id of the supplier
     * @param supplierName - the name of the supplier
     * @param supplyingDays - the fixed supplying days of the supplier
     *                      0 - Sunday ,.... , 6 - Friday, null if none
     * @param selfPickup - is it a self pick up supplier?
     * @param bankAccount - A string representing the bank account of the supplier
     * @param paymentMethod - Payment agreement
     *                      0 - Monthly
     *                      1 - Per order
     * @param categories - List of categories
     * @param manufactures - The manufactures that the supplier works with
     * @param contactInfo - A dictionary of [Name:Phone number]
     * @param discounts - The quantity agreement by price (for a total price of an order)
     *                  A dictionary of the type: [Price:Discount]
     * @return A response message, if true - than success.
     */
    Response<Boolean> addSupplier(int supplierId, String supplierName, List<Integer>supplyingDays, boolean selfPickup, String bankAccount, int paymentMethod, List<String> categories, List<String> manufactures, Map<String,String>contactInfo, Map<Double,Integer>discounts);

    /**
     * Deletes a supplier from the system
     * @param supplierId - the id of the supplier to delete.
     * @return - returns true upon successful delete, false otherwise.
     */
    Response<Boolean> deleteSupplier(int supplierId);

    /**
     * Gets a supplier
     * @param supplierId - the id of the supplier
     * @return a response message wrapped with a string that represents the supplier info
     *          in case of an error it would contain an appropriate message.
     */
    Response<String> getSupplier(int supplierId);

    /**
     * gets all suppliers in the system
     * @return returns a list of all suppliers in a short description: "Supplier's name - Supplier's id"
     */
    Response<List<String>> getAllSuppliers();

    /**
     * Updates a supplier's shipping status
     * @param supplierID - the supplier id
     * @param selfPickUp - the new status
     * @return true upon success
     */
    Response<Boolean> updateSuppliersShippingStatus(int supplierID, boolean selfPickUp);

    /**
     * updates the fixed days of the supplier
     * @param supplierID - supplier's id
     * @param newFixedDays - the new fixed days
     * @return true upon success
     */
    Response<Boolean> updateSuppliersFixedDays(int supplierID, List<Integer> newFixedDays);

    /**
     * Adds a discount per total price of an order for a specific supplier in the system
     * @param supplierId - the id of the supplier
     * @param price - the price when the order exceeds it, gets a discount
     * @param discountPerecentage - the percentage of the discount
     * @return true upon successful addition
     */
    Response<Boolean> addDiscount(int supplierId, double price, int discountPerecentage);

    /**
     * Creates an empty order
     * @param supplierId - the supplier's id
     * @param date - the date issued
     * @param isFixed - is the order fixed (we can reorder anytime)
     * @return a response message containing the id of the opened order
     */
    Response<Integer> openOrder(int supplierId, Date date, boolean isFixed);

    /**
     * Allows to re-order a fixed order
     * @param supplierID - the supplierID
     * @param orderID - the reference to the original order to re-order from
     * @param date - the date issued with the new order
     * @return returns a response message with the id of the new issued order
     */
    Response<Integer> reOrder(int supplierID,int orderID,Date date);

    /**
     * Adds an item to an existing order
     * @param supplierId - the supplier id
     * @param orderId - the order id
     * @param quantity - the quantity of the product
     * @param supplierProductId - the id of the product as specified in the catalogue of the supplier
     * @return true upon successful addition, a response containing the message of the exception in case of an error
     */
    Response<Boolean> addItemToOrder(int supplierId,int orderId,int quantity, int supplierProductId);

    /**
     *
     * @param supplierID - the supplier id
     * @param orderID - the order id
     * @return returns a list of strings representing the order info, the first string represents the order
     *          details, the rest are the products that the order contains.
     */
    Response<List<String>> getOrder(int supplierID,int orderID);

    /**
     * Changes the status of an order to "received"
     * @param supplierID - the id of the supplier
     * @param orderID - the id of the order
     * @return returns true upon success
     */
    Response<Boolean> receiveOrder(int supplierID,int orderID);

    /**
     * get all orders by a specific supplier
     * @param supplierId - the supplier id
     * @return returns a list of all the orders by a specific supplier
     */
    Response<List<Integer>> getOrderIdsBySupplier(int supplierId);

    /**
     * get all products supplied by a specific supplier in the system
     * @param supplierID - the supplier id
     * @return returns a list of all the items supplied by the supplier
     */
    Response<List<String>> getItemsBySupplier(int supplierID);

    /**
     * Add an item to the company
     * @param productName - the name of the new product
     * @return returns the id of the product id in the store
     */
    Response<Integer> addItemToStore(String productName);

    /**
     * Adds a new item to a suppliers contract
     * @param supplierID - supplier's id
     * @param StoreProductID - the id of the product as it's identified by the store
     * @param supplierProductID - the id of the product as it's identified by the supplier
     * @param price - the price the supplier sells the product
     * @param quantityAgreement - the quantity agreement
     * @return returns true upon successful addition of the product
     */
    Response<Boolean> addItemToSupplier(int supplierID, int StoreProductID, int supplierProductID, double price, Map<Integer,Integer> quantityAgreement);

    /**
     * Deletes a product from a supplier's contract
     * @param supplierID - the supplier id
     * @param supplierProductID - the id of the product as it's identified in the supplier's catalogue
     * @return returns true upon successful deletion
     */
    Response<Boolean> deleteItemFromSupplier(int supplierID,int supplierProductID);

    /**
     * Deletes a discount given by a supplier
     * @param supplierID - the id of the supplier
     * @param price - the price of the discount
     * @return returns true upon success
     */
    Response<Boolean> deleteSupplierDiscount(int supplierID, double price);

    /**
     * Deletes a product discount
     * @param supplierID - supplier ID
     * @param productID - product ID
     * @param quantity - quantity of the product from which the discount starts
     * @return true upon success
     */
    Response<Boolean> deleteProductDiscount(int supplierID, int productID, int quantity);

    /**
     * Cancelcs an order (actually deletes it)
     * @param supplierID - supplier id
     * @param orderID - order id
     * @return returns true upon success
     */
    Response<Boolean> cancelOrder(int supplierID,int orderID);

    /**
     * Deletes a product from an order
     * @param supplierID - supplier id
     * @param orderID - order id
     * @param productID - product's id
     * @return returns true upon success
     */
    Response<Boolean> deleteProductFromOrder(int supplierID,int orderID,int productID);
}
