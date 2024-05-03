package com.example.FMIbook.domain.users.teacher;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.users.user.Role;
import com.example.FMIbook.domain.users.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teachers")
public class Teacher extends User {
    @Column(nullable = false)
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;

    @Column
    @Pattern(regexp = ".+", message = "degree is empty")
    private String degree;

    @ManyToMany(mappedBy = "teachers")
    private List<Course> courses;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }

    public Teacher() {
        this.setRole(Role.TEACHER);
    }

    public Teacher(String name, String email, String password, String degree, List<Course> courses) {
        super(email, password);
        this.name = name;
        this.degree = degree;
        this.courses = courses;
        this.setRole(Role.TEACHER);
    }

    public Teacher(String email, String password, String name, String degree) {
        super(email, password);
        this.name = name;
        this.degree = degree;
        this.setRole(Role.TEACHER);
    }

    public Teacher(UUID id, String name, String email, String password, String degree, List<Course> courses) {
        super(id, email, password);
        this.name = name;
        this.degree = degree;
        this.courses = courses;
        this.setRole(Role.TEACHER);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}