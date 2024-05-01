package com.example.FMIbook.utils.user;

import com.example.FMIbook.domain.course.posts.CoursePost;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Email(message = "email is invalid")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "password is null")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CoursePost> posts;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CoursePost> getPosts() {
        return posts;
    }

    public void setPosts(List<CoursePost> posts) {
        this.posts = posts;
    }
}
