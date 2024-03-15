package myy803.diplomas_project.model;

import java.util.List;


public class BestApplicantStrategyFactory implements BestApplicantStrategy{
	private final static BestApplicantStrategy BestAvgGradeStrategy = new BestAvgGradeStrategy();
    private final static BestApplicantStrategy FewestCoursesStrategy = new FewestCoursesStrategy();
    private final static BestApplicantStrategy RandomChoiceStrategy = new RandomChoiceStrategy();
    private final static BestApplicantStrategy ThresholdsStrategy = new ThresholdsStrategy();

	@Override
	public Student findBestApplicant(List<Application> applications) {
		// TODO Auto-generated method stub
		return null;
	}
	public static BestApplicantStrategy createStrategy(String strategy) {
		switch (strategy) {
        case "random": return RandomChoiceStrategy;
            
        case "bestAverageGrade": return BestAvgGradeStrategy;
            
        case "fewestRemainingCourses": return FewestCoursesStrategy;
            
        case "thresholds": return ThresholdsStrategy;
            
        default: return null;

	    }
	}
}	


