package com.example.FMIbook.domain.course.posts;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.users.user.User;
import jakarta.persistence.*;
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
@Table(name = "posts")
public class CoursePost {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @Pattern(regexp = ".+", message = "title is empty")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Pattern(regexp = ".+", message = "description is empty")
    private String description;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer rate;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_post_id")
    private CoursePost parentPost;

    @OneToMany(mappedBy = "parentPost")
    private List<CoursePost> childrenPosts;

    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public CoursePost(UUID id, String title, String description, Integer rate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rate = rate == null ? 0 : rate;
    }

    public CoursePost(String title, String description, Integer rate) {
        this.title = title;
        this.description = description;
        this.rate = rate == null ? 0 : rate;
    }

    public CoursePost(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public CoursePost(String title, String description, Integer rate, Course course, User user) {
        this.title = title;
        this.description = description;
        this.rate = rate == null ? 0 : rate;
        this.course = course;
        this.user = user;
    }

    @Override
    public String toString() {
        return "CoursePost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rate=" + rate +
                ", course=" + course.getId() +
                ", teacher=" + user.getId() +
                '}';
    }
}
