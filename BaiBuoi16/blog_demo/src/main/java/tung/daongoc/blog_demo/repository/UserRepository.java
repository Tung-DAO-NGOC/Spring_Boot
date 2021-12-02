package tung.daongoc.blog_demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.blog_demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
}
