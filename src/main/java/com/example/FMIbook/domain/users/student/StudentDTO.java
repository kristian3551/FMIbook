package com.example.FMIbook.domain.users.student;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.users.user.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDTO extends UserDTO {
    @Pattern(regexp = ".{3,}", message = "name is invalid")
    private String name;

    @Pattern(regexp = "\\dMI(0800|0900|0700|0600)\\d{3}", message = "faculty number is invalid")
    private String facultyNumber;
    private Integer semester;
    private Integer group;
    private Integer year;
    private String specialty;
    private String description;
    private String degree;
    private List<CourseDTO> courses;
    private List<AchievementDTO> achievements;
    private List<CourseDTO> takenCourses;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", facultyNumber='" + facultyNumber + '\'' +
                ", specialty='" + this.getSpecialty() + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", semester=" + semester +
                ", year=" + this.getYear() +
                ", group=" + group +
                ", description='" + description + '\'' +
                ", degree='" + degree + '\'' +
                '}';
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

    public Integer getYear() {
        if (this.semester == null) {
            return null;
        }
        return this.semester / 2;
    }

    public static StudentDTO serializeLightweight(Student student) {
        if (student == null) {
            return null;
        }

        StudentDTO result = StudentDTO.builder()
                .name(student.getName())
                .facultyNumber(student.getFacultyNumber())
                .specialty(student.getSpecialty())
                .semester(student.getSemester())
                .year(student.getYear())
                .group(student.getGroup())
                .description(student.getDescription())
                .degree(student.getDegree())
                .achievements(new ArrayList<>())
                .courses(new ArrayList<>())
                .build();
        result.setId(student.getId());
        result.setEmail(student.getEmail());
        return result;
    }

    public static StudentDTO serializeFromEntity(Student student) {
        if (student == null) {
            return null;
        }

        List<CourseDTO> courses = student.getCourses() != null
                ? student.getCourses().stream().map(CourseDTO::serializeLightweight).toList()
                : new ArrayList<>();
        List<AchievementDTO> achievements = student.getAchievements() != null
                ? student.getAchievements().stream().map(
                        achievement -> AchievementDTO.serializeLightweight(achievement, false, true)
        ).toList()
                : new ArrayList<>();

        List<CourseDTO> takenCourses = student.getCourses() != null && student.getGrades() != null
                ? student.getCourses().stream().filter(course ->
                    student.getGrades().stream().anyMatch(
                            grade -> grade.getCourse().getId().equals(course.getId()) && grade.getIsFinal())
                    ).map(CourseDTO::serializeLightweight).toList()
                : new ArrayList<>();

        StudentDTO result = StudentDTO.builder()
                .name(student.getName())
                .facultyNumber(student.getFacultyNumber())
                .semester(student.getSemester())
                .group(student.getGroup())
                .description(student.getDescription())
                .degree(student.getDegree())
                .courses(courses)
                .achievements(achievements)
                .takenCourses(takenCourses)
                .build();
        result.setId(student.getId());
        result.setEmail(student.getEmail());
        return result;
    }
}