package com.example.FMIbook.domain.users.student.grade;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.student.StudentDTO;

import java.util.UUID;

public class GradeDTO {
    private UUID id;

    private StudentDTO student;

    private CourseDTO course;

    private Integer percentage;

    private Double grade;

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "uuid=" + id +
                ", student=" + student +
                ", course=" + course +
                ", percentage=" + percentage +
                ", grade=" + grade +
                '}';
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public Double getGrade() {
        return grade;
    }

    public GradeDTO(UUID id, StudentDTO student, CourseDTO course, Integer percentage, Double grade) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.percentage = percentage;
        this.grade = grade;
    }

    public GradeDTO(StudentDTO student, CourseDTO course, Integer percentage, Double grade) {
        this.student = student;
        this.course = course;
        this.percentage = percentage;
        this.grade = grade;
    }

    public void setUuid(UUID uuid) {
        this.id = uuid;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public UUID getUuid() {
        return id;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public static GradeDTO serializeFromEntity(Grade grade) {
        if (grade == null) {
            return null;
        }

        grade.getStudent().setGrades(null);
        return new GradeDTO(
                grade.getId(),
                StudentDTO.serializeFromEntity(grade.getStudent()),
                CourseDTO.serializeFromEntity(grade.getCourse()),
                grade.getPercentage(),
                grade.getGrade()
        );
    }
}
