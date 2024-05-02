package com.example.FMIbook.domain.users.user;

import com.example.FMIbook.domain.course.posts.CoursePost;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    @Email(message = "email is invalid")
    private String email;

    @Column(nullable = false)
    @NotNull(message = "password is null")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CoursePost> posts;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
        this.setRole(Role.ADMIN);
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.setRole(Role.ADMIN);
    }

    public User(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.setRole(Role.ADMIN);
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
