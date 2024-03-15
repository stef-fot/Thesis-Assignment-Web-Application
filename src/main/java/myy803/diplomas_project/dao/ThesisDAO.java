package myy803.diplomas_project.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.diplomas_project.model.Student;
import myy803.diplomas_project.model.Subject;
import myy803.diplomas_project.model.Thesis;
import myy803.diplomas_project.model.User;

@Repository
public interface ThesisDAO extends JpaRepository<Thesis, Integer> {
    // You can add any custom methods for the Thesis entity here
    Optional<Thesis> findById(int id);

    List<Thesis> findBySupervisorId(int professorId);

}
