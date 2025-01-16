package sandbox.model;

public class Skills {
	private int skillId;
	private String name;
	
	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}

	private int resumeId;
	
	public Skills (String name, int skillId) {
		this.name = name;
		this.skillId = skillId;
	}
	
	public Skills (String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}
}