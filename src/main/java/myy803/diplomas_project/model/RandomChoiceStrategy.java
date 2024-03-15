package myy803.diplomas_project.model;
import java.util.Random;

public class RandomChoiceStrategy extends TemplateStrategyAlgorithm{

	public RandomChoiceStrategy() {

	}

	@Override
	public int compareApplications(Application app1, Application app2) {
		 Random random = new Random();
	        int randomValue = random.nextInt(2); // Generates a random number between 0 and 1

	        if (randomValue == 0) {
	            return -1; // Return -1 to indicate app1 is better
	        } else {
	            return 1; // Return 1 to indicate app2 is better
	        }
	}

}
