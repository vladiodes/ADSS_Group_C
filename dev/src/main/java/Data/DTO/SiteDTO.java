package Data.DTO;

public class SiteDTO {
    public String address;
    public String contact;
    public String phoneNumber;
    public String Section;

    public SiteDTO(String address, String contact, String phoneNumber, String section) {
        this.address = address;
        this.contact = contact;
        this.phoneNumber = phoneNumber;
        this.Section = section;
    }
}
