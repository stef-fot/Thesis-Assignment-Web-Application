package myy803.diplomas_project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Assertions;
import java.util.Date;

import myy803.diplomas_project.model.*;
import myy803.diplomas_project.dao.*;
 

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")

class ProfServiceTest {

    @Autowired
    ProfDAO professorDAO;
    
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    SubjectDAO subjectDAO;
    
    @Autowired
    StdDAO studentDAO;
    
    @Autowired
    ApplicationDAO applicationDAO;
    
    @Autowired
    ThesisDAO thesisDAO;
    
    @Autowired
    ProfServiceImpl professorService;

    
    @Test
    void testRetrieveProfile() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor professor = new Professor();
     	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        // Save the professor to the database
        professorDAO.save(professor);
        // Act
        Professor foundProfessor = professorService.retrieveProfile(user.getUsername());
        // Assert
        //assertEquals(student.getId(), foundStudent.getId());
        assertEquals(professor.getId(), foundProfessor.getId());
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    
    @Test
    void testAddSupervisedSubject() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor professor = new Professor();
    	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        professorDAO.save(professor);
        Subject subject = new Subject();  
        professorService.addSupervisedSubject(subject, professor);

        // Check if the subject is added to the professor's supervised subjects
        assertTrue(professor.getSupervisedSubjects()
                .stream()
                .anyMatch(s -> s.getId() == (subject.getId())));
        
        // Clean up
        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }

    @Test
    void testUpdateSupervisedSubject() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor professor = new Professor();
    	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        professorDAO.save(professor);
        Subject subject = new Subject();  
        subject.setName("Math");
        subject.setDescription("Mathematics subject");
        subjectDAO.save(subject);

        // Update the supervised subject
        String updatedDescription = "Updated description";
        String updatedName = "Updated name";
        professorService.updateSupervisedSubject(subject, updatedDescription, updatedName);

        // Retrieve the updated subject from the database
        Optional<Subject> retrievedSubject = subjectDAO.findById(subject.getId());

        // Check if the subject has been updated with the new values
        assertEquals(updatedDescription, retrievedSubject.get().getDescription());
        assertEquals(updatedName, retrievedSubject.get().getName());

        // Clean up
        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
 
    @Test
    void testRemoveSupervisedSubject() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor professor = new Professor();
    	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        professorDAO.save(professor);
        Subject subject = new Subject();  
        subject.setName("Math");
        subject.setDescription("Mathematics subject");
        subjectDAO.save(subject);

        // Add the subject to the professor's supervised subjects
        professor.getSupervisedSubjects().add(subject);

        // Remove the supervised subject
        professorService.removeSupervisedSubject(subject, professor);

        // Check if the subject is removed from the professor's supervised subjects
        assertFalse(professor.getSupervisedSubjects().contains(subject));

        // Clean up
        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
      
    @Test
    void testRemoveSupervisedSubjectForThesis() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor professor = new Professor();
    	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        professorDAO.save(professor);
        Subject subject = new Subject();  
        subject.setName("Math");
        subject.setDescription("Mathematics subject");
        subjectDAO.save(subject);
        
        // Add the subject to the professor's supervised subjects
        professor.getSupervisedSubjects().add(subject);

        User user2 = new User("Nick","456",Role.STUDENT);
        userDAO.save(user2);       
        Student student = new Student();
        student.setFullName("Nick");
     	student.setUserId(user2.getId());
        studentDAO.save(student);

        // Create an application for the student
        Date date = new Date();
        Application application = new Application(student , subject , date);
        application.setApplicant(student);
        applicationDAO.save(application);

        // Remove the supervised subject for the thesis
        professorService.removeSupervisedSubjectForThesis(subject, professor, student.getId());

        // Check if the subject is removed from the professor's supervised subjects
        assertFalse(professor.getSupervisedSubjects().contains(subject));

        // Check if the application for the student is deleted
        List<Application> remainingApplications = applicationDAO.findAll();
        for (Application remainingApplication : remainingApplications) {
            assertNotEquals(student.getId(), remainingApplication.getApplicant().getId());
        }

        for (Application remainingApplication : remainingApplications) {
            assertNotEquals(subject.getId(), remainingApplication.getSubject().getId());
        }
        
        // Clean up
        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
        studentDAO.delete(student);
        userDAO.delete(user2);
    }

    @Test
    void testAssignDiplomaThesisSubject() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	
        Professor professor = new Professor();
    	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        professorDAO.save(professor);
        
        Subject diplomaThesisSubject = new Subject();  
        diplomaThesisSubject.setName("Math");
        diplomaThesisSubject.setDescription("Mathematics subject");
        subjectDAO.save(diplomaThesisSubject);

        User user2 = new User("Nick","456",Role.STUDENT);
        userDAO.save(user2);       
        Student student = new Student();
        student.setFullName("Nick");
     	student.setUserId(user2.getId());
        studentDAO.save(student);


        // Assign the diploma thesis subject
        professorService.assignDiplomaThesisSubject(professor, diplomaThesisSubject.getId(), student);

        // Check if the diploma thesis subject is added to the professor's supervised thesis subjects
        assertTrue(professor.getSupervisedThesisSubjects()
                .stream()
                .anyMatch(thesis -> thesis.getThesisTitle().equals(diplomaThesisSubject.getName())));

        // Check if the student is assigned the diploma thesis subject
        assertEquals(diplomaThesisSubject.getName(), student.getAssignedSubject().getThesisTitle());
        
       // Check if the subject is deleted from the available subjects
        assertFalse(professor.getSupervisedSubjects().contains(diplomaThesisSubject));


        // Clean up
        Thesis thesis = student.getAssignedSubject();
        //System.out.println(thesis.getThesisTitle()+" with id : "+thesis.getId());
        subjectDAO.delete(diplomaThesisSubject);
        studentDAO.delete(student);
        userDAO.delete(user2);
        thesisDAO.delete(thesis);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    
    @Test
    void testCalculateOverallGrade() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	
        Professor professor = new Professor();
    	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        professorDAO.save(professor);
    	// Create a thesis
        Thesis thesis = new Thesis();
        thesis.setSupervisor(professor);
        professor.getSupervisedThesisSubjects().add(thesis);
        thesis.setImplementationGrade(90);
        thesis.setReportGrade(80);
        thesis.setPresentationGrade(85);
        thesisDAO.save(thesis);

        // Calculate the overall grade
        double overallGrade = professorService.calculateOverallGrade(thesis.getId());
        
        // Retrieve the updated thesis from the database
        Optional<Thesis> retrievedThesis = thesisDAO.findById(thesis.getId());
        
        // Check if the overall grade is calculated correctly
        assertEquals(87.75, overallGrade);
        assertEquals(87.75, retrievedThesis.get().getAverageGrade());

        // Clean up
        thesisDAO.delete(retrievedThesis.get());
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    
    @Test
    void testlistProfessorSubjects() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        //user.setId(300);
        userDAO.save(user);
    	Professor professor = new Professor();
     	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        // Save the professor to the database
        professorDAO.save(professor);
        Subject subject = new Subject();
    	subject.setSupervisor(professor);
    	subjectDAO.save(subject);
        List<Subject> subjectList = new ArrayList<>(); // Initialize subjectList
    	subjectList.add(subject) ;
        //professor.setSupervisedSubjects(subjectList);
        Subject subject1 = new Subject();
    	subject1.setSupervisor(professor);
    	subjectDAO.save(subject1);
    	subjectList.add(subject1) ;
        professor.setSupervisedSubjects(subjectList);
        // Act
        List<Subject> foundSubjects = professorService.listProfessorSubjects(user.getId());
        // Assert
        //assertEquals(student.getId(), foundStudent.getId());
        assertEquals(subjectList.size(), foundSubjects.size());
        Set<Integer> foundSubjectIds = foundSubjects.stream().map(Subject::getId).collect(Collectors.toSet());
        assertTrue(subjectList.stream().map(Subject::getId).allMatch(foundSubjectIds::contains));
        subjectDAO.delete(subject);
        subjectDAO.delete(subject1);
        professorDAO.delete(professor);
        
        userDAO.delete(user);

    }
    @Test
    void testlistProfessorThesis() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        //user.setId(300);
        userDAO.save(user);
    	Professor professor = new Professor();
     	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        // Save the professor to the database
        professorDAO.save(professor);
        Thesis thesis = new Thesis();
        thesis.setSupervisor(professor);
    	thesisDAO.save(thesis);
        List<Thesis> thesisList = new ArrayList<>(); // Initialize subjectList
        thesisList.add(thesis) ;
        //professor.setSupervisedSubjects(subjectList);
        Thesis thesis1 = new Thesis();
        thesis1.setSupervisor(professor);
        thesisDAO.save(thesis1);
        thesisList.add(thesis1) ;
        professor.setSupervisedThesisSubjects(thesisList);
        // Act
        List<Thesis> foundSubjects = professorService.listProfessorThesis(user.getId());
        // Assert
        //assertEquals(student.getId(), foundStudent.getId());
        assertEquals(thesisList.size(), foundSubjects.size());
        Set<Integer> foundSubjectIds = foundSubjects.stream().map(Thesis::getId).collect(Collectors.toSet());
        assertTrue(thesisList.stream().map(Thesis::getId).allMatch(foundSubjectIds::contains));
        thesisDAO.delete(thesis);
        thesisDAO.delete(thesis1);
        professorDAO.delete(professor);
        
        userDAO.delete(user);

    }
   
    @Test
    void testsetThesisGrades() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        //user.setId(300);
        userDAO.save(user);
    	Professor professor = new Professor();
     	professor.setFullName("John Wick");
     	professor.setUserId(user.getId());
        // Save the professor to the database
        professorDAO.save(professor);
    	Thesis thesis = new Thesis();
        thesis.setSupervisor(professor);
    	thesisDAO.save(thesis);
        List<Thesis> thesisList = new ArrayList<>(); // Initialize subjectList
        thesisList.add(thesis) ;
    	professorService.setThesisGrades(thesis, 8, 7, 5);
    	assertTrue(thesis.getImplementationGrade()==8 && thesis.getReportGrade()==7 && thesis.getPresentationGrade()==5);
    	thesisDAO.delete(thesis);

        professorDAO.delete(professor);
        
        userDAO.delete(user);
    }
}