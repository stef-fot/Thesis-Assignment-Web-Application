package myy803.diplomas_project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.User;

@Repository
public interface StdDAO extends JpaRepository<Student, Integer> {
	
	List<Student> findAll();
	
	Optional<Student> findByUserId(Integer user_id);

}
