package tung.daongoc.question18.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity(name="tag")
@Table(name="tag")
@Data
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "setTag")
    private Set<Product> setProduct;
}
