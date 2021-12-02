package tung.daongoc.blog_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tung.daongoc.blog_demo.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
