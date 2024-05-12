package com.example.FMIbook.domain.users.student;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.task.submission.Submission;
import com.example.FMIbook.domain.course.grade.Grade;
import com.example.FMIbook.domain.users.user.Role;
import com.example.FMIbook.domain.users.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "students")
public class Student extends User {
    @Column(nullable = false)
    @NotNull(message = "name is null")
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "faculty number is null")
    @Pattern(regexp = "\\dMI(0800|0900|0700|0600)\\d{3}", message = "faculty number is invalid")
    private String facultyNumber;

    @Transient
    private String specialty;

    @Column(nullable = false)
    @NotNull(message = "semester is null")
    private Integer semester;

    @Transient
    private Integer year;

    @Column(name = "groupId", nullable = false)
    @NotNull(message = "group is null")
    private Integer group;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String degree;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Achievement> achievements;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Submission> submissions;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    public Student(UUID id,
                   String name,
                   String facultyNumber,
                   String email,
                   String password,
                   Integer semester,
                   Integer group,
                   String description,
                   String degree) {
        super(id, email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.setRole(Role.STUDENT);
    }

    public Student(String name,
                   String facultyNumber,
                   String email,
                   String password,
                   Integer semester,
                   Integer group,
                   String description,
                   String degree) {
        super(email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.setRole(Role.STUDENT);
    }

    public Student() {
        super();
        this.setRole(Role.STUDENT);
    }

    public Student(UUID id,
                   String name,
                   String facultyNumber,
                   String email,
                   String password,
                   Integer semester,
                   Integer year,
                   Integer group,
                   String description,
                   String degree,
                   List<Course> courses) {
        super(id, email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.year = year;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
        this.setRole(Role.STUDENT);
    }

    public Student(String name,
                   String facultyNumber,
                   String email,
                   String password,
                   Integer semester,
                   Integer year,
                   Integer group,
                   String description,
                   String degree,
                   List<Course> courses) {
        super(email, password);
        this.name = name;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.year = year;
        this.group = group;
        this.description = description;
        this.degree = degree;
        this.courses = courses;
        this.setRole(Role.STUDENT);
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRole().getAuthorities();
    }

    public String getSpecialty() {
        if (this.facultyNumber.contains("0800"))
            return "Computer Science";
        if (this.facultyNumber.contains("0900"))
            return "Software Engineering";
        return "Other";
    }


    public Integer getYear() {
        if (this.semester == null) {
            return null;
        }
        return this.semester / 2;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", facultyNumber='" + facultyNumber + '\'' +
                ", specialty='" + specialty + '\'' +
                ", email='" + this.getEmail() + '\'' +
                ", password='" + this.getPassword() + '\'' +
                ", semester=" + semester +
                ", year=" + year +
                ", group=" + group +
                ", description='" + description + '\'' +
                ", degree='" + degree + '\'' +
                '}';
    }
}
