package com.example.FMIbook.domain.course.posts;

import jakarta.validation.constraints.Pattern;

import java.util.UUID;

public class PostRequestDTO {
    @Pattern(regexp = ".+", message = "title is empty")
    private String title;
    @Pattern(regexp = ".+", message = "description is empty")
    private String description;
    private Integer rate;
    private UUID courseId;
    private UUID userId;
    private UUID parentId;

    public PostRequestDTO(String title, String description, Integer rate, UUID courseId, UUID userId) {
        this.title = title;
        this.description = description;
        this.courseId = courseId;
        this.userId = userId;
        this.rate = rate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public UUID getUserId() {
        return userId;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
}
