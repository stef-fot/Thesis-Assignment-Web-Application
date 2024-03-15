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
class ThesisDAOTest {

    @Autowired
    ProfDAO professorDAO;
    
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    ThesisDAO thesisDAO;
	

    @Test
    void testFindById_existingThesis() {
    	User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor prof = new Professor();
    	prof.setUserId(user.getId());
    	professorDAO.save(prof);
    	Thesis thesis = new Thesis(prof);
    	thesisDAO.save(thesis);
    	
    	// Act
        Thesis foundThesis = thesisDAO.findById(thesis.getId()).orElse(null);
    	
        // Assert
        assertEquals(thesis.getId(), foundThesis.getId());
        thesisDAO.delete(thesis);
    	professorDAO.delete(prof);
        userDAO.delete(user);

        
    }
    
    
    @SuppressWarnings("null")
	@Test
    void testFindBySupervisorId_existingThesis() { 
    	User user = new User("Bob","123",Role.PROFESSOR);
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
        professor.setSupervisedThesisSubjects(thesisList);
        //professorDAO.save(professor);
    	//subjectDAO.save(subject);
    	
        // Act
        List <Thesis> foundThesis = thesisDAO.findBySupervisorId(professor.getId());
    
        // Assert
        assertEquals(thesisList.size(), foundThesis.size());
        Set<Integer> foundThesisIds = foundThesis.stream().map(Thesis::getId).collect(Collectors.toSet());
        assertTrue(thesisList.stream().map(Thesis::getId).allMatch(foundThesisIds::contains));

        thesisDAO.delete(thesis);
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    
    @Test
    void testFindById_nonExistingThesis() {
        // Act
        Thesis foundThesis = thesisDAO.findById(200).orElse(null);

        // Assert
        assertNull(foundThesis);
    }
}
