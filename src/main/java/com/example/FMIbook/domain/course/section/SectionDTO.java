package com.example.FMIbook.domain.course.section;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.course_material.CourseMaterialDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
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
    private List<CourseMaterialDTO> courseMaterials;

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

    public static SectionDTO serializeLightweight(Section section) {
        if (section == null) {
            return null;
        }
        return SectionDTO.builder()
                .id(section.getId())
                .name(section.getName())
                .priority(section.getPriority())
                .course(CourseDTO.serializeLightweight(section.getCourse()))
                .courseMaterials(section.getCourseMaterials().stream().map(CourseMaterialDTO::serializeLightweight).toList())
                .build();
    }

    public static SectionDTO serializeFromEntity(Section section) {
        if (section == null) {
            return null;
        }

        List<CourseMaterialDTO> materials = section.getCourseMaterials()
                .stream().map(CourseMaterialDTO::serializeFromEntity).toList();
        return new SectionDTO(section.getId(),
                section.getName(),
                section.getPriority(),
                CourseDTO.serializeLightweight(section.getCourse()),
                materials);
    }
}
