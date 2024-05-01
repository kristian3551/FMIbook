package com.example.FMIbook.domain.teacher;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.utils.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teachers")
public class Teacher extends User {
    @Column(nullable = false)
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;

    @Column
    @Pattern(regexp = ".+", message = "degree is empty")
    private String degree;

    @ManyToMany(targetEntity = Course.class, cascade = CascadeType.ALL)
    private List<Course> courses;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Teacher() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Teacher(String name, String email, String password, String degree, List<Course> courses) {
        super(email, password);
        this.name = name;
        this.degree = degree;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", degree='" + degree + '\'' +
                ", courses=" + courses +
                '}';
    }

    public Teacher(UUID id, String name, String email, String password, String degree, List<Course> courses) {
        super(id, email, password);
        this.name = name;
        this.degree = degree;
        this.courses = courses;
    }
}
