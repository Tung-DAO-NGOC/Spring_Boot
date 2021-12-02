package tung.daongoc.blog_demo;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import tung.daongoc.blog_demo.entity.Comment;
import tung.daongoc.blog_demo.entity.Post;
import tung.daongoc.blog_demo.entity.User;
import tung.daongoc.blog_demo.repository.CommentRepository;
import tung.daongoc.blog_demo.repository.PostRepository;
import tung.daongoc.blog_demo.repository.UserRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


@DataJpaTest
@AutoConfigureMockMvc
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BlogDemoApplicationTests {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	@Order(1)
	public void getAllUser() {
		List<User> userList = userRepository.findAll();
		Assertions.assertThat(userList.size()).isEqualTo(5);
	}

	@Test
	@Order(2)
	public void getAllPost(){
		List<Post> postList = postRepository.findAll();
		Assertions.assertThat(postList.size()).isEqualTo(10);
	}

	@Test
	@Order(3)
	public void getAllComment(){
		List<Comment> commentList = commentRepository.findAll();
		Assertions.assertThat(commentList.size()).isEqualTo(15);
	}

	@Test
	@Order(4)
	public void thirdUserPost(){
		Optional<User> user = userRepository.findById(1L);
		log.info(user.toString());
	}


}
