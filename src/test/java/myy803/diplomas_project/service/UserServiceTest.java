
package myy803.diplomas_project.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import myy803.diplomas_project.model.*;
import myy803.diplomas_project.dao.*;



@SpringBootTest
@TestPropertySource(
  locations = "classpath:application.properties")
class UserServiceTest {

	 @Autowired
    SubjectDAO subjectDAO;
	 
	@Autowired
    ProfDAO professorDAO;

	@Autowired
    private UserDAO userDAO;
    
    @Autowired
    private StdDAO studentDAO;

    @Autowired
    private UserService userService;
     
    @Autowired
    private UserServiceImpl userServiceImpl;


    @Test
    void testSaveUser() {
        User user = new User("Bob", "123", Role.PROFESSOR);

        userService.saveUser(user);
        Optional<User> opt_user = userDAO.findById(user.getId());

        // Check if the subject is added to the professor's supervised subjects
        assertEquals(user.getId(),opt_user.get().getId());

        userDAO.delete(user);
    }
    @Test
    void testisUserPresent() {
        User user = new User("Bob", "123", Role.PROFESSOR);

        userDAO.save(user);

        assertTrue(userService.isUserPresent(user));

        userDAO.delete(user);
    }
   @Test
    void testfindById() {
        User user = new User("Bob", "123", Role.PROFESSOR);

        userDAO.save(user);
        Optional<User> user1 =userServiceImpl.findById(user.getId());
        assertEquals(user.getId(),user1.get().getId());

        userDAO.delete(user);
    }
}
