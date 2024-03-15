package myy803.diplomas_project.model;

import java.util.List;

public abstract class TemplateStrategyAlgorithm implements BestApplicantStrategy{
	private double Th1;//threshold for avgGrade
	private int Th2;//threshold for remainingCourses
	public TemplateStrategyAlgorithm() {

	}
	
	@Override
	public Student findBestApplicant(List<Application> applications) {
	    if (applications == null || applications.isEmpty()) {
	        return null; // No applications available
	    }

	    // Initialize the best applicant as the first application in the list
	    Application bestApplication = applications.get(0);

	    // Iterate through the remaining applications and compare them
	    for (int i = 1; i < applications.size(); i++) {
	        Application currentApplication = applications.get(i);
	        int comparisonResult = compareApplications(bestApplication, currentApplication);

	        if (comparisonResult < 0) {
	            // If the current application is considered better and the applicant doesn't have an assigned subject
	            if (currentApplication.getApplicant().getAssignedSubject() == null) {
	                bestApplication = currentApplication;
	            }
	        }else if (comparisonResult == 0) {
	        	System.out.println("No eligable students.");
		        return null;
	        }
	    }

	    if (bestApplication.getApplicant().getAssignedSubject() != null) {
	        System.out.println("Best applicant already has an assigned subject.");
	        return null;
	    }

	    System.out.println("In the TemplateStrategyAlgorithm, student is " + bestApplication.getApplicant().getFullName());

	    // Return the best applicant
	    return bestApplication.getApplicant();
	}

	
	public abstract int compareApplications(Application app1, Application app2);
	
	 public double getTh1() {
	        return Th1;
	    }
	
	    public void setTh1(double Th1) {
	        this.Th1 = Th1;
	    }
	
	    public int getTh2() {
	        return Th2;
	    }
	
	    public void setTh2(int Th2) {
	        this.Th2 = Th2;
	    }
}
