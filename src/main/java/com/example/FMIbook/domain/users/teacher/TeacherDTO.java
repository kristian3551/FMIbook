package com.example.FMIbook.domain.users.teacher;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.user.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO extends UserDTO {
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;

    @Pattern(regexp = "[A-Z a-z]+", message = "degree is empty")
    private String degree;

    private List<CourseDTO> courses;

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
