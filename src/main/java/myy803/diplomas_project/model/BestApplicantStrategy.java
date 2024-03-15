package myy803.diplomas_project.model;
import java.util.*;
 

public interface BestApplicantStrategy {
    Student findBestApplicant(List<Application> applications);
}

