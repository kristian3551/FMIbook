package com.example.FMIbook.domain.material;

import com.example.FMIbook.domain.course.course_material.CourseMaterial;
import com.example.FMIbook.domain.course.task.submission.Submission;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "original name is empty")
    private String originalName;

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;

    @ManyToMany(mappedBy = "materials")
    private List<CourseMaterial> sections;

    @ManyToMany(mappedBy = "materials")
    private List<Submission> submissions;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
