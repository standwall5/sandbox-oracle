package sandbox.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogin {

    private String fname;
    private String lname;
    private String bday;
    private String bio;
    private String password;
    private String icon;
    private int id2;
    private int companyID;
    private int verifyNum;

    // Constructor for fname, lname, password
    public UserLogin(String fname, String lname, String password) {
        this.fname = fname;
        this.lname = lname;
        this.password = password;
    }

    // Constructor for bio, bday
    public UserLogin(String bio, String bday) {
        this.bio = bio;
        this.bday = bday;
    }

    // Constructor for icon
    public UserLogin(String icon) {
        this.icon = icon;
    }

    // Constructor for id2, uname, email, password
    public UserLogin(int id2, String uname, String email, String password) {
        this.id2 = id2;
        this.password = password;
    }

    // Constructor for id2
    public UserLogin(int id2) {
        this.id2 = id2;
    }

    // Constructor for fname, lname, bio, bday, password
    public UserLogin(String fname2, String lname2, String bio2, String bday2, String password2) {
        this.fname = fname2;
        this.lname = lname2;
        this.bio = bio2;
        this.bday = bday2;
        this.password = password2;
    }

    public int getVerifyNum() {
        return verifyNum;
    }

    public void setVerifyNum(int verifyNum) {
        this.verifyNum = verifyNum;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyID(int companyID) {
        this.companyID = companyID;
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

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Email validation
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
