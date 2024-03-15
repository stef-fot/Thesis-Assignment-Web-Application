package myy803.diplomas_project.dao;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.diplomas_project.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	
	Optional<User> findById(Integer id);
	Optional<User> findByUsername(String username);
	
}



