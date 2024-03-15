package myy803.diplomas_project.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import myy803.diplomas_project.dao.ApplicationDAO;
import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.StdDAO;
import myy803.diplomas_project.dao.ThesisDAO;
import myy803.diplomas_project.dao.SubjectDAO;
import myy803.diplomas_project.dao.UserDAO;

import myy803.diplomas_project.model.Application;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.Thesis;
import myy803.diplomas_project.model.User;
import myy803.diplomas_project.service.ProfService;
import myy803.diplomas_project.service.UserServiceImpl;
@Service
public class ProfServiceImpl implements ProfService {

	//@Autowired
	//private Professor professor;	
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ProfDAO profDAO;
	
	@Autowired
	private StdDAO stdDAO;
	
	@Autowired
	private ThesisDAO thesisDAO;
	
	@Autowired
	private ApplicationDAO applicationDAO;
	
	@Autowired
	private SubjectDAO subjectDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	@Override
	public void saveProfessor(Professor professor) {
		profDAO.save(professor);
    }

	@Override
	public boolean isProfessorPresent(Professor professor) {
		Optional <Professor> storedUser = profDAO.findById(professor.getId());
	    return storedUser.isPresent();
	}

	@Override
	public Professor loadUserByUsername(String username) throws UsernameNotFoundException {
		 boolean exist = userDAO.findByUsername(username).isPresent();
		 if (exist) {
			Optional<User> user = userDAO.findByUsername(username);
			Integer id = user.get().getId();
			Optional <Professor> professor = profDAO.findById(id);
		 	return professor.get();
	    } else {
	        System.out.println("No professor found with username: " + username);
	        throw new UsernameNotFoundException("No user found with username: " + username);
	    }
	}
	
	//@Transactional
	@Override
	public void addSupervisedSubject(Subject subject , Professor professor) {// P3
		professor.getSupervisedSubjects().add(subject);
		subjectDAO.save(subject);
    }
	
	@Transactional
	@Override
	public void updateSupervisedSubject(Subject subject , String description , String name) {
		subject.setDescription(description);
		subject.setName(name);
		subjectDAO.save(subject);

    }
	
	@Override
    public void removeSupervisedSubject(Subject subject , Professor professor) {// P4
		professor.getSupervisedSubjects().remove(subject);
    	subjectDAO.delete(subject);
		
    	System.out.println("Afet the subjectDAO delete");

	}
    
	@Override
    public void removeSupervisedSubjectForThesis(Subject subject , Professor professor , int studentId) {// P4
		professor.getSupervisedSubjects().remove(subject);
        
    	//System.out.println("Afet the getSupervisedSubjcets()");
    	List <Application> allAplications = applicationDAO.findAll();
    	for (Application application : allAplications) {
        	if (application.getApplicant().getId() == studentId) {
        		System.out.println(application.getId());
        		applicationDAO.delete(application);
        		
        	}
    	}
    	subjectDAO.delete(subject);
		
    	//System.out.println("Afet the subjectDAO delete");

	}
	
    public List<Student> getStudentsWithApplications(Professor professor) {
        List<Student> studentsWithApplications = new ArrayList<>();
        for (Subject subject : professor.getSupervisedSubjects()) {
            List<Application> applications = subject.getApplications();
            for (Application application : applications) {
            	studentsWithApplications.add(application.getApplicant());
            }
        }
        return studentsWithApplications;
    }

    public void assignDiplomaThesisSubject(Professor professor , int subjectID, Student student) {// P6
        Optional <Subject> diplomaThesisSubject = subjectDAO.findById(subjectID);
        if (diplomaThesisSubject == null) {
            System.out.println("Invalid subject id.");
            return;
        }
        
        // Assign diploma thesis subject to eligible student based on strategy
        if (student != null) {
        	Optional <Subject> subject = subjectDAO.findById(subjectID);
        	Thesis newThesis = new Thesis(professor); //, diplomaThesisSubject.get());
            newThesis.setAssignedStudentName(student.getFullName());
        	professor.getSupervisedThesisSubjects().add(newThesis);
            newThesis.setThesisTitle(subject.get().getName());
            thesisDAO.save(newThesis);
            removeSupervisedSubjectForThesis(diplomaThesisSubject.get(),professor,student.getId());
        	//System.out.println("After removal");
            student.setAssignedSubject(newThesis);
            stdDAO.save(student);
            System.out.println("Diploma thesis subject assigned to student: " + student.getFullName());
            System.out.println("Student can start working on the project.");
        } else {
            System.out.println("No eligible students found.");
        }
    }
    

    public Student getStudentWithFewestRemainingCourses(List<Student> students) {
        Student selectedStudent = null;
        int minRemainingCourses = Integer.MAX_VALUE;

        for (Student student : students) {
            if (student.getRemainingCoursesForGraduation() < minRemainingCourses) {
                minRemainingCourses = student.getRemainingCoursesForGraduation();
                selectedStudent = student;
            }
        }

        return selectedStudent;
    }

	public Student getStudentWithGradeAndRemainingCourses(List<Student> students, double threshold1, int threshold2) {
        Student selectedStudent = null;

        for (Student student : students) {
            if (student.getCurrentAverageGrade() > threshold1 && student.getRemainingCoursesForGraduation() < threshold2) {
                selectedStudent = student;
                break; // Return the first eligible student found
            }
        }

        return selectedStudent;
    }

	// Helper method to get student with the best average grade
    public Student getStudentWithBestAverageGrade(List<Student> students) {
        Student selectedStudent = null;
        double highestAverageGrade = Double.MIN_VALUE;

        for (Student student : students) {
            if (student.getCurrentAverageGrade() > highestAverageGrade) {
                highestAverageGrade = student.getCurrentAverageGrade();
                selectedStudent = student;
            }
        }

        return selectedStudent;
    }
    
    public double calculateOverallGrade(int thesisId) {// P9
    	Optional<Thesis> thesis = thesisDAO.findById(thesisId);
        double implementationGrade = thesis.get().getImplementationGrade();
        double reportGrade = thesis.get().getReportGrade();
        double presentationGrade = thesis.get().getPresentationGrade();

        // Calculate total grade based on weighted average formula
        double totalGrade = 0.7 * implementationGrade + 0.15 * reportGrade + 0.15 * presentationGrade;
        thesis.get().setAverageGrade(totalGrade);
        thesisDAO.save(thesis.get());
        return totalGrade;
    }

    @Override
    public Professor retrieveProfile(String username) {
    	Optional<User> user = userDAO.findByUsername(username);
		int id = user.get().getId();
		Optional <Professor> optionalProfessor = profDAO.findByUserId(id);
        if (optionalProfessor.isPresent()) {
        	//System.out.println("Professor name : " + optionalProfessor.get().getFullName());
            return optionalProfessor.get();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }    
    
    @Override
    public List<Application> listApplications(int prof_id, int subjectId) {// P5
		Professor professor = profDAO.findById(prof_id).orElseThrow(
            ()-> new UsernameNotFoundException(
                String.format("USER_NOT_FOUND", prof_id)
            )
        );
//		System.out.println(professor.getId());
        Optional<Subject> subject = subjectDAO.findById(subjectId);
//        System.out.println(subject.get().getId());
//        List<Application> app = subject.get().getApplications();
//        for(Application app1:app) {
//        	System.out.println(app1);
//        }
//        System.out.println(subject.get().getApplications());
        if (!subject.isPresent()) {
            throw new IllegalArgumentException("Subject with id " + subjectId + " not found.");
        }


        if (!professor.getSupervisedSubjects().contains(subject.get())) {
            throw new IllegalArgumentException("Professor is not the supervisor of subject with id " + subjectId + ".");
        }
     // Eagerly initialize the applications collection
        Hibernate.initialize(subject.get().getApplications());
        return subject.get().getApplications();
    }
    
    @Transactional
    @Override
	public List<Subject> listProfessorSubjects(int user_id) {// P2
    	Optional<Professor> professor = profDAO.findByUserId(user_id);
    	List <Subject> subjects = subjectDAO.findBySupervisorId(professor.get().getId());
    	
    	return subjects;
    	//return professor.get().getSupervisedSubjects();
	}

    @Override
    public List<Thesis> listProfessorThesis(int user_id) {// P7
    	Optional<Professor> professor = profDAO.findByUserId(user_id);
    	List <Thesis> thesis = thesisDAO.findBySupervisorId(professor.get().getId());
    	
    	return thesis;
	}
    
    public void setThesisGrades(Thesis thesis, double implementationGrade, double reportGrade, double presentationGrade) {// P9
        // Set implementation, report, and presentation grades for the thesis
        thesis.setImplementationGrade(implementationGrade);
        thesis.setReportGrade(reportGrade);
        thesis.setPresentationGrade(presentationGrade);
        thesis.setAverageGrade(0);
        // Save the updated thesis to the database
        thesisDAO.save(thesis);
	}

}
