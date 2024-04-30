package com.example.FMIbook.domain.course.section;

import java.util.UUID;

public class SectionRequestDTO {
    private UUID id;
    private String name;
    private Integer priority;
    private UUID courseId;

    public SectionRequestDTO(Integer priority, UUID courseId) {
        this.priority = priority;
        this.courseId = courseId;
    }

    public SectionRequestDTO() {
    }

    public SectionRequestDTO(UUID id, String name, Integer priority, UUID courseId) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.courseId = courseId;
    }

    public SectionRequestDTO(String name, Integer priority, UUID courseId) {
        this.name = name;
        this.priority = priority;
        this.courseId = courseId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setCourseId(UUID courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public Integer getPriority() {
        return priority;
    }

    public UUID getCourseId() {
        return courseId;
    }
}
