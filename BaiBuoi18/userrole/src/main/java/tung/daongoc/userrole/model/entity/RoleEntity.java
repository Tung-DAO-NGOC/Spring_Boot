package tung.daongoc.userrole.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import org.hibernate.Hibernate;
import tung.daongoc.userrole.constant.Role;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    @ToString.Exclude
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RoleEntity that = (RoleEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
