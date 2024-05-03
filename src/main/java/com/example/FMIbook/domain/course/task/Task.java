package com.example.FMIbook.domain.course.task;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "start date is null")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @NotNull(message = "start date is null")
    private LocalDateTime endDate;

    @Column
    @NotEmpty(message = "description is empty")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    @NotEmpty(message = "type is empty")
    private String type;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Task(String name, LocalDateTime startDate, LocalDateTime endDate, String description, Course course, String type) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.course = course;
        this.type = type;
    }
}
