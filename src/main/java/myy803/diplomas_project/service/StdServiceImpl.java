package myy803.diplomas_project.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.StdDAO;
import myy803.diplomas_project.dao.SubjectDAO;
import myy803.diplomas_project.dao.ApplicationDAO;
import myy803.diplomas_project.dao.UserDAO;

import myy803.diplomas_project.model.Application;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.User;

@Service
public class StdServiceImpl implements StdService {

    private Student student; 
    
    @Autowired
	private UserDAO userDAO;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private StdDAO stdDAO;
		
	@Autowired
	private ApplicationDAO applicationDAO;
	
	@Override
	public void saveStudent(Student student) {
        stdDAO.save(student);	
    }

	@Override
	public boolean isStudentPresent(Student student) {
		Optional<Student> storedUser = stdDAO.findById(student.getId());
		return storedUser.isPresent();
	}
	
	@Override
	public Student loadUserByUsername(String username) throws UsernameNotFoundException {
		 boolean exist = userDAO.findByUsername(username).isPresent();
		 if (exist) {		 	
			Optional<User> user = userDAO.findByUsername(username);
			Integer id = user.get().getId();
			Optional <Student> student = stdDAO.findById(id);
			return student.get();
	    } else {
	        System.out.println("No student found with username: " + username);
	        throw new UsernameNotFoundException("No user found with username: " + username);
	    }
	}

    @Autowired
    private ProfDAO profDAO;

    public List<Subject> getAvailableDiplomaThesisSubjects() {// S2
        List<Subject> availableSubjects = new ArrayList<>();
        for (Professor professor : profDAO.findAll()) {
            availableSubjects.addAll(professor.getSupervisedSubjects());
        }
        //availableSubjects.removeIf(subject -> subject.getStudent() != null);
        //student.setStudentSubjects(availableSubjects);
        //return student.getStudentSubjects();
        return availableSubjects;
    }
    @Transactional
    @Override
	public List<Subject> listStudentSubjects(/*int user_id*/) {
    	List <Subject> subjects = subjectDAO.findAll();
    	return subjects;
	}
    public static class SubjectDetails {
        private String title;
        private String objectives;
        private String supervisor;

        public SubjectDetails(String title, String objectives, String supervisor) {
            this.title = title;
            this.objectives = objectives;
            this.supervisor = supervisor;
        }
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getObjectives() {
            return objectives;
        }

        public void setObjectives(String objectives) {
            this.objectives = objectives;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }
    }

    @Autowired
    private SubjectDAO subjectDAO;
    public SubjectDetails getSubjectDetails(int subjectId) {// S3
    	Optional <Subject> subject = subjectDAO.findById(subjectId);
        Professor supervisor = subject.get().getSupervisor();
        String title = subject.get().getName();
        String objectives = subject.get().getDescription();
        String supervisorName = supervisor.getFullName();

        return new SubjectDetails(title, objectives, supervisorName);
    }

    public void applyForSubject(Subject subject , Student student) {// S4
        if (student.getAssignedSubject() != null) {
            System.out.println("You have already been assigned a subject.");
            return;
        }

        for (Application application : student.getApplications()) {
            if (application.getSubject().equals(subject)) {
                System.out.println("You have already applied for this subject.");
                return;
            }
        }

        Date applicationDate = new Date();
        Application application = new Application(student, subject, applicationDate);
        student.getApplications().add(application);
        subject.getApplications().add(application);
        applicationDAO.save(application);
        System.out.println("You have successfully applied for the subject '" + subject.getName() + "'.");
    }

    @Override
    public Student retrieveProfile(String username) {
    	Optional<User> user = userDAO.findByUsername(username);
		Integer id = user.get().getId();
		Optional <Student> optionalStudent = stdDAO.findByUserId(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }   
    
    
    
}