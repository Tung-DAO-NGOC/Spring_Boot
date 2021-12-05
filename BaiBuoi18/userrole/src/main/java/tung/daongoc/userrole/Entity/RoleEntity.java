package tung.daongoc.userrole.Entity;

import tung.daongoc.userrole.Constant.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user")
    private List<UserEntity> userList;
}
