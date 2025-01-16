package sandbox.model;

public class UserEdit {
	
//	int userId = (int) session.getAttribute("userId");
//	String fname = request.getParameter("fname");
//	String lname = request.getParameter("lname");
//	String cnumber = request.getParameter("cnumber");
//	String bday  = request.getParameter("birthday");
//	String desc = request.getParameter("desc");
//	String specificAddress = request.getParameter("address");
//	String barangay = request.getParameter("barangay");
//	String district = request.getParameter("district");
	
	private int userId;
	private String fname;
	private String lname;
	private String bday;
	private String cnumber;
	private String desc;
	private String address;
	private String barangay;
	private String district;
	
	
	
	public UserEdit(int userId, String fname, String lname, String bday, String cnumber, String desc, String address,
			String barangay, String district) {
		super();
		this.userId = userId;
		this.fname = fname;
		this.lname = lname;
		this.bday = bday;
		this.cnumber = cnumber;
		this.desc = desc;
		this.address = address;
		this.barangay = barangay;
		this.district = district;
	}




	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getBday() {
		return bday;
	}
	public void setBday(String bday) {
		this.bday = bday;
	}
	public String getCnumber() {
		return cnumber;
	}
	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getbarangay() {
		return barangay;
	}
	public void setbarangay(String barangay) {
		this.barangay = barangay;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	
	
}
