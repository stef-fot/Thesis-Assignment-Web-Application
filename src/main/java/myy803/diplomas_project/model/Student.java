package myy803.diplomas_project.model;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.SubjectDAO;


@Entity
@Table(name="students") 
public class Student {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private int id;
	
	//@OneToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "id")
    private int userId;

	@Column(name = "full_name")
    private String fullName;
    
    @Column(name = "year_of_studies")
    private int yearOfStudies;
    
    @Column(name = "current_average_grade")
    private double currentAverageGrade;
    
    @Column(name = "remaining_cources_for_graduation")
    private int remainingCoursesForGraduation;
    
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<Application> applications;// Applications that have been made for a thesis 
    
    @OneToOne
    @JoinColumn(name = "assigned_subject_id" , referencedColumnName = "id")
    private Thesis assignedSubject;// The one and only assigned subject (diploma)

	public Student( String fullName, int yearOfStudies,
            double currentAverageGrade, int remainingCoursesForGraduation,
            Thesis assignedSubject, String user_name) {
	 super();
	 this.fullName = fullName;
	 this.yearOfStudies = yearOfStudies;
	 this.currentAverageGrade = currentAverageGrade;
	 this.remainingCoursesForGraduation = remainingCoursesForGraduation;
	 this.applications = new ArrayList<>();
	 this.assignedSubject = assignedSubject;
}


    public Student() {
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {//S1
        this.fullName = fullName;
    }

    public int getYearOfStudies() {
        return yearOfStudies;
    }

    public void setYearOfStudies(int yearOfStudies) {//S1
        this.yearOfStudies = yearOfStudies;
    }

    public double getCurrentAverageGrade() {
        return currentAverageGrade;
    }

    public void setCurrentAverageGrade(double currentAverageGrade) {//S1
        this.currentAverageGrade = currentAverageGrade;
    }

    public int getRemainingCoursesForGraduation() {
        return remainingCoursesForGraduation;
    }

    public void setRemainingCoursesForGraduation(int remainingCoursesForGraduation) {//S1
        this.remainingCoursesForGraduation = remainingCoursesForGraduation;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }
    
    public void setAssignedSubject(Thesis assignedSubject) {
        this.assignedSubject = assignedSubject;
    }
    
    public Thesis getAssignedSubject() {
        return assignedSubject;
    }
	
	    
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", yearOfStudies=" + yearOfStudies +
                ", currentAverageGrade=" + currentAverageGrade +
                ", remainingCoursesForGraduation=" + remainingCoursesForGraduation +
                ", applications=" + applications +
                '}';
    }
}

