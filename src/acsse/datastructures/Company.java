package acsse.datastructures;

public class Company extends VertexNode{
  
	private int id;
    private String name;
    private String username;
    private String password;
	private String contactInformation;
    private String location;
    private String industry;
    private int companySize;
	private String email;
    private String city;
    private String phone;
    
    public Company(int id,String password, String username, String name, String contactInformation, 
    		String location, String industry, int companySize,String email,String city,String phone) {
        this.id = id;
        this.name = name;
        this.contactInformation = contactInformation;
        this.location = location;
        this.industry = industry;
        this.companySize = companySize;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
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



    // getters and setters
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

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getCompanySize() {
        return companySize;
    }

    public void setCompanySize(int companySize) {
        this.companySize = companySize;
    }

    // override toString method to print company information
    @Override
    public String toString() {
        return "Company{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", contactInformation='" + contactInformation + '\'' +
                ", location='" + location + '\'' +
                ", industry='" + industry + '\'' +
                ", companySize=" + companySize  +
                '}';
    }
}

