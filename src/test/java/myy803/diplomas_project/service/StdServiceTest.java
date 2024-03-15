package myy803.diplomas_project.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.List;

import myy803.diplomas_project.model.*;
import myy803.diplomas_project.dao.*;
import myy803.diplomas_project.service.*;
import myy803.diplomas_project.service.StdServiceImpl.SubjectDetails;

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class StdServiceTest {

	 @Autowired
    SubjectDAO subjectDAO;
	 
	@Autowired
    ProfDAO professorDAO;

	@Autowired
    private UserDAO userDAO;
    
    @Autowired
    private StdDAO studentDAO;

    @Autowired
    private StdService studentService;

    
    @Test
    void testRetrieveProfile() {
    	User user = new User("Bob","123",Role.STUDENT);
        //user.setId(300);
        userDAO.save(user);
    	Student student = new Student();
        student.setFullName("John Wick");
        student.setUserId(user.getId());
        // Save the student to the database
        studentDAO.save(student);
        // Act
        Student foundStudent = studentService.retrieveProfile(user.getUsername());
        // Assert
        //assertEquals(student.getId(), foundStudent.getId());
        assertEquals(student.getId(), foundStudent.getId());
        studentDAO.delete(student);
        userDAO.delete(user);
    }
    @Test
    void testSubjectDetails() { 
    	User user = new User("Bob","123",Role.PROFESSOR);
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
        professor.setSupervisedSubjects(subjectList);
        //professorDAO.save(professor);
    	//subjectDAO.save(subject);
        SubjectDetails subd = new SubjectDetails(subject.getName(), subject.getDescription(), professor.getFullName());
        // Act
        SubjectDetails foundSubjectDetails = studentService.getSubjectDetails(subject.getId());

        // Assert
        assertEquals(subd.getTitle(),foundSubjectDetails.getTitle());
        
        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    
    @Test
    void testgetAvailableDiplomaThesisSubjects() { 
    	User user = new User("Bob","123",Role.PROFESSOR);
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
        professor.setSupervisedSubjects(subjectList);
        
        User user1 = new User("Bob1","123",Role.PROFESSOR);
        userDAO.save(user1);
     	Professor professor1 = new Professor();
     	professor1.setFullName("John Wick");
     	professor1.setUserId(user1.getId());
        // Save the professor to the database
        professorDAO.save(professor1);

    	Subject subject1 = new Subject();
    	subject1.setSupervisor(professor1);
    	subjectDAO.save(subject1);
        List<Subject> subjectList1 = new ArrayList<>(); // Initialize subjectList
        subjectList1.add(subject1) ;
        professor1.setSupervisedSubjects(subjectList1);
        //professorDAO.save(professor);
    	//subjectDAO.save(subject);

        subjectList.addAll(subjectList1);

        // Act
        List <Subject> foundSubjects = studentService.getAvailableDiplomaThesisSubjects();
//        System.out.println(subjectList.size()+ foundSubjects.size());
//        System.out.println(foundSubjects.get(0));
//        System.out.println(subjectList.get(0));
        // Assert
  
        //System.out.println(subjectList.size() + " : "  + foundSubjects.size());
        assertTrue(foundSubjects.size() >= subjectList.size());
        Set<Integer> foundSubjectIds = foundSubjects.stream().map(Subject::getId).collect(Collectors.toSet());
        assertTrue(subjectList.stream().map(Subject::getId).allMatch(foundSubjectIds::contains));

        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
        subjectDAO.delete(subject1);
        professorDAO.delete(professor1);
        userDAO.delete(user1);
    }
    
    @Test
    void testlistStudentSubjects() { 
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
        Student std = new Student();
        std.setFullName("");
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
        professor.setSupervisedSubjects(subjectList);
        
       
        // Act
        List <Subject> foundSubjects = studentService.listStudentSubjects();
//        System.out.println(subjectList.size()+ foundSubjects.size());
//        System.out.println(foundSubjects.get(0));
//        System.out.println(subjectList.get(0));
        // Assert
        assertTrue(foundSubjects.size() >= subjectList.size());

        Set<Integer> foundSubjectIds = foundSubjects.stream().map(Subject::getId).collect(Collectors.toSet());
        assertTrue(subjectList.stream().map(Subject::getId).allMatch(foundSubjectIds::contains));

        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
   
    
}
