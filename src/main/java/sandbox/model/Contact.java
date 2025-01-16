package sandbox.model;

public class Contact {
    private String email;
    private String contact_number;
    private String specific_address;
    private String district;
    private String barangay;

    @Override
    public String toString() {
        return "Contact [email=" + email + ", contact_number=" + contact_number + ", specific_address="
        		+ specific_address + ", district=" + district + ", barangay=" + barangay +"]";
    }

    
    // Constructor for just email
    public Contact(String email) {
        this.email = email;
    }

    // Constructor for all fields
    public Contact(String email, String contact_number, String specific_address, String district, String barangay) {
        this.email = email;
        this.contact_number = contact_number;
        this.specific_address = specific_address;
        this.district = district;
        this.barangay = barangay;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getSpecific_address() {
        return specific_address;
    }

    public void setSpecific_address(String specific_address) {
        this.specific_address = specific_address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }
}
