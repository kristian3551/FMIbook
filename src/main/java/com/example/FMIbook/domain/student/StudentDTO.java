package com.example.FMIbook.domain.student;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.domain.student.grade.GradeDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDTO {
    private UUID id;

    @Pattern(regexp = "\\w{3,}", message = "name is invalid")
    private String name;

    @Pattern(regexp = "\\dMI(0800|0900|0700|0600)\\d{3}", message = "faculty number is invalid")
    private String facultyNumber;

    @Email(message = "email is invalid")
    private String email;

    private String password;

    private Integer semester;

    private Integer group;

    private String description;

    private String degree;

    private List<CourseDTO> courses;

    private List<GradeDTO> grades;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", facultyNumber='" + facultyNumber + '\'' +
                ", specialty='" + this.getSpecialty() + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", semester=" + semester +
                ", year=" + this.getYear() +
                ", group=" + group +
                ", description='" + description + '\'' +
                ", degree='" + degree + '\'' +
                ", courses=" + courses +
                ", grades=" + grades +
                '}';
    }

    public List<CourseDTO> getCourses() {
        return courses;
    }

    public void setGrades(List<GradeDTO> grades) {
        this.grades = grades;
    }

    public List<GradeDTO> getGrades() {
        return grades;
    }

    public void setCourses(List<CourseDTO> courses) {
        this.courses = courses;
    }

    public StudentDTO(UUID id, String name, String facultyNumber, String email, String password, Integer semester, Integer group, String description, String degree, List<CourseDTO> courses) {
        this.id = id;
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.email = email;
        this.password = password;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
    }

    public StudentDTO(String name, String facultyNumber, String email, String password, Integer semester, Integer group, String description, String degree, List<CourseDTO> courses) {
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.email = email;
        this.password = password;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public String getSpecialty() {
        if (this.facultyNumber == null) {
            return null;
        }
        if (this.facultyNumber.contains("0800"))
            return "Computer Science";
        if (this.facultyNumber.contains("0900"))
            return "Software Engineering";
        return "Other";
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public StudentDTO() {
    }

    public static StudentDTO serializeFromEntity(Student student) {
        if (student == null) {
            return null;
        }
        List<CourseDTO> courses = student.getCourses() != null
                ? student.getCourses().stream().map(course -> {
            course.setStudents(null);
            course.setGrades(null);
            return CourseDTO.serializeFromEntity(course);
        }).toList()
                : new ArrayList<>();

        List<GradeDTO> grades = student.getGrades() != null
                ? student.getGrades().stream().map(grade -> {
                    if (grade.getStudent() != null) {
                        grade.getStudent().setCourses(null);
                        grade.getStudent().setGrades(null);
                    }
                    if (grade.getCourse() != null) {
                        grade.getCourse().setStudents(null);
                    }
                    return GradeDTO.serializeFromEntity(grade);
        }).toList()
                : new ArrayList<>();

        StudentDTO result = new StudentDTO(
                student.getId(),
                student.getName(),
                student.getFacultyNumber(),
                student.getEmail(),
                null,
                student.getSemester(),
                student.getGroup(),
                student.getDescription(),
                student.getDegree(),
                courses
        );

        result.setGrades(grades);
        return result;
    }
}