package acsse.datastructures;

import java.util.List;

public class JobSeeker extends VertexNode{

    public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	public void setSkills(List<String> skills) {
		this.skills = skills;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}


	private int id;
    private String name;
    private String username;
    private String password;
	private String email;
    private String phone;
    private String province;
    private String educationLevel;
    private String workExperience;
    private List<String> skills;
    private String jobTitle;
    private String industry;
    
    public String getUsername() {
  		return username;
  	}

  	public void setUsername(String username) {
  		this.username = username;
  	}

  	public String getPassword() {
  		return password;
  	}

  	public void setPassword(String password) {
  		this.password = password;
  	}

    public JobSeeker(int id, String username,String password,String name, String email, String phone, String province,
            String educationLevel, String workExperience, List<String> skills, String jobTitle, String industry,
            String workSchedule) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.province = province;
        this.educationLevel = educationLevel;
        this.workExperience = workExperience;
        this.skills = skills;
        this.jobTitle = jobTitle;
        this.industry = industry;
        this.username = username;
        this.password = password;
    }

    public int  getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }



    public String getProvince() {
        return province;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public String getWorkExperience() {
        return workExperience;
    }

    public List<String> getSkills() {
        return skills;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getIndustry() {
        return industry;
    }


    // Override toString method to print out JobSeekerNode details
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Province: ").append(province).append("\n");
        sb.append("Education Level: ").append(educationLevel).append("\n");
        sb.append("Work Experience: ").append(workExperience).append("\n");
        sb.append("Skills: ").append(skills.toString()).append("\n");
        sb.append("Job Title: ").append(jobTitle).append("\n");
        sb.append("Industry: ").append(industry).append("\n");
        return sb.toString();
    }
}
