package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.student.Student;
import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.domain.teacher.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotNull
    private String name;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private String semester;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Pattern(regexp = "(compulsory|selectable)", message = "type is invalid")
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    private List<Student> students;

    @ManyToMany(targetEntity = Teacher.class, fetch = FetchType.LAZY)
    private List<Teacher> teachers;

    public Course() {
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
                '}';
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Course(UUID id, String name, Integer year, String semester, String category, String type, String description, List<Student> students, List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
    }

    public Course(String name, Integer year, String semester, String category, String type, String description, List<Student> students, List<Teacher> teachers) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(Integer year) {
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
