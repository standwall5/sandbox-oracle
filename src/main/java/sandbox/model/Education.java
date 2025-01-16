package sandbox.model;

public class Education {
	private String school_name;
	private String degree;
	private int start_year;
	private int end_year;
	private int resumeId;
	private int educId;
	

	public Education(String school, String degree, int start, int end, int educId) {
		super();
		this.school_name = school;
		this.degree = degree;
		this.start_year = start;
		this.end_year = end;
		this.educId = educId;
	}
	
	public Education(String school, String degree, int start, int end) {
		super();
		this.school_name = school;
		this.degree = degree;
		this.start_year = start;
		this.end_year = end;
	}
	
	public int getEducId() {
		return educId;
	}

	public void setEducId(int educId) {
		this.educId = educId;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSchool_name() {
		return school_name;
	}
	
	public void setSchool_name(String school_name) {
		this.school_name = school_name;
	}
	
	public int getStart_year() {
		return start_year;
	}
	
	public void setStart_year(int start_year) {
		this.start_year = start_year;
	}
	
	public int getEnd_year() {
		return end_year;
	}
	
	public void setEnd_year(int end_year) {
		this.end_year = end_year;
	}

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}
	
	
}
