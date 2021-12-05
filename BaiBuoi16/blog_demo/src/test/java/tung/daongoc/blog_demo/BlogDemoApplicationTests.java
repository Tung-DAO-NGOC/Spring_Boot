package tung.daongoc.blog_demo;


import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
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
		Assertions.assertThat(commentList.size()).isEqualTo(30);
	}

	@Test
	@Order(4)
	public void secondUserComment(){
		User user = userRepository.findById(2L).get();
		Assertions.assertThat(user.getCommentList().size()).isEqualTo(5);
	}

	@Test
	@Order(5)
	public void firstUserPost(){
		User user = userRepository.findById(1L).get();
		Assertions.assertThat(user.getPostList().size()).isEqualTo(2);
	}

	@Test
	@Order(6)
	public void eighthPostComment(){
		Post post = postRepository.findById(8L).get();
		Assertions.assertThat(post.getCommentList().size()).isEqualTo(4);
	}

	@Test
	@Order(7)
	public void addUser(){
		User user = User.builder().setName("Hoang Long").build();
		Comment comment = new Comment("This is a new comment");
		Comment comment2 = new Comment("The second comment");
		Post post = new Post("This is a new Post");

		user.addComment(comment);
		user.addComment(comment2);
		user.addPost(post);
		post.addComment(comment);
		postRepository.getById(8L).addComment(comment2);

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(user.getPostList().size()).isEqualTo(1);
		softAssertions.assertThat(user.getCommentList().size()).isEqualTo(2);
		softAssertions.assertThat(post.getCommentList().size()).isEqualTo(1);
		softAssertions.assertThat(postRepository.getById(8L).getCommentList().size()).isEqualTo(5);
		softAssertions.assertAll();
	}

	@Test
	@Order(8)
	public void editUser(){
		User user = userRepository.getById(2L);
		String replaceName = "Hong Hai Nhi";
		user.setName(replaceName);
		userRepository.save(user);

		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(userRepository.findByName(replaceName).size()).isEqualTo(1);
		softAssertions.assertThat(userRepository.getById(2L).getName()).isEqualTo(replaceName);
		softAssertions.assertAll();
	}

	@Test
	@Order(9)
	public void deleteUser(){
		User user = userRepository.getById(1L);
		userRepository.deleteById(1L);
		SoftAssertions softAssertions = new SoftAssertions();
		softAssertions.assertThat(userRepository.findById(1L).isEmpty()).isTrue();
		softAssertions.assertThat(postRepository.findByUser(user).size()).isEqualTo(0);
		softAssertions.assertThat(commentRepository.findByUser(user).size()).isEqualTo(0);
		softAssertions.assertAll();
	}



}
