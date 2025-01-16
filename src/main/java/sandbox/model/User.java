package sandbox.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	private int id;
	private int id3;	// Not sure why there are two IDs, I think I ran into a problem of reusing this parameter in a different function
	private String name;
	private String tplv;
	private String status;
	private String fname;
	private String lname;
	private String bio;
	private String bday;
	private String cnumber;
	private String district;
	private String barangay;
	private String address;
	private String workhist;
	private String educhist;
//	private String skills;
	private String resdesc;
	private String icon;
	private String specificAddress;
	private String email;
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	private List<WorkHist> workHistory;
    private List<Skills> skills;
    private List<Education> education;
	
	public String getIcon() {
		return icon;
	}

	
//	users.add(new User(userId, fname, lname, cnumber, specific_address, district, barangay));
//	
//	public User (String address, String workhist, String educhist, String skills, String resdesc) {
//		this.address = address;
//		this.workhist = workhist;
//		this.educhist = educhist;
//		this.skills = skills;
//		this.resdesc = resdesc;
//	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	private int idApp;
	
	public User(int idApp) {
		this.idApp = idApp;
	}
	
	public User(int id, String fname, String lname, String bio, String icon, String district, String barangay, String email) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.bio = bio;
		this.icon = icon;
		this.district = district;
		this.barangay = barangay;
		this.email = email;
	}
	
//	users.add(new User(userId, fname, lname, cnumber, specific_address, district, barangay));
	
	public User(int id, String fname, String lname, String icon, String bio, String bday, String cnumber, String specificAddress, String district, String barangay, String email) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.bio = bio;
		this.icon = icon;
		this.cnumber = cnumber;
		this.specificAddress = specificAddress;
		this.district = district;
		this.barangay = barangay;
		this.email = email;
	}
	
	
	public User(int id, String fname, String lname, String bio, String cnumber, String specificAddress, String district, String barangay, List<WorkHist> workHistory, List<Education> educ, List<Skills> skills) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.bio = bio;
		this.cnumber = cnumber;
		this.specificAddress = specificAddress;
		this.district = district;
		this.barangay = barangay;
		this.workHistory = workHistory;
        this.skills = skills;
        this.education = educ;
	}
	
	public User(int id, String fname, String lname, String bio, String email, String cnumber, String specificAddress, String district, String barangay, List<WorkHist> workHistory, List<Education> educ, List<Skills> skills) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.bio = bio;
		this.email = email;
		this.cnumber = cnumber;
		this.specificAddress = specificAddress;
		this.district = district;
		this.barangay = barangay;
		this.workHistory = workHistory;
        this.skills = skills;
        this.education = educ;
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


//	public String getSkills() {
//		return skills;
//	}
//
//
//	public void setSkills(String skills) {
//		this.skills = skills;
//	}


	public String getResdesc() {
		return resdesc;
	}


	public List<WorkHist> getWorkHistory() {
		return workHistory;
	}


	public void setWorkHistory(List<WorkHist> workHistory) {
		this.workHistory = workHistory;
	}


	public List<Skills> getSkills() {
		return skills;
	}


	public void setSkills(List<Skills> skills) {
		this.skills = skills;
	}


	public List<Education> getEducation() {
		return education;
	}


	public void setEducation(List<Education> education) {
		this.education = education;
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


	public String getSpecificAddress() {
		return specificAddress;
	}


	public void setSpecificAddress(String specificAddress) {
		this.specificAddress = specificAddress;
	}


	public String getBday() {
		return bday;
	}


	public void setBday(String bday) {
		this.bday = bday;
	}


}