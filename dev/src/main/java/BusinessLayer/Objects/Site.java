package BusinessLayer.Objects;

import DTO.SiteDTO;

public class Site implements persistentObject<SiteDTO> {
    private String Address;
    private String PhoneNum;
    private String Contact;
    private String Section;

    public Site(String address, String phoneNum, String contact, String section) {
        setAddress(address);
        setPhoneNum(phoneNum);
        setContact(contact);
        setSection(section);
    }

    public Site(SiteDTO dto) {
        setAddress(dto.address);
        setPhoneNum(dto.phoneNumber);
        setContact(dto.contact);
        setSection(dto.Section);
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setPhoneNum(String phoneNum) {
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

    public String getPhoneNum() {
        return PhoneNum;
    }

    public String getContact() {
        return Contact;
    }

    public String getSection() {
        return Section;
    }

    @Override
    public String toString() {
        return "Site{" +
                "Address='" + Address + '\'' +
                ", PhoneNum=" + PhoneNum +
                ", Contact='" + Contact + '\'' +
                ", Section='" + Section + '\'' +
                '}';
    }

    @Override
    public SiteDTO toDTO() {
        return new SiteDTO(getAddress(), getContact(), getPhoneNum(), getSection());
    }
}
