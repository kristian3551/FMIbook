package com.example.FMIbook.domain.course.section;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SectionRequestDTO {
    private UUID id;
    private String name;
    private Integer priority;
    private UUID courseId;

    public SectionRequestDTO(Integer priority, UUID courseId) {
        this.priority = priority;
        this.courseId = courseId;
    }

    public SectionRequestDTO(String name, Integer priority, UUID courseId) {
        this.name = name;
        this.priority = priority;
        this.courseId = courseId;
    }
}
