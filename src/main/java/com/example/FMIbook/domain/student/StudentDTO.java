package com.example.FMIbook.domain.student;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.student.grade.GradeDTO;
import com.example.FMIbook.utils.user.UserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StudentDTO extends UserDTO {
    @Pattern(regexp = ".{3,}", message = "name is invalid")
    private String name;

    @Pattern(regexp = "\\dMI(0800|0900|0700|0600)\\d{3}", message = "faculty number is invalid")
    private String facultyNumber;

    private Integer semester;

    private Integer group;

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

    public StudentDTO(UUID id,
                      String name,
                      String facultyNumber,
                      String email,
                      Integer semester,
                      Integer group,
                      String description,
                      String degree,
                      List<CourseDTO> courses) {
        super(id, email);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
    }

    public StudentDTO(String name,
                      String facultyNumber,
                      String email,
                      Integer semester,
                      Integer group,
                      String description,
                      String degree,
                      List<CourseDTO> courses) {
        super(email);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
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

    public List<AchievementDTO> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<AchievementDTO> achievements) {
        this.achievements = achievements;
    }

    public static StudentDTO serializeFromEntity(Student student) {
        if (student == null) {
            return null;
        }
        List<CourseDTO> courses = student.getCourses() != null
                ? student.getCourses().stream().map(course -> {
            course.setStudents(new ArrayList<>());
            course.setGrades(new ArrayList<>());
            return CourseDTO.serializeFromEntity(course);
        }).toList()
                : new ArrayList<>();

        List<GradeDTO> grades = student.getGrades() != null
                ? student.getGrades().stream().map(grade -> {
                    if (grade.getStudent() != null) {
                        grade.getStudent().setCourses(new ArrayList<>());
                        grade.getStudent().setGrades(new ArrayList<>());
                    }
                    if (grade.getCourse() != null) {
                        grade.getCourse().setStudents(new ArrayList<>());
                    }
                    return GradeDTO.serializeFromEntity(grade);
        }).toList()
                : new ArrayList<>();
        List<Achievement> achievements = student.getAchievements();
        achievements.forEach(achievement -> {
            achievement.setStudent(null);
            achievement.setCourse(null);
        });
        List<AchievementDTO> achievementDtos =
                achievements.stream().map(AchievementDTO::serializeFromEntity).toList();
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
                achievementDtos
        );
    }
}