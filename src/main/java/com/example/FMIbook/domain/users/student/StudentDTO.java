package com.example.FMIbook.domain.users.student;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.grade.GradeDTO;
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
    private List<GradeDTO> grades;
    private List<AchievementDTO> achievements;

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

    public StudentDTO(UUID id,
                      String name,
                      String facultyNumber,
                      String email,
                      Integer semester,
                      Integer group,
                      String description,
                      String degree,
                      List<CourseDTO> courses,
                      List<GradeDTO> grades,
                      List<AchievementDTO> achievements) {
        super(id, email);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
        this.grades = grades;
        this.achievements = achievements;
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
                .grades(new ArrayList<>())
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
        List<GradeDTO> grades = student.getGrades() != null
                ? student.getGrades().stream().map(GradeDTO::serializeLightweight).toList()
                : new ArrayList<>();
        List<AchievementDTO> achievements = student.getAchievements() != null
                ? student.getAchievements().stream().map(AchievementDTO::serializeLightweight).toList()
                : new ArrayList<>();

        return new StudentDTO(
                student.getId(),
                student.getName(),
                student.getFacultyNumber(),
                student.getEmail(),
                student.getSemester(),
                student.getGroup(),
                student.getDescription(),
                student.getDegree(),
                courses,
                grades,
                achievements
        );
    }
}