package myy803.diplomas_project.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myy803.diplomas_project.model.Subject;
import java.util.Optional;

@Repository
public interface SubjectDAO extends JpaRepository<Subject, Integer> {
    Optional<Subject> findById(int id);
    
    List<Subject> findBySupervisorId(int professorId);
    
    
}
