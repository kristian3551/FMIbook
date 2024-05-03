package com.example.FMIbook.domain.users.user;

import com.example.FMIbook.domain.course.posts.CoursePost;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
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

    @OneToMany(mappedBy = "user")
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
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
}
