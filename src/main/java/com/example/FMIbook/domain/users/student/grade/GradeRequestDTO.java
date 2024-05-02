package com.example.FMIbook.domain.users.student.grade;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class GradeRequestDTO {
    private UUID id;
    @NotNull(message = "percentage is null")
    private Integer percentage;
    @NotNull(message = "grade is null")
    private Double grade;
    @NotNull(message = "studentId is null")
    private UUID studentId;
    @NotNull(message = "courseId is null")
    private UUID courseId;

    public GradeRequestDTO(UUID id, Integer percentage, Double grade, UUID studentId, UUID courseId) {
        this.id = id;
        this.percentage = percentage;
        this.grade = grade;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public GradeRequestDTO(Integer percentage, Double grade, UUID studentId, UUID courseId) {
        this.percentage = percentage;
        this.grade = grade;
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public void setStudentId(UUID studentId) {
        this.studentId = studentId;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public UUID getId() {
        return id;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public Double getGrade() {
        return grade;
    }

    public UUID getStudentId() {
        return studentId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public GradeRequestDTO() {
    }

    @Override
    public String toString() {
        return "GradeRequestDTO{" +
                "id=" + id +
                ", percentage=" + percentage +
                ", grade=" + grade +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
