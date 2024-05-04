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
    private List<CourseMaterialDTO> materials;

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
            section.getCourse().setTeachers(new ArrayList<>());
            section.getCourse().setStudents(new ArrayList<>());
            section.getCourse().setGrades(new ArrayList<>());
            section.getCourse().setAchievements(new ArrayList<>());
            section.getCourse().setPosts(new ArrayList<>());
            section.getCourse().setSections(new ArrayList<>());
            section.getCourse().setTasks(new ArrayList<>());
            section.getCourse().setDepartment(null);
        }
        List<CourseMaterialDTO> materials = section.getCourseMaterials() != null
                ? section.getCourseMaterials().stream().map(CourseMaterialDTO::serializeFromEntity).toList()
                : new ArrayList<>();
        return new SectionDTO(section.getId(),
                section.getName(),
                section.getPriority(),
                CourseDTO.serializeFromEntity(section.getCourse()),
                materials);
    }
}
