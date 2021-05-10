package BusinessLayer.SuppliersModule;


public class ProductInOrder{
    private int quantity;
    private double totalPrice;
    private Contract contract;

    public ProductInOrder(int quantity, Contract productContract){
        setQuantity(quantity);
        setContract(productContract);
        calculatePrice();
    }

    /**
     * Calculates the price after discount, with the highest discount possible
     */
    public void calculatePrice() {
        if (contract.getDiscountByQuantity() == null)
            totalPrice = contract.getPricePerUnit() * quantity;
        else {
            int discount = -1;
            for (int quantity : contract.getDiscountByQuantity().keySet()) {
                if (this.quantity > quantity)
                    discount = Math.max(contract.getDiscountByQuantity().get(quantity), discount);
            }
            if (discount == -1)
                totalPrice = contract.getPricePerUnit() * quantity;
            else{
                double discountSum=1-(discount*0.01);
                totalPrice = discountSum*contract.getPricePerUnit()*quantity;
            }
        }
    }

    /**
     * Simple getters
     */
    public int getQuantity(){
        return quantity;
    }

    public Contract getContract() { return contract; }

    public double getTotalPrice(){
        return totalPrice;
    }

    /**
     * Orders more of the product
     * @param quantity how much more quantity
     */
    public void orderMore(int quantity){
        if(quantity<0){
            throw new IllegalArgumentException("when ordering more units of a product,the amount of added units must be positive.");
        }
        this.quantity+=quantity;
        calculatePrice();
    }

    //these functions are used to check the validity of the constructor arguments
    private void setQuantity(int quantity){
        if(quantity<1){
            throw new IllegalArgumentException("cannot order 0 units of a product in an order.");
        }
        this.quantity=quantity;
    }

    private void setContract(Contract productContract) {
        if(productContract==null)
            throw new IllegalArgumentException("Can't insert null contract");
        contract=productContract;
    }

}