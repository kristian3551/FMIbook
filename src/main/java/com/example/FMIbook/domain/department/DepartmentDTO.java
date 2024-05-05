package com.example.FMIbook.domain.department;

import com.example.FMIbook.domain.course.CourseDTO;
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
    @Pattern(regexp = "[A-Za-z ]+", message = "name is empty")
    private String name;
    private List<CourseDTO> courses;

    public DepartmentDTO(String name, List<CourseDTO> courses) {
        this.name = name;
        this.courses = courses;
    }

    public DepartmentDTO(String name) {
        this.name = name;
    }

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

        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                department.getCourses() != null
                ? department.getCourses().stream().map(CourseDTO::serializeLightweight).toList()
                        : new ArrayList<>()
        );
    }
}
