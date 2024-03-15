package myy803.diplomas_project.controller;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.User;
import myy803.diplomas_project.service.StdService;
import myy803.diplomas_project.service.StdServiceImpl.SubjectDetails;
import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.StdDAO;
import myy803.diplomas_project.dao.SubjectDAO;
import myy803.diplomas_project.dao.UserDAO;


@Controller
@RequestMapping("/student")
public class StdController {
	
	@Autowired
	private StdService stdService;
	
	@Autowired
	private SubjectDAO subjectDAO;
	
	@Autowired
	private StdDAO stddao;
	
	@Autowired
	private UserDAO userdao;
	
	public StdController(StdService theStudentService) {
		stdService = theStudentService;
	}

	@RequestMapping("/dashboard")
    public String getStudentDashboard(Authentication authentication, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        List<Subject> subjects = stdService.listStudentSubjects();
        model.addAttribute("subjects", subjects);
        return "student/dashboard";
    }
    @RequestMapping("/homepage")
    public String getStudentHomepage() {
        return "homepage";
    }
	@RequestMapping("/profile")
	public String getStudentProfile(Authentication authentication, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Student student = stdService.retrieveProfile(username);
        //System.out.println("In the controller");
        //System.out.println(username);
        model.addAttribute("student", student);
        return "student/info";
    }
	@RequestMapping("/editProfile")
	//public String editProfile(Authentication authentication, Model theModel) {
	public String editProfile(Authentication authentication, Model theModel) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Student> theStudent = stddao.findByUserId(user_id);
        theStudent.get().getFullName();
        theModel.addAttribute("student", theStudent.get());
		return "student/editStdProfile";			
	}
	@RequestMapping("/save")
    public String saveStudent(@ModelAttribute("student") Student theStudent, Model theModel) {
        stdService.saveStudent(theStudent);
        //System.out.println("In the save phase ");
		//System.out.println(theStudent.getId() +" " + theStudent.getUserId());
        return "redirect:/student/dashboard";
    }
	@RequestMapping("/list")
    public String listStudentSubjects(Authentication authentication, Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Student> theStudent = stddao.findByUserId(user_id);
        List<Subject> subjects = stdService.getAvailableDiplomaThesisSubjects();
        model.addAttribute("subjects", subjects);
        return "redirect:/student/dashboard";
    }
    @RequestMapping("/updateProfile")
    public String updateProfile(Authentication authentication, Model theModel) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Student> theStudent = stddao.findByUserId(user_id);
		theStudent.get().getFullName();
		//theProfessor.get().setUserId(user_id);
		if (theStudent.get().getFullName() == null) {
	        theModel.addAttribute("student", theStudent.get());
			return "student/editStdProfile";			
		}else {
			return "redirect:/student/dashboard";
		}
	}
	@RequestMapping("/apply")
	public String applyForSubject(@RequestParam("subject_id") int subjectId , Authentication authentication, Model theModel) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Student> theStudent = stddao.findByUserId(user_id);
		Optional <Subject> subject = subjectDAO.findById(subjectId);
		stdService.applyForSubject(subject.get(), theStudent.get());
		return "redirect:/student/dashboard";
	}  
	@RequestMapping("/details")
	public String getSubjectDetails(@RequestParam("subject_id") int subjectId, Model model) {
	    System.out.println("Subject id: " + subjectId);
	    SubjectDetails subjectDetails = stdService.getSubjectDetails(subjectId);
	    model.addAttribute("subjectDetails", subjectDetails);
	    return "student/subjectDetails";
	}
    /*
	@RequestMapping("/subjects")
    public String listAvailableSubjects(Model model) {
        List<Subject> subjects = stdService.getAvailableDiplomaThesisSubjects();
        model.addAttribute("subjects", subjects);
        return "student/subjects";
    }
    */

}

