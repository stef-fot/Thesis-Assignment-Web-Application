package myy803.diplomas_project.model;
import java.util.Random;

public class ThresholdsStrategy extends TemplateStrategyAlgorithm{

	public ThresholdsStrategy() {
	}

	@Override
	public int compareApplications(Application app1, Application app2) {
	    double app1AvgGrade = app1.getApplicant().getCurrentAverageGrade();
	    int app1RemainingCourses = app1.getApplicant().getRemainingCoursesForGraduation();

	    double app2AvgGrade = app2.getApplicant().getCurrentAverageGrade();
	    int app2RemainingCourses = app2.getApplicant().getRemainingCoursesForGraduation();
    	System.out.println(app1AvgGrade + " " + app1RemainingCourses + " " + app2AvgGrade + " " + app2RemainingCourses);
	    if (app1AvgGrade > getTh1() && app1RemainingCourses < getTh2()) {
	        if (app2AvgGrade > getTh1() && app2RemainingCourses < getTh2()) {
	            // Both applications satisfy the thresholds, so randomly pick one
	            Random random = new Random();
		    	System.out.println("random case");
	            return random.nextInt(2) == 0 ? -1 : 1;
	        } else {
		    	System.out.println("App1 eligible case");
	            // Only app1 satisfies the thresholds, so it's considered better
	            return 1;
	        }
	    } else if (app2AvgGrade > getTh1() && app2RemainingCourses < getTh2()) {
	        // Only app2 satisfies the thresholds, so it's considered better
	    	System.out.println("App2 eligible case");
	    	//System.out.println("TH1 : "+ getTh1() + " , TH2 : "+getTh2());
	        return -1;
	    } else {
	        // Both applications don't satisfy the thresholds, so they are considered equal\
	    	System.out.println("No eligible case");
	    	System.out.println("TH1 : "+ getTh1() + " , TH2 : " +getTh2());
	    	
	    	//return null;
	        return 0;
	    }
	}
	
}
