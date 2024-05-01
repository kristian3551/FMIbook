package com.example.FMIbook.domain.course.achievement;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class AchievementRequestDTO {
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;
    @Pattern(regexp = ".+", message = "description is empty")
    private String description;
    private UUID studentId;
    private UUID courseId;

    public AchievementRequestDTO(String name, String description, UUID studentId, UUID courseId) {
        this.name = name;
        this.description = description;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "AchievementRequestDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
