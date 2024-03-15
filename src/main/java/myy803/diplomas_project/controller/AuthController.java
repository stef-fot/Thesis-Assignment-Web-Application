package myy803.diplomas_project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import myy803.diplomas_project.model.User;
import myy803.diplomas_project.service.UserService;
import myy803.diplomas_project.service.ProfService;
import myy803.diplomas_project.service.StdService;
import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Role;
import myy803.diplomas_project.model.Student;


@Controller
public class AuthController {
	@Autowired
	@Qualifier("userServiceImpl")
    UserService userService;
	
	@Autowired
	private StdService stdService;

	@Autowired
	private ProfService profService;
	
    public AuthController(UserService userServiceMock, StdService stdServiceMock) {
	}

	@RequestMapping("/login")
    public String login(){
        return "auth/signin";
    }

    @RequestMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "auth/signup";
    }

    @RequestMapping("/save")
    public String registerUser(@ModelAttribute("user") User user, Model model){
        System.out.println("We have to go here " +user.getId());
        if(userService.isUserPresent(user)){
            model.addAttribute("successMessage", "User already registered!");
            return "auth/signin";
        }
        //System.out.println("We have to go here " +user.getId());
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully!");
        System.out.println("Please go here");
        if(user.getRole() == Role.STUDENT){
        	Student student = new Student();
        	student.setUserId(user.getId());
        	stdService.saveStudent(student);

        	model.addAttribute("successMessage", "Student registered successfully!");
        	//return "student/editStdProfile";

        } else if(user.getRole() == Role.PROFESSOR){
        	Professor professor = new Professor();
        	professor.setUserId(user.getId());
        	profService.saveProfessor(professor);

        	model.addAttribute("successMessage", "Professor registered successfully!");
        	//return "professor/editProfProfile";
        }
        return "auth/signin";
    }
}


