package sandbox.model;


//	To be used as a blueprint by DAO
//	Similar to how int, bool and string are variable types, classes are OBJECT types
//	Object types can have specific parameters and act as containers for data
//	These containers make it easier to map data from the website to the database
//	it is like having a collection and putting it in a box with a label, compared to just
//	the data being disorganized
public class Company {
	private int id;
	private String name;
	private String desc;
	private String icon;
	private String address;
	
	public Company (int id, String name, String desc, String icon, String address){
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.icon = icon;
		this.address = address;
	}

	public int getId() {
		return id;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
