package tung.daongoc.userrole.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.userrole.model.entity.EventEntity;

@SuppressWarnings("unused")
public interface EventRepo extends JpaRepository<EventEntity, Long> {
}
