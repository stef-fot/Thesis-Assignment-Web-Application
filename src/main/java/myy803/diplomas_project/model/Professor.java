package myy803.diplomas_project.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import myy803.diplomas_project.dao.ThesisDAO;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.OneToOne;


@Entity
@Table(name="professors") 
public class Professor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

	@Column(name = "full_name")
    private String fullName;
    
    @Column(name = "specialty")
    private String specialty;
    
    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Subject> supervisedSubjects = new ArrayList<>();// All the subjects that he has and are applicable for a diploma 
    
	@OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL)
    private List<Thesis> supervisedThesisSubjects = new ArrayList<>();// All the subjects who are thesis and are supervised by him
    
    //@OneToOne
	 @JoinColumn(name = "user_id" , referencedColumnName = "id")
	private int userId;

	public Professor() {
		
	}
	 
	public Professor(int id, String fullName, String specialty, int userId) {
        super();
    	this.id = id;
        this.fullName = fullName;
        this.specialty = specialty;
        this.userId = userId;
    }

    
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    
    
    public void setSupervisedSubjects(List<Subject> supervisedSubjects) {
        this.supervisedSubjects = supervisedSubjects;
    }
    
    public List<Subject> getSupervisedSubjects() {
        return supervisedSubjects;
    }
    
    public List<Thesis> getSupervisedThesisSubjects() {
		return supervisedThesisSubjects;
	}


	public void setSupervisedThesisSubjects(List<Thesis> supervisedThesisSubjects) {
		this.supervisedThesisSubjects = supervisedThesisSubjects;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
    public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	 @Override
		public String toString() {
			return "Professor [id=" + id + ", fullName=" + fullName + ", specialty=" + specialty + ", supervisedSubjects="
					+ supervisedSubjects + ", supervisedThesisSubjects=" + supervisedThesisSubjects + ", userId=" + userId
					+ "]";
		}


}

