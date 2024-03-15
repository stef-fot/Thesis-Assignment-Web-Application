package myy803.diplomas_project.model;

import java.util.List;
import java.util.Random;

public class FewestCoursesStrategy extends TemplateStrategyAlgorithm{

	public FewestCoursesStrategy() {

	}

	@Override
	public int compareApplications(Application app1, Application app2) {

		double fewestCourses1 = app1.getApplicant().getRemainingCoursesForGraduation();
		double fewestCourses2 = app2.getApplicant().getRemainingCoursesForGraduation();

        if (fewestCourses1 < fewestCourses2) {
            return 1;
        } else if (fewestCourses1 > fewestCourses2) {
            return -1;
        } else {
        	Random random = new Random();
	    	System.out.println("random case");
            return random.nextInt(2) == 0 ? -1 : 1;
            //return 0;
        }
	}

	

}
