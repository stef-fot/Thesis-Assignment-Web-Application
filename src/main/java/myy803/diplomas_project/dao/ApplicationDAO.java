package myy803.diplomas_project.dao;

import java.util.ArrayList;
import java.util.List;
import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.diplomas_project.model.Application;
import java.util.Optional;

@Repository
public interface ApplicationDAO extends JpaRepository<Application, Integer> {
	Optional<Application> findById(int id);
	
}
