package tung.daongoc.question1920.model.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "student")
@Table(name = "student")
@Data
public class Student {
    @Id
    private Long id;

    private String name;
}
