package com.example.FMIbook.domain.student;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.utils.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "students")
public class Student extends User {
    @Column(nullable = false)
    @NotNull(message = "name is null")
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "faculty number is null")
    @Pattern(regexp = "\\dMI(0800|0900|0700|0600)\\d{3}", message = "faculty number is invalid")
    private String facultyNumber;

    @Transient
    private String specialty;

    @Column(nullable = false)
    @NotNull(message = "semester is null")
    private Integer semester;

    @Transient
    private Integer year;

    @Column(name = "groupId", nullable = false)
    @NotNull(message = "group is null")
    private Integer group;

    @Column
    private String description;

    @Column(nullable = false)
    private String degree;

    public List<Course> getCourses() {
        return courses;
    }

    public Student(UUID id, String name, String facultyNumber, String specialty, String email, String password, Integer semester, Integer year, Integer group, String description, String degree) {
        super(id, email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.specialty = specialty;
        this.semester = semester;
        this.year = year;
        this.group = group;
        this.description = description;
        this.degree = degree;
    }

    public Student(String name, String facultyNumber, String specialty, String email, String password, Integer semester, Integer year, Integer group, String description, String degree) {
        super(email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.specialty = specialty;
        this.semester = semester;
        this.year = year;
        this.group = group;
        this.description = description;
        this.degree = degree;
    }

    public Student() {
        super();
    }

    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private List<Course> courses;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", facultyNumber='" + facultyNumber + '\'' +
                ", specialty='" + specialty + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", semester=" + semester +
                ", year=" + year +
                ", group=" + group +
                ", description='" + description + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Student(UUID id, String name, String facultyNumber, String email, String password, Integer semester, Integer year, Integer group, String description, String degree, List<Course> courses) {
        super(id, email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.year = year;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
    }

    public Student(String name, String facultyNumber, String email, String password, Integer semester, Integer year, Integer group, String description, String degree, List<Course> courses) {
        super(email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.year = year;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getName() {
        return name;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public String getSpecialty() {
        if (this.facultyNumber.contains("0800"))
            return "Computer Science";
        if (this.facultyNumber.contains("0900"))
            return "Software Engineering";
        return "Other";
    }

    public Integer getSemester() {
        return semester;
    }

    public Integer getYear() {
        if (this.semester == null) {
            return null;
        }
        return this.semester / 2;
    }

    public Integer getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public String getDegree() {
        return degree;
    }
}
