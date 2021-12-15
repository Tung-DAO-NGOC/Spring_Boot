package tung.daongoc.question1920.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.question1920.model.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
