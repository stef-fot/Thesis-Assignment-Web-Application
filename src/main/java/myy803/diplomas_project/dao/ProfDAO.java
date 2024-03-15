package myy803.diplomas_project.dao;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.diplomas_project.model.Professor;
import myy803.diplomas_project.model.User;

@Repository
public interface ProfDAO extends JpaRepository<Professor, Integer> {
    
    List<Professor> findAll();
		
	Optional<Professor> findById(Integer id);
	
	Optional<Professor> findByUserId(Integer user_id);

}
