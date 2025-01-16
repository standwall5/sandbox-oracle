package sandbox.model;

public class CompanyContact {
	private String email;
	private String specificAddress;
	private String city;
	private String province;
	
	public CompanyContact(String email, String specificAddress, String city, String province) {
		super();
		this.email = email;
		this.specificAddress = specificAddress;
		this.city = city;
		this.province = province;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSpecificAddress() {
		return specificAddress;
	}
	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
}
