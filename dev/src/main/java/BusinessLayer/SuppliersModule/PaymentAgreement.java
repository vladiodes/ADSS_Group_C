package BusinessLayer.SuppliersModule;

import java.util.HashMap;

public enum PaymentAgreement{
    Monthly(1),PerOrder(2);

    private int value;
    private static HashMap<Integer,PaymentAgreement> map = createMapping();

    private PaymentAgreement(int value) {
        this.value = value;
    }

    private static HashMap<Integer,PaymentAgreement> createMapping() {
        HashMap<Integer,PaymentAgreement> output=new HashMap();
        for (PaymentAgreement agreement : PaymentAgreement.values()) {
            output.put(agreement.value, agreement);
        }
        return output;
    }

    public static PaymentAgreement valueOf(int agreement) {
        return map.get(agreement);
    }
}
