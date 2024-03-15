package myy803.diplomas_project.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

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
class StudentDAOTest {

    @Autowired
    StdDAO studentDAO;
    
    @Autowired
    UserDAO userDAO;
    
    @Test
	void testUserDAOJpaImplIsNotNull() {
		Assertions.assertNotNull(userDAO);
	}

    @Test
    void testFindByUserId_existingStudent() {
    	
        User user = new User("Bob","123",Role.STUDENT);
        //user.setId(300);
        userDAO.save(user);
    	Student student = new Student();
        student.setFullName("John Wick");
        student.setUserId(user.getId());
        // Save the student to the database
        studentDAO.save(student);

        // Act
        Student foundStudent = studentDAO.findByUserId(user.getId()).orElse(null);

        // Assert
        //assertEquals(student.getId(), foundStudent.getId());
        assertEquals(student.getFullName(), foundStudent.getFullName());
        studentDAO.delete(student);
        userDAO.delete(user);
        //assertEquals(student.getAge(), foundStudent.getAge());
    }
    @Test
    void testFindById_existingStudent() {
    	
        User user = new User("Bob","123",Role.STUDENT);
        //user.setId(300);
        userDAO.save(user);
    	Student student = new Student();
        student.setFullName("John Wick");
        student.setUserId(user.getId());
        // Save the student to the database
        studentDAO.save(student);

        // Act
        Student foundStudent = studentDAO.findById(student.getId()).orElse(null);

        // Assert
        assertEquals(student.getId(), foundStudent.getId());
        //assertEquals(student.getFullName(), foundStudent.getFullName());
        studentDAO.delete(student);
        userDAO.delete(user);
        //assertEquals(student.getAge(), foundStudent.getAge());
    }
    @Test
    void testFindById_nonExistingStudent() {
        // Act
        Student foundStudent = studentDAO.findById(200).orElse(null);

        // Assert
        assertNull(foundStudent);
    }
}
