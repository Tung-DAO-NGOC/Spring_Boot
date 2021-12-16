package tung.daongoc.userrole.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.userrole.model.entity.UserEntity;

import java.util.Optional;

@SuppressWarnings("unused")
public interface UserRepo extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUuid(String uuid);
}
