package tung.daongoc.question1920.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "course_student")
@Data
public class CourseStudent {

    @EmbeddedId
    private CourseStudentKey courseStudentKey;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "score")
    @Size(min = 0, max = 10)
    int score;


}
