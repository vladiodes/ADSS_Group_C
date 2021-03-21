package Business.Objects;

public class Site {
    private String Address;
    private int PhoneNum;
    private String Contact;
    private String Section;

    public Site(String address, int phoneNum, String contact, String section) {
        setAddress(address);
        setPhoneNum(phoneNum);
        setContact(contact);
        setSection(section);
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPhoneNum(int phoneNum) {
        PhoneNum = phoneNum;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public void setSection(String section) {
        Section = section;
    }

    public String getAddress() {
        return Address;
    }

    public int getPhoneNum() {
        return PhoneNum;
    }

    public String getContact() {
        return Contact;
    }

    public String getSection() {
        return Section;
    }
}
