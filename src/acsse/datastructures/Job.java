package acsse.datastructures;


import java.util.Date;

public class Job extends VertexNode{

	private int id;
    private String title;
    private String description;
    private String company;
    private double salary;
    private String requirements;
    private String location;
    private String experienceLevel;
    private int positionsOpen;
    private Date applicationDeadline;

	public Job(int id, String title, String description, String company, double salary, String requirements,
               String location, String experienceLevel, int positionsOpen,Date applicationDeadline) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.company = company;
        this.salary = salary;
        this.requirements = requirements;
        this.location = location;
        this.experienceLevel = experienceLevel;
        this.positionsOpen = positionsOpen;
        this.applicationDeadline = applicationDeadline;
    }
    
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public String getCompany() {
        return company;
    }
    
    public double getSalary() {
        return salary;
    }
    
    public String getRequirements() {
        return requirements;
    }
    
    public String getLocation() {
        return location;
    }
    
    public String getExperienceLevel() {
        return experienceLevel;
    }
    
    public int getPositionsOpen() {
        return positionsOpen;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCompany(String company) {
        this.company = company;
    }
    
    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public void setRequirements(String requirements) {
        this.requirements = requirements;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public void setExperienceLevel(String experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    
    public void setPositionsOpen(int positionsOpen) {
        this.positionsOpen = positionsOpen;
    }
    
    public Date getApplicationDeadline() {
		return applicationDeadline;
	}

	public void setApplicationDeadline(Date applicationDeadline) {
		this.applicationDeadline = applicationDeadline;
	}
    
    @Override
    public String toString() {
        return "Job[id=" + id + ", title=" + title + ", description=" + description +
                ", company=" + company + ", salary=" + salary + ", requirements=" + requirements +
                ", location=" + location + ", experienceLevel=" + experienceLevel + ", positionsOpen=" + positionsOpen + "]";
    }

}

