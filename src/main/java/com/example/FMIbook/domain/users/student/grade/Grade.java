package com.example.FMIbook.domain.users.student.grade;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.users.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    @Column(nullable = false)
    @NotNull
    private Integer percentage;

    @Column
    private Double grade;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
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

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public double getGrade() {
        return grade;
    }

    public Grade() {
    }

    public Grade(UUID id, Student student, Course course, Integer percentage, Double grade) {
        this.id = id;
        this.student = student;
        this.course = course;
        this.percentage = percentage;
        this.grade = grade;
    }

    public Grade(Student student, Course course, Integer percentage, Double grade) {
        this.student = student;
        this.course = course;
        this.percentage = percentage;
        this.grade = grade;
    }

    public void setUuid(UUID uuid) {
        this.id = uuid;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public UUID getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }
}
