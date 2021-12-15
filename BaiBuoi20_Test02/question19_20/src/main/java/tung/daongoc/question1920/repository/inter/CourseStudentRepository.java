package tung.daongoc.question1920.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tung.daongoc.question1920.model.entity.CourseStudent;
import tung.daongoc.question1920.model.entity.CourseStudentKey;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentKey> {


}
