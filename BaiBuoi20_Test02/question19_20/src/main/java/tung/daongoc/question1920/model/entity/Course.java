package tung.daongoc.question1920.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity(name = "course")
@Table(name =  "course")
@Data
public class Course {
    @Id
    private Long id;
    private String name;
}
