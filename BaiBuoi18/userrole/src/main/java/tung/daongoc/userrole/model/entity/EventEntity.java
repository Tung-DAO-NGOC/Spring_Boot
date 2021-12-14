package tung.daongoc.userrole.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import tung.daongoc.userrole.constant.Event;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Entity

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Event event;

    @Column(name = "event_name")
    private String eventName;

    @Column()
    private Instant instant;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;

    @PostLoad
    public void postLoad(){
        if (!this.eventName.isBlank()){
            this.event = Event.of(this.eventName);
        }
    }

    @PrePersist
    public void prePersist(){
        if (!this.event.getEventType().isBlank()) {
            this.eventName = this.event.getEventName();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EventEntity that = (EventEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
