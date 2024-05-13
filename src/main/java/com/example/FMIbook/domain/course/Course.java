package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.grade.Grade;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.teacher.Teacher;
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
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotNull(message = "name is null")
    private String name;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    @NotNull(message = "semester is null")
    private String semester;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    @Pattern(regexp = "(compulsory|selectable)", message = "type is invalid")
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @ManyToMany(targetEntity = Student.class)
    @OrderBy("name ASC")
    private List<Student> students;
    @ManyToMany(targetEntity = Teacher.class)
    @OrderBy("name ASC")
    private List<Teacher> teachers;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Grade> grades;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @OrderBy("priority ASC")
    private List<Section> sections;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @OrderBy("created_at DESC")
    private List<CoursePost> posts;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Achievement> achievements;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @OrderBy("created_at DESC")
    private List<Task> tasks;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    public Course(UUID id,
                  String name,
                  Integer year,
                  String semester,
                  String category,
                  String type,
                  String description,
                  List<Student> students,
                  List<Teacher> teachers) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
    }

    public Course(String name,
                  Integer year,
                  String semester,
                  String category,
                  String type,
                  String description,
                  List<Student> students,
                  List<Teacher> teachers) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
