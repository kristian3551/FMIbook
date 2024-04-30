package com.example.FMIbook.domain.course.section;

import com.example.FMIbook.domain.course.CourseDTO;

import java.util.UUID;

public class SectionDTO {
    private UUID id;
    private String name;
    private Integer priority;
    private CourseDTO course;

    public SectionDTO() {
    }

    public SectionDTO(String name, Integer priority, CourseDTO course) {
        this.name = name;
        this.priority = priority;
        this.course = course;
    }

    public SectionDTO(UUID id, String name, Integer priority, CourseDTO course) {
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

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public CourseDTO getCourse() {
        return course;
    }

    @Override
    public String toString() {
        return "SectionDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }

    public static SectionDTO serializeFromEntity(Section section) {
        if (section == null) {
            return null;
        }
        if (section.getCourse() != null) {
            section.getCourse().setSections(null);
        }
        return new SectionDTO(section.getId(), section.getName(), section.getPriority(), CourseDTO.serializeFromEntity(section.getCourse()));
    }
}
