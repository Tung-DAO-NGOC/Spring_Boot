package tung.daongoc.blog_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.blog_demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
