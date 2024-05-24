package com.example.FMIbook.domain.users.teacher;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.user.Role;
import com.example.FMIbook.domain.users.user.UserDTO;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO extends UserDTO {
    private String name;
    private String degree;

    private List<CourseDTO> courses;

    public TeacherDTO(UUID id, String name, String email, Role role, String degree, List<CourseDTO> courses) {
        super(id, email, role);
        this.name = name;
        this.degree = degree;
        this.courses = courses;
    }

    public static TeacherDTO serializeLightweight(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        TeacherDTO result = TeacherDTO.builder()
                .degree(teacher.getDegree())
                .name(teacher.getName())
                .courses(new ArrayList<>())
                .build();
        result.setId(teacher.getId());
        result.setEmail(teacher.getEmail());
        result.setRole(teacher.getRole());
        return result;
    }

    public static TeacherDTO serializeFromEntity(Teacher teacher) {
        if (teacher == null) {
            return null;
        }
        return new TeacherDTO(
                teacher.getId(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getRole(),
                teacher.getDegree(),
                teacher.getCourses() != null
                ? teacher.getCourses().stream().map(CourseDTO::serializeLightweight).toList()
                        : new ArrayList<>()
        );
    }
}
