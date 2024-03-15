package myy803.diplomas_project.model;

import java.util.Date;
import javax.persistence.Entity;
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
@Table(name = "applications")
public class Application {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@ManyToOne
	@JoinColumn(name = "applicant_id" , referencedColumnName = "id")
    private Student applicant;
	
	@ManyToOne
	@JoinColumn(name = "subject_id" , referencedColumnName = "subject_id")
    private Subject subject;

	@Column(name = "application_date")
    private Date applicationDate;

    public Application() {}

    public Application(Student applicant, Subject subject, Date applicationDate) {
        this.applicant = applicant;
        this.subject = subject;
        this.applicationDate = applicationDate;
    }
    
    public Application(Student applicant, Subject subject) {
        this.applicant = applicant;
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getApplicant() {
        return applicant;
    }

    public void setApplicant(Student applicant) {
        this.applicant = applicant;
    }

    public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id=" + id +
                ", applicant=" + applicant +
                ", subject=" + subject +
                ", applicationDate=" + applicationDate +
                '}';
    }
}

