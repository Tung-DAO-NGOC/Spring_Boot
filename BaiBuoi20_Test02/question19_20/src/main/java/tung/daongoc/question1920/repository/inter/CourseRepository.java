package tung.daongoc.question1920.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.question1920.model.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
