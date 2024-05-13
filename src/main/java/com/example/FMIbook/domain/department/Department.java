package com.example.FMIbook.domain.department;

import com.example.FMIbook.domain.course.Course;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "name is null")
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Course> courses;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Department(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
