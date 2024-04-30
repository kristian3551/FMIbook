package com.example.FMIbook.domain.department;

import com.example.FMIbook.domain.course.CourseDTO;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DepartmentDTO {
    private UUID id;
    @Pattern(regexp = "[A-Za-z ]+", message = "name is empty")
    private String name;
    private List<CourseDTO> courses;

    public DepartmentDTO() {
    }

    public DepartmentDTO(UUID id, String name, List<CourseDTO> courses) {
        this.id = id;
        this.name = name;
        this.courses = courses;
    }

    public DepartmentDTO(String name, List<CourseDTO> courses) {
        this.name = name;
        this.courses = courses;
    }

    public DepartmentDTO(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public static DepartmentDTO serializeFromEntity(Department department) {
        if (department == null) {
            return null;
        }

        List<CourseDTO> courses = department.getCourses() != null
                ? department.getCourses().stream().map(course -> {
                    course.setDepartment(null);
                    return CourseDTO.serializeFromEntity(course);
        }).toList()
                : new ArrayList<>();

        return new DepartmentDTO(
                department.getId(),
                department.getName(),
                courses
        );
    }
}
