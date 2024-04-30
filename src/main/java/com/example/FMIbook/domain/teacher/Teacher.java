package com.example.FMIbook.domain.teacher;

import com.example.FMIbook.domain.course.Course;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Za-z ]+]", message = "name is empty")
    private String name;

    @Column(nullable = false)
    @Email(message = "email is invalid")
    private String email;

    @Column(nullable = false)
    @Pattern(regexp = "[A-Za-z @!#$%&0-9]+", message = "password is empty")
    private String password;

    @Column
    @Pattern(regexp = "[A-Za-z ]+", message = "degree is empty")
    private String degree;

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.LAZY)
    private List<Course> courses;

    public Teacher() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDegree() {
        return degree;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Teacher(String name, String email, String password, String degree, List<Course> courses) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.degree = degree;
        this.courses = courses;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", degree='" + degree + '\'' +
                ", courses=" + courses +
                '}';
    }

    public Teacher(UUID id, String name, String email, String password, String degree, List<Course> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.degree = degree;
        this.courses = courses;
    }
}
