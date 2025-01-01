package sandbox.model;

public class JobPosts {
//	This is a lot, but I didn't edit it due to fear of breaking the code
//	There are definitely variables that are not needed here
	private int id;
	private String fname;
	private String lname;
	private String education;
	private String location;
	private String region;
	private String bio;
	private String icon;
	private String name;
	private String desc;
	private String title;
	private String postdate;
	private String companyname;
	private String address;
	private String workExp;
	private String skills;
	private String district;
	private String barangay;
	private String educhist;
	private String resdesc;
	private String category;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getResdesc() {
		return resdesc;
	}

	public void setResdesc(String resdesc) {
		this.resdesc = resdesc;
	}

	public String getEduchist() {
		return educhist;
	}

	public void setEduchist(String educhist) {
		this.educhist = educhist;
	}

	public String getWorkhist() {
		return workhist;
	}

	public void setWorkhist(String workhist) {
		this.workhist = workhist;
	}

	private String workhist;

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

	public String getWorkExp() {
		return workExp;
	}

	public void setWorkExp(String workExp) {
		this.workExp = workExp;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public JobPosts(int id, String address, String work, String educ, String skills, String desc) {
		this.address = address;
		this.workExp = work;
		this.education = educ;
		this.skills = skills;
		this.desc = desc;
	}

	public JobPosts(int id, String company, String title, String desc, String address, String category, String postdate,
			String placeholder) {
		super();
		this.id = id;
		this.companyname = company;
		this.title = title;
		this.desc = desc;
		this.address = address;
		this.category = category;
		this.postdate = postdate;
	}

	public JobPosts(String fname, String lname, String education, String location, String region, String bio,
			String icon) {
		this.fname = fname;
		this.lname = lname;
		this.education = education;
		this.location = location;
		this.region = region;
		this.bio = bio;
		this.icon = icon;

	}

	public JobPosts(int id, String fname, String lname, String district, String barangay, String bio, String icon) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.district = district;
		this.barangay = barangay;
		this.bio = bio;
		this.icon = icon;

	}

	public JobPosts(int id, String fname, String lname, String education, String district, String barangay, String bio,
			String icon, int id2) {
		super();
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.education = education;
		this.district = district;
		this.barangay = barangay;
		this.bio = bio;
		this.icon = icon;

	}

	public JobPosts(String name, String desc, String icon, String address) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.desc = desc;
		this.icon = icon;
		this.address = address;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPostdate() {
		return postdate;
	}

	public void setPostdate(String postdate) {
		this.postdate = postdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

}
