package com.example.FMIbook.domain.course.section;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.course_material.CourseMaterial;
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
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(nullable = false)
    private Integer priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<CourseMaterial> courseMaterials;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Section(String name, Integer priority, Course course) {
        this.name = name;
        this.priority = priority;
        this.course = course;
    }

    public Section(UUID id, String name, Integer priority, Course course) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.course = course;
    }
    @Override
    public String toString() {
        return "Section{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
