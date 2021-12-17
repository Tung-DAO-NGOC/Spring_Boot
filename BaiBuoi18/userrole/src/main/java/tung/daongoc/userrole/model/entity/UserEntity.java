package tung.daongoc.userrole.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "uuid")
    private String uuid;

    @NaturalId
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "role")
    @ManyToMany
    @JoinTable(name = "user_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonManagedReference
    @ToString.Exclude
    private List<RoleEntity> roleList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    @ToString.Exclude
    private List<EventEntity> eventList;

    @Column(name = "password", nullable = false)
    private String password;

    @PrePersist
    private void setUuid(){
        if (this.uuid.isEmpty()) {
            this.uuid = UUID.randomUUID().toString();
        }
    }

    public void addEvent(EventEntity eventEntity){
        this.eventList.add(eventEntity);
        eventEntity.setUser(this);
    }

    public void addRole(RoleEntity roleEntity){
        this.roleList.add(roleEntity);
        roleEntity.getUserList().add(this);
    }

    public void clearRole(){
        List<RoleEntity> temporal = this.roleList.stream().toList();
        for (RoleEntity roleEntity: temporal) {
            this.roleList.remove(roleEntity);
            roleEntity.getUserList().remove(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
