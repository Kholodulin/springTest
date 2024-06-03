package dev.vorstuu.repositories;

import dev.vorstuu.entity.Student;
import dev.vorstuu.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByGroup(String group);

    List<Student> findByUser(User user);
}
