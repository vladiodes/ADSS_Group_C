package BusinessLayer.DTO;

public class Supplier {
    public String name;
    public boolean selfPickUp;
    public String bankAccount;
    public BusinessLayer.Supplier.PaymentAgreement paymentMethod;
    public int id;

    public Supplier(BusinessLayer.Supplier supplier){
        name=supplier.getName();
        selfPickUp=supplier.getSelfPickUp();
        bankAccount=supplier.getBankAccount();
        paymentMethod=supplier.getPaymentMethod();
        id=supplier.getID();
    }
}
