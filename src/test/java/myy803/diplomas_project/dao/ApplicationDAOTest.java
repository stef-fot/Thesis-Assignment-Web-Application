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
import java.util.Date;
import myy803.diplomas_project.model.*;
import myy803.diplomas_project.dao.*;
 

@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class ApplicationDAOTest {

    @Autowired
    ProfDAO professorDAO;
    
    @Autowired
    StdDAO studentDAO;
    
    @Autowired
    UserDAO userDAO;
    
    @Autowired
    ThesisDAO thesisDAO;
	
    @Autowired
    SubjectDAO subjectDAO;
    
    @Autowired
    ApplicationDAO applicationDAO;
    
    @Test
    void testFindById_existingApplication() {
    	User user = new User("Bob","123",Role.STUDENT);
        userDAO.save(user);
        
    	Student student = new Student();
    	student.setUserId(user.getId());
    	studentDAO.save(student);
    	
    	Subject subject = new Subject();
    	subjectDAO.save(subject);
    	
    	Date date = new Date();
    	
    	Application app = new Application(student , subject , date);
    	applicationDAO.save(app);
    	
    	// Act
        Application foundApp = applicationDAO.findById(app.getId()).orElse(null);
    	
        // Assert
        assertEquals(app.getId(), foundApp.getId());
        applicationDAO.delete(app);
        subjectDAO.delete(subject);;
    	studentDAO.delete(student);
        userDAO.delete(user);  
    }
    
    
    @Test
    void testFindById_nonExistingApplication() {
        // Act
        Application foundApp = applicationDAO.findById(200).orElse(null);

        // Assert
        assertNull(foundApp);
    }
}
