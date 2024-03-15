package myy803.diplomas_project.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import myy803.diplomas_project.model.Application;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.Thesis;
import myy803.diplomas_project.model.User;
@Service
public interface ProfService {
    public Professor retrieveProfile(String username);
	public void saveProfessor(Professor professor);
    public boolean isProfessorPresent(Professor professor);
	public Professor loadUserByUsername(String username) throws UsernameNotFoundException;
    public List<Application> listApplications(int profId, int subjectId);
    public List<Subject> listProfessorSubjects(int user_id);///alla
	public void addSupervisedSubject(Subject subject , Professor professor); 
	public void removeSupervisedSubject(Subject subject, Professor professor );
    public void removeSupervisedSubjectForThesis(Subject subject, Professor professor , int studentId);
    public List<Student> getStudentsWithApplications(Professor professor);
    public List<Thesis> listProfessorThesis(int user_id);
    public void assignDiplomaThesisSubject(Professor professor , int subjectID, Student student);
    public Student getStudentWithFewestRemainingCourses(List<Student> students);
	public Student getStudentWithGradeAndRemainingCourses(List<Student> students, double threshold1, int threshold2);
    public Student getStudentWithBestAverageGrade(List<Student> students);    
    public double calculateOverallGrade(int thesisId);
	public void updateSupervisedSubject(Subject subject , String description , String name) ;
	 public void setThesisGrades(Thesis thesis, double implementationGrade, double reportGrade, double presentationGrade);
	//void updateSupervisedSubject(Subject subject, Professor professor);

}


