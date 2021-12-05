package tung.daongoc.userrole.Entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GenericGenerator(name = "userGenerator", strategy = "user_id_gen")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userGenerator")
    private Long id;

    @NaturalId
    @Column(name = "uuid")
    private String uuid;

    @NaturalId
    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "role")
    private List<RoleEntity> roleList;
}
