package com.example.FMIbook.domain.course.achievement;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.users.student.Student;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column
    @NotNull(message = "achievement is null")
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(columnDefinition = "TEXT")
    @Pattern(regexp = ".+", message = "description is empty")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @NotNull(message = "course is null")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    @NotNull(message = "student is null")
    private Student student;

    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Achievement(UUID id, String name, String description, Course course, Student student) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.course = course;
        this.student = student;
    }

    public Achievement(String name, String description, Course course, Student student) {
        this.name = name;
        this.description = description;
        this.course = course;
        this.student = student;
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", course=" + course.getId() +
                ", student=" + student.getId() +
                ", createdAt=" + createdAt +
                '}';
    }
}
