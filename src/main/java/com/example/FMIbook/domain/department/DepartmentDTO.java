package com.example.FMIbook.domain.department;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.teacher.TeacherDTO;
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
@Builder
public class DepartmentDTO {
    private UUID id;

    @Pattern(regexp = ".+", message = "name is empty")
    private String name;
    private List<CourseDTO> courses;
    private List<TeacherDTO> teachers;

    public static DepartmentDTO serializeLightweight(Department department) {
        if (department == null) {
            return null;
        }
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .courses(new ArrayList<>())
                .build();
    }

    public static DepartmentDTO serializeFromEntity(Department department) {
        if (department == null) {
            return null;
        }

        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .courses(department.getCourses() != null
                        ? department.getCourses().stream().map(CourseDTO::serializeLightweight).toList()
                        : new ArrayList<>())
                .teachers(department.getTeachers() != null
                        ? department.getTeachers().stream().map(TeacherDTO::serializeLightweight).toList()
                        : new ArrayList<>())
                .build();
    }
}
