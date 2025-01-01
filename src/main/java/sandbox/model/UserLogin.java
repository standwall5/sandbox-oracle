package sandbox.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogin {

//	Idk why this is separate
//	I think this is used by the Login Servlet
	private static String fname;
	private static String lname;
	private static String cnumber;
	private static String region;
	private static String location;
	private static String bday;
	private static int age;
	private static String education;
	private static String bio;
	private static String uname;
	private static String email;
	private static String password;
	private static String icon;
	private static int id2;
	private static int companyID;
	private static int verifyNum;
	private static String district;
	private static String barangay;
	
	
	
	public static String getDistrict() {
		return district;
	}

	public static void setDistrict(String district) {
		UserLogin.district = district;
	}

	public static String getBarangay() {
		return barangay;
	}

	public static void setBarangay(String barangay) {
		UserLogin.barangay = barangay;
	}

	public static int getVerifyNum() {
		return verifyNum;
	}

	public static void setVerifyNum(int verifyNum) {
		UserLogin.verifyNum = verifyNum;
	}

	public static int getCompanyID() {
		return companyID;
	}

	public static void setCompanyID(int companyID) {
		UserLogin.companyID = companyID;
	}

	public static void setBio(String bio) {
		UserLogin.bio = bio;
	}
	

	public UserLogin(int age, String bday, String cnumber, String district, String barangay, String bio) {
		super();
		this.age = age;
		this.bday = bday;
		this.cnumber = cnumber;
		this.district = district;
		this.barangay = barangay;
		this.bio = bio;
		
	}
	
	public UserLogin(String icon) {
		this.icon = icon;
	}
	
	public UserLogin(String fname, String lname, String education, String location, String region, String bio) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.education = education;
		this.location = location;
		this.region = region;
		this.bio = bio;
		
	}
	
	public UserLogin(String fname, String lname, String email, String password) {
		super();
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.password = password;
		
	}

	
	public UserLogin(int id2, String uname, String email, String password) {
		super();
		this.id2 = id2;
		this.uname = uname;
		this.email = email;
		this.password = password;
	}
	
	public UserLogin(int id2) {
		super();
		this.id2 = id2;
	}
	
	public UserLogin(String uname, String email2, String password2) {
		super();
		this.uname = uname;
		this.email = email;
		this.password = password;
	}
	
	public static String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public static String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public static String getCnumber() {
		return cnumber;
	}

	public void setCnumber(String cnumber) {
		this.cnumber = cnumber;
	}

	public static String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public static String getLocation() {
		return location;
	}

	public  void setLocation(String location) {
		this.location = location;
	}

	public static String getBday() {
		return bday;
	}

	public void setBday(String bday) {
		this.bday = bday;
	}

	public static int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public static String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public static String getBio() {
		return bio;
	}

	public static void setBio(UserLogin userLogin, String bio) {
		userLogin.bio = bio;
	}

	public static String getIcon() {
		return icon;
	}

	public static void setIcon(String icon) {
		UserLogin.icon = icon;
	}
	

	public static int getId2() {
		return id2;
	}
	
	public static void setId2(int id2) {
		UserLogin.id2 = id2;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public static String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public static String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
//	Email validation
	public static boolean isValidEmail(String email) {
	    // Regular expression pattern for basic email validation
	    String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	    // Compile the pattern into a regex pattern object
	    Pattern pattern = Pattern.compile(regex);

	    // Create a Matcher object to match the input email against the pattern
	    Matcher matcher = pattern.matcher(email);

	    // Return true if the email matches the pattern, false otherwise
	    return matcher.matches();
	}

}
