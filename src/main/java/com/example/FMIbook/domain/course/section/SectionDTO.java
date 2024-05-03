package com.example.FMIbook.domain.course.section;

import com.example.FMIbook.domain.course.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionDTO {
    private UUID id;
    private String name;
    private Integer priority;
    private CourseDTO course;

    public SectionDTO(String name, Integer priority, CourseDTO course) {
        this.name = name;
        this.priority = priority;
        this.course = course;
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
