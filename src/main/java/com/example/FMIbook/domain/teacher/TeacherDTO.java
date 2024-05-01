package com.example.FMIbook.domain.teacher;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.utils.user.UserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeacherDTO extends UserDTO {
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;

    @Pattern(regexp = "[A-Z a-z]+", message = "degree is empty")
    private String degree;

    private List<CourseDTO> courses;

    public void setName(String name) {
        this.name = name;
    }

    public TeacherDTO() {
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public String getName() {
        return name;
    }

    public String getDegree() {
        return degree;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public TeacherDTO(String name, String email, String degree, List<CourseDTO> courses) {
        super(email);
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
                ", degree='" + degree + '\'' +
                ", courses=" + courses +
                '}';
    }

    public TeacherDTO(UUID id, String name, String email, String degree, List<CourseDTO> courses) {
        super(id, email);
        this.name = name;
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
                teacher.getDegree(),
                courses
        );
    }
}
