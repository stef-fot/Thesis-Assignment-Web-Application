package myy803.diplomas_project.dao;

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
 

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class SubjectDAOTest {

    @Autowired
    ProfDAO professorDAO;
    
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    SubjectDAO subjectDAO;
	

    @Test
    void testFindById_existingSubject() {    
    	Subject subject = new Subject();
    	subjectDAO.save(subject);
    	//subject.setId(300);
    	// Act
        Subject foundSubject = subjectDAO.findById(subject.getId()).orElse(null);
    	// Assert
        assertEquals(subject.getId(), foundSubject.getId());
        subjectDAO.delete(subject);
        
    }
    
    
    @SuppressWarnings("null")
	@Test
    void testFindBySupervisorId_existingSubject() { 
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
    	
        // Act
        List <Subject> foundSubjects = subjectDAO.findBySupervisorId(professor.getId());
    
        // Assert
        assertEquals(subjectList.size(), foundSubjects.size());
        Set<Integer> foundSubjectIds = foundSubjects.stream().map(Subject::getId).collect(Collectors.toSet());
        assertTrue(subjectList.stream().map(Subject::getId).allMatch(foundSubjectIds::contains));

        subjectDAO.delete(subject);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    
    @Test
    void testFindById_nonExistingSubject() {
        // Act
        Subject foundSubject = subjectDAO.findById(200).orElse(null);

        // Assert
        assertNull(foundSubject);
    }
}
