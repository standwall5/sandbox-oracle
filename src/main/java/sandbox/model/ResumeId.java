package sandbox.model;

public class ResumeId {
	private static int resumeId;

	public ResumeId(int resumeId) {
		super();
		this.resumeId = resumeId;
	}

	public int getResumeId() {
		return resumeId;
	}

	public static void setResumeId(int resumeId) {
		ResumeId.resumeId = resumeId;
	}
	
	
}
