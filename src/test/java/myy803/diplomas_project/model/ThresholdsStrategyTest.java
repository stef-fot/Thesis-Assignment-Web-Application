
package myy803.diplomas_project.model;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
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
class ThresholdsStrategyTest {

	 @Autowired
    SubjectDAO subjectDAO;
	 
	@Autowired
    ProfDAO professorDAO;

	@Autowired
    private UserDAO userDAO;
    
    @Autowired
    private StdDAO studentDAO;


    @Test
    void testcompareApplications() {
    	User user = new User("Bob","123",Role.STUDENT);
        userDAO.save(user);
        User user1 = new User("Alice","123",Role.STUDENT);
        userDAO.save(user);
        User user2 = new User("Prof","123",Role.PROFESSOR);
        userDAO.save(user);
        userDAO.save(user1);
        userDAO.save(user2);
     	Professor professor = new Professor();
     	professor.setFullName("John Wick");
     	professor.setUserId(user2.getId());
        // Save the professor to the database
        professorDAO.save(professor);
        Student student = new Student();
        student.setFullName("John Wick");
        student.setUserId(user.getId());
        student.setCurrentAverageGrade(8);
        student.setRemainingCoursesForGraduation(3);
        // Save the student to the database
        studentDAO.save(student);
        Student student1 = new Student();
        student1.setFullName("John Wick1");
        student1.setUserId(user1.getId());
        student1.setCurrentAverageGrade(6);
        student1.setRemainingCoursesForGraduation(13);
        // Save the student to the database
        studentDAO.save(student1);
    	Subject subject = new Subject();
    	subject.setSupervisor(professor);
    	subjectDAO.save(subject);
        List<Subject> subjectList = new ArrayList<>(); // Initialize subjectList
    	subjectList.add(subject) ;
        professor.setSupervisedSubjects(subjectList);
        Application app1 = new Application(student,subject);
        Application app2 = new Application(student1,subject);
        TemplateStrategyAlgorithm strategy = new ThresholdsStrategy();
        strategy.setTh1(7);
        strategy.setTh2(7);
        //ThresholdsStrategy thresholds = new ThresholdsStrategy();
        int result = strategy.compareApplications(app1, app2);
        System.out.println(result);
//        userService.saveUser(user);
//        Optional<User> opt_user = userDAO.findById(user.getId());

        // Check if the subject is added to the professor's supervised subjects
        assertTrue(result==1);

        
        subjectDAO.delete(subject);
        studentDAO.delete(student);
        studentDAO.delete(student1);
        professorDAO.delete(professor);
        
        userDAO.delete(user);
        userDAO.delete(user1);
        userDAO.delete(user2);
    }

    
}
