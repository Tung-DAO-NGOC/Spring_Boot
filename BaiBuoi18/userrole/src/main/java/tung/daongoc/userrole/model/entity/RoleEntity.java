package tung.daongoc.userrole.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import tung.daongoc.userrole.constant.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    @JsonIgnore
    private String roleName;

    @Transient
    @JsonValue
    private Role role;

    @ManyToMany(mappedBy = "roleList")
    @Column(name = "user")
    @JsonBackReference
    private List<UserEntity> userList;

    @PostLoad
    void postLoad(){
        if (!this.roleName.isBlank()){
            this.role = Role.of(roleName);
        }
    }

    @PrePersist
    void prePersist(){
        if (!this.role.getRoleName().isBlank()){
            this.roleName = this.role.getRoleName();
        }
    }
}