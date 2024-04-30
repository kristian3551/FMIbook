package com.example.FMIbook.domain.teacher;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeacherDTO {
    private UUID id;

    @Pattern(regexp = "\\w+", message = "name is empty")
    private String name;

    @Email(message = "email is invalid")
    private String email;

    @Pattern(regexp = "\\w+", message = "password is empty")
    private String password;

    @Pattern(regexp = "[A-Z a-z]+", message = "degree is empty")
    private String degree;

    private List<CourseDTO> courses;

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TeacherDTO() {
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

    public void setCourses(List<CourseDTO> courses) {
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

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public TeacherDTO(String name, String email, String password, String degree, List<CourseDTO> courses) {
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

    public TeacherDTO(UUID id, String name, String email, String password, String degree, List<CourseDTO> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.degree = degree;
        this.courses = courses;
    }

    public static TeacherDTO serializeFromEntity(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        List<CourseDTO> courses = teacher.getCourses() != null
                ? teacher.getCourses().stream().map(CourseDTO::serializeFromEntity).toList()
                : new ArrayList<>();
        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                null,
                teacher.getDegree(),
                courses
        );
    }
}
