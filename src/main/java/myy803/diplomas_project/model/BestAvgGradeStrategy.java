package myy803.diplomas_project.model;

import java.util.List;
import java.util.Random;

public class BestAvgGradeStrategy extends TemplateStrategyAlgorithm{

	public BestAvgGradeStrategy() {

	}

	@Override
    public int compareApplications(Application app1, Application app2) {
		double avgGrade1 = app1.getApplicant().getCurrentAverageGrade();
		double avgGrade2 = app2.getApplicant().getCurrentAverageGrade();

        if (avgGrade1 > avgGrade2) {
            return 1;
        } else if (avgGrade1 < avgGrade2) {
            return -1;
        } else {
        	Random random = new Random();
	    	System.out.println("random case");
            return random.nextInt(2) == 0 ? -1 : 1;
        }
    }


}
