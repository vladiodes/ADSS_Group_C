package Data.DTO;
import Business.Objects.Site;
import java.util.HashMap;

public class ItemContractDTO {
    public int ID;
    public String destination;
    public HashMap<String, Integer> items;
    public Boolean passed;

    public ItemContractDTO(int ID, String destination, HashMap<String, Integer> items, Boolean passed) {
        this.ID = ID;
        this.destination = destination;
        this.items = items;
        this.passed = passed;
    }
}
