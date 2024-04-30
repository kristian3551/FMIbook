package com.example.FMIbook.domain.course;

import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.UUID;

public class CourseRequestDTO {
    private UUID id;

    private String name;

    private Integer year;

    private String semester;

    private String category;

    @Pattern(regexp = "(compulsory|selectable)", message = "type is invalid")
    private String type;

    private String description;

    private List<UUID> students;

    private List<UUID> teachers;

    private List<UUID> sections;

    private UUID department;

    public UUID getDepartment() {
        return department;
    }

    public void setDepartment(UUID department) {
        this.department = department;
    }

    public void setSections(List<UUID> sections) {
        this.sections = sections;
    }

    public List<UUID> getSections() {
        return sections;
    }

    private List<UUID> grades;

    public void setGrades(List<UUID> grades) {
        this.grades = grades;
    }

    public List<UUID> getGrades() {
        return grades;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", students=" + students +
                ", teachers=" + teachers +
                ", grades=" + grades +
                '}';
    }

    public CourseRequestDTO() {
    }

    public CourseRequestDTO(UUID id,
                     String name,
                     Integer year,
                     String semester,
                     String category,
                     String type,
                     String description,
                     List<UUID> students,
                     List<UUID> teachers, UUID department
    ) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
        this.department = department;
    }

    public CourseRequestDTO(String name,
                     Integer year,
                     String semester,
                     String category,
                     String type,
                     String description,
                     List<UUID> students,
                     List<UUID> teachers) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
    }

    public void setTeachers(List<UUID> teachers) {
        this.teachers = teachers;
    }

    public List<UUID> getTeachers() {
        return teachers;
    }

    public List<UUID> getStudents() {
        return students;
    }

    public void setStudents(List<UUID> students) {
        this.students = students;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}

