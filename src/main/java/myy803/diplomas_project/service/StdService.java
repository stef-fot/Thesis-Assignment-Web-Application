package myy803.diplomas_project.service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.SubjectDAO;
import myy803.diplomas_project.model.Application;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.User;
import myy803.diplomas_project.service.StdServiceImpl.SubjectDetails;
@Service
public interface StdService {
	public void saveStudent(Student student);
	public Student loadUserByUsername(String username) throws UsernameNotFoundException;// S1
    public boolean isStudentPresent(Student student);
    public List<Subject> getAvailableDiplomaThesisSubjects();// S2
    public SubjectDetails getSubjectDetails(int subjectId);// S3
    public void applyForSubject(Subject subject , Student student);// S4
    public Student retrieveProfile(String username);
	List<Subject> listStudentSubjects(/*int user_id*/);
}


