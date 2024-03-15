package myy803.diplomas_project.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.diplomas_project.dao.ProfDAO;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "supervisor_id" , referencedColumnName = "id")
    private Professor supervisor;
    
	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Application> applications = new ArrayList<>();

    public Subject(String name, String description, Professor supervisor) {
        this.name = name;
        this.description = description;
        this.supervisor = supervisor;
    }
    
    public Subject() {
    	
    }

    // Getters and setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Professor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Professor supervisor) {
		this.supervisor = supervisor;
	}
    
    public List<Application> getApplications() {
        return applications;
    }
    
    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    
    public void addApplication(Application application) {
        this.applications.add(application);
    }
    
    public void removeApplication(Application application) {
        this.applications.remove(application);
    }
	
}

    
  
    
  