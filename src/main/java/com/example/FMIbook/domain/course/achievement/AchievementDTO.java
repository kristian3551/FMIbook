package com.example.FMIbook.domain.course.achievement;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.student.StudentDTO;

import java.util.ArrayList;
import java.util.UUID;

public class AchievementDTO {
    private UUID id;
    private String name;
    private String description;
    private StudentDTO student;
    private CourseDTO course;

    public AchievementDTO(UUID id, String name, String description, StudentDTO studentDTO, CourseDTO courseDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.student = studentDTO;
        this.course = courseDTO;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public static AchievementDTO serializeFromEntity(Achievement achievement) {
        if (achievement == null) {
            return null;
        }

        if (achievement.getStudent() != null) {
            achievement.getStudent().setAchievements(new ArrayList<>());
            achievement.getStudent().setCourses(new ArrayList<>());
            achievement.getStudent().setGrades(new ArrayList<>());
        }

        if (achievement.getCourse() != null) {
            achievement.getCourse().setStudents(new ArrayList<>());
            achievement.getCourse().setGrades(new ArrayList<>());
            achievement.getCourse().setTeachers(new ArrayList<>());
            achievement.getCourse().setSections(new ArrayList<>());
            achievement.getCourse().setDepartment(null);
        }

        return new AchievementDTO(
                achievement.getId(),
                achievement.getName(),
                achievement.getDescription(),
                StudentDTO.serializeFromEntity(achievement.getStudent()),
                CourseDTO.serializeFromEntity(achievement.getCourse())
        );
    }
}
