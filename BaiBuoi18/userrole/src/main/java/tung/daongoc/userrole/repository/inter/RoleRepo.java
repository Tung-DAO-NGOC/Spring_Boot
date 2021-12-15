package tung.daongoc.userrole.repository.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.userrole.model.entity.RoleEntity;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRoleName(String roleName);
}
