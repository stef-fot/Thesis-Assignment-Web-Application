package myy803.diplomas_project.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Assertions;

import myy803.diplomas_project.model.*;
import myy803.diplomas_project.dao.*;
 

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class ProfessorDAOTest {

    @Autowired
    ProfDAO professorDAO;
    
    @Autowired
    UserDAO userDAO;
	
   	
    @Test
	void testUserDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(userDAO);
	}

    @Test
    void testFindByUserId_existingProfessor() {
    	
        User user = new User("Bob","123",Role.PROFESSOR);
        userDAO.save(user);
    	Professor professor = new Professor();
    	professor.setFullName("John Wick");
    	professor.setUserId(user.getId());
        // Save the professor to the database
        professorDAO.save(professor);

        // Act
        Professor foundProfessor = professorDAO.findByUserId(user.getId()).orElse(null);

        // Assert
        assertEquals(professor.getFullName(), foundProfessor.getFullName());
        professorDAO.delete(professor);
        userDAO.delete(user);
    }
    @Test
    void testFindById_existingProfessor() {
    	
    	 User user = new User("Bob","123",Role.PROFESSOR);
         userDAO.save(user);
     	 Professor professor = new Professor();
     	 professor.setFullName("John Wick");
     	 professor.setUserId(user.getId());
         // Save the professor to the database
         professorDAO.save(professor);

         // Act
         Professor foundProfessor = professorDAO.findById(professor.getId()).orElse(null);

         // Assert
         assertEquals(professor.getFullName(), foundProfessor.getFullName());
         professorDAO.delete(professor);
         userDAO.delete(user);
    }
    @Test
    void testFindById_nonExistingProfessor() {
        // Act
        Professor foundProfessor = professorDAO.findById(200).orElse(null);

        // Assert
        assertNull(foundProfessor);
    }
}
