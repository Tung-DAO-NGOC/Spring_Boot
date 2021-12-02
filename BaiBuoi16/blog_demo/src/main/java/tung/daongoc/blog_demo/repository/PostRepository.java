package tung.daongoc.blog_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.blog_demo.entity.Post;
import tung.daongoc.blog_demo.entity.User;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
}
