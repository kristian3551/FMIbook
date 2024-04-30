package com.example.FMIbook.domain.course.section;

import com.example.FMIbook.domain.course.Course;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer priority;

    @ManyToOne
    private Course course;

    public Section() {
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

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
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
