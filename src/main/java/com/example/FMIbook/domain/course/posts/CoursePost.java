package com.example.FMIbook.domain.course.posts;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.users.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @OneToMany(mappedBy = "parentPost", cascade = CascadeType.ALL)
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

    public CoursePost() {
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

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRate() {
        return rate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Course getCourse() {
        return course;
    }

    public User getUser() {
        return user;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setUser(User user) {
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

    public CoursePost getParentPost() {
        return parentPost;
    }

    public List<CoursePost> getChildrenPosts() {
        return childrenPosts;
    }

    public void setParentPost(CoursePost parentPost) {
        this.parentPost = parentPost;
    }

    public void setChildrenPosts(List<CoursePost> childrenPosts) {
        this.childrenPosts = childrenPosts;
    }
}
