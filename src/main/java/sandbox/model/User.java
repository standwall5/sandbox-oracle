package sandbox.model;

public class User {
	private int id;
	private int id3;	// Not sure why there are two IDs, I think I ran into a problem of reusing this parameter in a different function
	private String name;
	private String tplv;
	private String status;
	private String fname;
	private String lname;
	private String bio;
	private String cnumber;
	private String district;
	private String barangay;
	private String address;
	private String workhist;
	private String educhist;
	private String skills;
	private String resdesc;
	private String icon;
	public String getIcon() {
		return icon;
	}

	public User (String address, String workhist, String educhist, String skills, String resdesc) {
		this.address = address;
		this.workhist = workhist;
		this.educhist = educhist;
		this.skills = skills;
		this.resdesc = resdesc;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	private int idApp;
	
	public User(int idApp) {
		this.idApp = idApp;
	}
	
	public User(int id, String fname, String lname, String district, String barangay, String bio, String icon) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.district = district;
		this.barangay = barangay;
		this.bio = bio;
		this.icon = icon;
	}
	
	public User(int id, String fname, String lname, String cnumber, String district, String barangay, String address, String workhist, String educhist, String skills, String resdesc) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.cnumber = cnumber;
		this.district = district;
		this.barangay = barangay;
		this.address = address;
		this.workhist = workhist;
		this.educhist = educhist;
		this.skills = skills;
		this.resdesc = resdesc;
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


	public String getBio() {
		return bio;
	}


	public void setBio(String bio) {
		this.bio = bio;
	}


	public String getCnumber() {
		return cnumber;
	}


	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
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


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getWorkhist() {
		return workhist;
	}


	public void setWorkhist(String workhist) {
		this.workhist = workhist;
	}


	public String getEduchist() {
		return educhist;
	}


	public void setEduchist(String educhist) {
		this.educhist = educhist;
	}


	public String getSkills() {
		return skills;
	}


	public void setSkills(String skills) {
		this.skills = skills;
	}


	public String getResdesc() {
		return resdesc;
	}


	public void setResdesc(String resdesc) {
		this.resdesc = resdesc;
	}


	public void setId3(int id3) {
		this.id3 = id3;
	}


	public User(int id, String name, String tplv, String status) {
		super();
		this.id = id;
		this.name = name;
		this.tplv = tplv;
		this.status = status;
	}


	public User(String name, String tplv, String status, int id3) {
		super();
		this.name = name;
		this.tplv = tplv;
		this.status = status;
		this.id3 = id3;
	}


	public int getId() {
		return id;
	}
	public int getId3() {
		return id3;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTplv() {
		return tplv;
	}
	public void setTplv(String tplv) {
		this.tplv = tplv;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}


}