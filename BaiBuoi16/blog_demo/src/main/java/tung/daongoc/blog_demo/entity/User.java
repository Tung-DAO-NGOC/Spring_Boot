package tung.daongoc.blog_demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonManagedReference
    private List<Post> postList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> commentList = new ArrayList<>();

    public void addPost(Post post) {
        this.postList.add(post);
        post.setUser(this);
    }

    public void removePost(Post post) {
        post.setUser(null);
        this.postList.remove(post);
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
        comment.setUser(this);
    }

    public void removeComment(Comment comment){
        comment.setUser(null);
        this.commentList.remove(comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Builder(setterPrefix = "set")
    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }


}
