package tung.daongoc.userrole.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@Data
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
    private List<RoleEntity> roleList;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
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
        for (RoleEntity roleEntity: this.roleList) {
            roleEntity.getUserList().remove(this);
            this.roleList.remove(roleEntity);
        }
    }
}
