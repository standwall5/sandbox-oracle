package sandbox.model;

public class WorkHist {
	private String jobTitle;
	private String companyName;
	private int startYear;
	private int endYear;
	private int resumeId;
	private int workId;

	public int getWorkId() {
		return workId;
	}

	public void setWorkId(int workId) {
		this.workId = workId;
	}

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public WorkHist(String jobTitle, String companyName, int startYears, int endYears, int workId) {
		// TODO Auto-generated constructor stub
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.startYear = startYears;
		this.endYear = endYears;
		this.workId = workId;
	}
	
	public WorkHist(String jobTitle, String companyName, int startYears, int endYears) {
		// TODO Auto-generated constructor stub
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.startYear = startYears;
		this.endYear = endYears;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getStartYear() {
		return startYear;
	}

	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}

	public int getEndYear() {
		return endYear;
	}

	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}

}
