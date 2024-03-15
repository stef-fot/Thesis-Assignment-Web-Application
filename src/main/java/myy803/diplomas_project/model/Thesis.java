package myy803.diplomas_project.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.OneToOne;

@Entity
@Table(name = "thesis")
public class Thesis {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name = "id")
	private int id;

    @ManyToOne
    @JoinColumn(name = "supervisor_id" , referencedColumnName = "id")
    private Professor supervisor;

    @Column(name = "thesis_title")
    private String thesisTitle;

    @Column(name = "assigned_student_name")
    private String assignedStudentName;
   
	@Column(name = "implementation_grade")
    private double implementationGrade;

    @Column(name = "report_grade")
    private double reportGrade;

    @Column(name = "presentation_grade")
    private double presentationGrade;
    
    @Column(name = "average_grade")
    private double averageGrade;
    
    
    
    public Thesis(Professor supervisor, Subject subject, double implementationGrade, double reportGrade, double presentationGrade, double averageGrade) {
        this.supervisor = supervisor;
        this.implementationGrade = implementationGrade;
        this.reportGrade = reportGrade;
        this.presentationGrade = presentationGrade;
        this.averageGrade = averageGrade;
    }

    public Thesis(Professor supervisor/* Subject subject*/) {
        this.supervisor = supervisor;
        //this.subject = subject;
    }
    
	public Thesis() {
	}

    public int getId() {
        return id;
    }

    public Professor getSupervisor() {
        return supervisor;
    }

    public String getThesisTitle() {
		return thesisTitle;
	}

    public String getAssignedStudentName() {
		return assignedStudentName;
	}

	public void setAssignedStudentName(String assignedStudentName) {
		this.assignedStudentName = assignedStudentName;
	}

	public void setThesisTitle(String thesisTitle) {
		this.thesisTitle = thesisTitle;
	}

    public void setId(int id) {
        this.id = id;
    }

    public void setSupervisor(Professor supervisor) {
        this.supervisor = supervisor;
    }
    
    public double getImplementationGrade() {
        return implementationGrade;
    }

    public void setImplementationGrade(double implementationGrade) {
        this.implementationGrade = implementationGrade;
    }

    public double getReportGrade() {
        return reportGrade;
    }

    public void setReportGrade(double reportGrade) {
        this.reportGrade = reportGrade;
    }

    public double getPresentationGrade() {
        return presentationGrade;
    }

    public void setPresentationGrade(double presentationGrade) {
        this.presentationGrade = presentationGrade;
    }
    
    public double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}

}

