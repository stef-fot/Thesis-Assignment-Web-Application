package myy803.diplomas_project.controller;

import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.context.SecurityContextHolder;

import myy803.diplomas_project.dao.SubjectDAO;
import myy803.diplomas_project.dao.ThesisDAO;
import myy803.diplomas_project.dao.ProfDAO;
import myy803.diplomas_project.dao.UserDAO;
import myy803.diplomas_project.model.Application;
import myy803.diplomas_project.model.BestApplicantStrategyFactory;
import myy803.diplomas_project.model.BestAvgGradeStrategy;
import myy803.diplomas_project.model.FewestCoursesStrategy;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.RandomChoiceStrategy;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.TemplateStrategyAlgorithm;
import myy803.diplomas_project.model.Thesis;
import myy803.diplomas_project.model.ThresholdsStrategy;
import myy803.diplomas_project.service.ProfService;
import myy803.diplomas_project.model.User;



@Controller
@RequestMapping("/professor")
public class ProfController {

	@Autowired
    private ProfService profService;

	@Autowired
    private SubjectDAO subjectDAO;
	
	@Autowired
	private ProfDAO profdao;
	
	@Autowired
	private UserDAO userdao;
	
	@Autowired
	private ThesisDAO thesisdao;
		
    public ProfController(ProfService theProfService) {
        profService = theProfService;
    }
    
    @RequestMapping("/homepage")
    public String getProfessorHomepage() {
        return "homepage";
    }
    @RequestMapping("/dashboard")
    public String getProfessorDashboard(Authentication authentication, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        List<Subject> subjects = profService.listProfessorSubjects(user_id);
        model.addAttribute("subjects", subjects);
        return "professor/dashboard";
    }
    @RequestMapping("/profile")
    public String getProfessorProfile(Authentication authentication, Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	Professor professor = profService.retrieveProfile(username);
        model.addAttribute("professor", professor);
        return "professor/info";
    }
    @RequestMapping("/subjects")
    public String getProfessorSubjects(Authentication authentication, Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
    	Professor professor = profService.retrieveProfile(username);
        model.addAttribute("professor", professor);
        return "professor/supervisedSubjects";
    }
    @RequestMapping("/save")
    public String saveProfessor(@ModelAttribute("professor") Professor theProfessor, Model theModel) {
    	profService.saveProfessor(theProfessor);
    	return "redirect:/professor/dashboard";
    }
    @RequestMapping("/addSubject")
    public String addSupervisedSubject(@ModelAttribute("subject") Subject subject,Authentication authentication, Model theModel) {
        return "professor/addSupervisedSubjectForm";
    }
    @RequestMapping("/addSavedSubject")
    public String addSupervisedSubjectTest(@ModelAttribute("subject") Subject subject,Authentication authentication, Model theModel) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Professor> theProfessor = profdao.findByUserId(user_id);
        theModel.addAttribute("professor", theProfessor.get());
        subject.setSupervisor(theProfessor.get());
    	theModel.addAttribute("successMessage", "Subject added successfully!");
    	profService.addSupervisedSubject(subject,theProfessor.get());
        return "redirect:/professor/dashboard";
    }
    @RequestMapping("/updateProfile")
	public String updateProfile(Authentication authentication, Model theModel) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Professor> theProfessor = profdao.findByUserId(user_id);
		theProfessor.get().getFullName();
		if (theProfessor.get().getFullName() == null) {
	        theModel.addAttribute("professor", theProfessor.get());
			return "professor/editProfProfile";			
		}else {
			return "redirect:/professor/dashboard";
		}
	}
    @RequestMapping("/editProfile")
	public String editProfile(Authentication authentication, Model theModel) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Professor> theProfessor = profdao.findByUserId(user_id);
		theProfessor.get().getFullName();
        theModel.addAttribute("professor", theProfessor.get());
		return "professor/editProfProfile";			
	}
    @RequestMapping("/list")
    public String listProfessorSubjects(Authentication authentication, Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Professor> theProfessor = profdao.findByUserId(user_id);
        List<Subject> subjects = profService.listProfessorSubjects(user_id);
        model.addAttribute("subjects", subjects);
        return "redirect:/professor/dashboard";
    }
    @RequestMapping("/listSubjects")
    public String listProfessorSupervisedSubjects(Authentication authentication, Model model) {
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Professor> theProfessor = profdao.findByUserId(user_id);
        List<Subject> subjects = profService.listProfessorSubjects(user_id);
        model.addAttribute("subjects", subjects);
        return "professor/supervisedSubjects";
    }
    @RequestMapping("/updateSubject")
    public String updateSupervisedSubject(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
        Optional <Subject> subject = subjectDAO.findById(subjectId);
    	theModel.addAttribute("subject", subject.get());
    	return "professor/updateSupervisedSubjectForm";
    }
   @RequestMapping("/updateSavedSubject")
    public String updateSupervisedSubjectTest(@ModelAttribute("subject") Subject subject, Authentication authentication, Model theModel) {
    	String description = subject.getDescription();
    	String name = subject.getName();
        profService.updateSupervisedSubject(subject,description,name);
        return "redirect:/professor/dashboard";
    }
   @RequestMapping("/removeSubject")
    public String removeSupervisedSubject(@RequestParam("subject_id") int subjectId , Authentication authentication, Model theModel) {
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional <User> user = userdao.findByUsername(username);
        int user_id = user.get().getId();
        Optional<Professor> theProfessor = profdao.findByUserId(user_id);
        Optional <Subject> subject = subjectDAO.findById(subjectId);
    	theModel.addAttribute("subject", subject.get());
   		profService.removeSupervisedSubject(subject.get() , theProfessor.get());
   		return "redirect:/professor/dashboard";
   }
   @RequestMapping("/assign")
   public String assignSubject(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
       Optional <Subject> subject = subjectDAO.findById(subjectId);
	   theModel.addAttribute("subject", subject.get());
	   theModel.addAttribute("subjectId", subjectId);
	   return "professor/assignSubjectForm";
   }
   @RequestMapping("/applications")
   public String listApplications(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
	   String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   Optional<User> user = userdao.findByUsername(username);
	   int user_id = user.get().getId();
	   Optional<Professor> theProfessor = profdao.findByUserId(user_id);
	   List<Application> applications = profService.listApplications(theProfessor.get().getId(), subjectId);
	   theModel.addAttribute("applications", applications);
	   theModel.addAttribute("subjectId", subjectId);
       return "professor/applicationsList";
   }
   @RequestMapping("/random")  
   public String assignRandomSubject(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   Optional<User> user = userdao.findByUsername(username);
	   int user_id = user.get().getId();
	   Optional<Professor> theProfessor = profdao.findByUserId(user_id);
	   List<Application> applications = profService.listApplications(theProfessor.get().getId(), subjectId);
       //String strategyName = "random"; 
       TemplateStrategyAlgorithm strategy = new RandomChoiceStrategy();
       Student student = strategy.findBestApplicant(applications);
       profService.assignDiplomaThesisSubject(theProfessor.get(), subjectId , student );
	   return "redirect:/professor/dashboard";
   }
   @RequestMapping("/bestAvgGrade")  
   public String assignbestAvgGradeSubject(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   Optional<User> user = userdao.findByUsername(username);
	   int user_id = user.get().getId();
	   Optional<Professor> theProfessor = profdao.findByUserId(user_id);
	   List<Application> applications = profService.listApplications(theProfessor.get().getId(), subjectId);
       //String strategyName = "bestAvgGrade"; 
       TemplateStrategyAlgorithm strategy = new BestAvgGradeStrategy();
       Student student = strategy.findBestApplicant(applications);
       profService.assignDiplomaThesisSubject(theProfessor.get(), subjectId , student );
	   return "redirect:/professor/dashboard";
   }
   @RequestMapping("/fewestCourses")  
   public String assignfewestCoursesSubject(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
       String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   Optional<User> user = userdao.findByUsername(username);
	   int user_id = user.get().getId();
	   Optional<Professor> theProfessor = profdao.findByUserId(user_id);
	   List<Application> applications = profService.listApplications(theProfessor.get().getId(), subjectId);
       //String strategyName = "bestAvgGrade"; 
       TemplateStrategyAlgorithm strategy = new FewestCoursesStrategy();
       Student student = strategy.findBestApplicant(applications);
       profService.assignDiplomaThesisSubject(theProfessor.get(), subjectId , student );
	   return "redirect:/professor/dashboard";
   }
   @RequestMapping("/thresholds")  
   public String addThresholds(@RequestParam("subject_id") int subjectId, Authentication authentication, Model theModel) {
       theModel.addAttribute("subjectId", subjectId);
       return "professor/thresholdsForm";
   }
   @RequestMapping("/assignThresholds")  
   public String assignThresholdSubject(@RequestParam("subject_id") int subjectId,@RequestParam("Th1") double Th1,@RequestParam("Th2") int Th2, Authentication authentication, Model theModel) {
	   String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   Optional<User> user = userdao.findByUsername(username);
	   int user_id = user.get().getId();
	   Optional<Professor> theProfessor = profdao.findByUserId(user_id);
	   List<Application> applications = profService.listApplications(theProfessor.get().getId(), subjectId);
       //String strategyName = "bestAvgGrade"; 
       TemplateStrategyAlgorithm strategy = new ThresholdsStrategy();
       strategy.setTh1(Th1);
       strategy.setTh2(Th2);
       Student student = strategy.findBestApplicant(applications);
       
       profService.assignDiplomaThesisSubject(theProfessor.get(), subjectId , student );
       
	   return "redirect:/professor/dashboard";
   }
   @RequestMapping("/checkThesis")  
   public String listThesis(/*@RequestParam("thesis_id") int thesisId, */Authentication authentication, Model theModel) {
	   String username = SecurityContextHolder.getContext().getAuthentication().getName();
	   Optional<User> user = userdao.findByUsername(username);
	   int user_id = user.get().getId();
	   Optional<Professor> theProfessor = profdao.findByUserId(user_id);
	   List<Thesis> thesis = profService.listProfessorThesis(user_id);
	   theModel.addAttribute("thesis", thesis);
       return "professor/supervisedThesis";
   }
   @RequestMapping("/setThesisGrades")  
   public String setGrades(@RequestParam("thesisId") int thesisId, Authentication authentication, Model theModel) {
	   theModel.addAttribute("thesisId", thesisId);
       return "professor/setGradesForm";
   }
   @RequestMapping("/assignThesisGrades")  
   public String assignGradesForThesis(@RequestParam("thesisId") int thesisId,@RequestParam("implementationGrade") double implementationGrade,@RequestParam("reportGrade") double reportGrade,
		   @RequestParam("presentationGrade") double presentationGrade, Authentication authentication, Model theModel) {

	   Optional <Thesis> thesis = thesisdao.findById(thesisId);

	   profService.setThesisGrades(thesis.get(), implementationGrade, reportGrade, presentationGrade);
	   Professor prof = thesis.get().getSupervisor();	   
	   List<Thesis> thesisList = profService.listProfessorThesis(prof.getUserId());
	   theModel.addAttribute("thesis", thesisList);
	   return "professor/supervisedThesis";

   }
   @RequestMapping("/calculate")  
   public String calculateThesisGrade(@RequestParam("thesisId") int thesisId, Authentication authentication, Model theModel) {
	   profService.calculateOverallGrade(thesisId);
	   Optional<Thesis> thesis = thesisdao.findById(thesisId);
	   Professor prof = thesis.get().getSupervisor();	   
	   List<Thesis> thesisList = profService.listProfessorThesis(prof.getUserId());
	   theModel.addAttribute("thesis", thesisList);
       return "professor/supervisedThesis";
   }
    /*
    @RequestMapping("/thesis")
    public String listProfessorThesis(@RequestParam("prof_user_name") String professorUsername, Model model) {
        List<Thesis> thesisList = profService.listProfessorThesis(professorUsername);
        model.addAttribute("thesisList", thesisList);
        return "professor/thesis";
    }
    
    @RequestMapping("/assign-thesis")
    public String assignDiplomaThesisSubject(@ModelAttribute("applications") List<Application> applications,
            @RequestParam("subject_id") int subjectID, @RequestParam("strategy") String strategy,
            @RequestParam("threshold1") double threshold1, @RequestParam("threshold2") int threshold2) {
        profService.assignDiplomaThesisSubject(applications, subjectID, strategy, threshold1, threshold2);
        return "redirect:/professor/applications";
    }*/
    
   
    
    /*@RequestMapping("/suggest-student")
    public String suggestStudentForThesis(Model model) {
        List<Student> students = profService.getStudentsWithApplications();
        //Student bestCandidate = profService.getStudentWithFewestRemainingCourses(students);
        model.addAttribute("studentsWithApplication", students);
        return "professor/students-application";
    }

    @RequestMapping("/assign-subject")
    public String assignDiplomaThesisSubject(@RequestParam("prof_user_name") String username,
                                              @RequestParam("subject_id") int subjectID,
                                              @RequestParam("strategy") String strategy,
                                              @RequestParam(value = "threshold1", defaultValue = "0.0") double threshold1,
                                              @RequestParam(value = "threshold2", defaultValue = "0") int threshold2) {
        List<Application> applications = profService.listApplications(username, subjectID);
        profService.assignDiplomaThesisSubject(applications, subjectID, strategy, threshold1, threshold2);
        return "redirect:/professor/applications?prof_user_name=" + username;
    }*/
    
}