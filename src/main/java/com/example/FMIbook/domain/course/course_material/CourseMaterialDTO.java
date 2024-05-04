package com.example.FMIbook.domain.course.course_material;

import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.material.MaterialDTO;
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
public class CourseMaterialDTO {
    private UUID id;
    private String name;
    private String description;
    private SectionDTO section;
    private List<MaterialDTO> materials;

    public static CourseMaterialDTO serializeLightweight(CourseMaterial courseMaterial) {
        if (courseMaterial == null) {
            return null;
        }
        return CourseMaterialDTO.builder()
                .id(courseMaterial.getId())
                .name(courseMaterial.getName())
                .description(courseMaterial.getDescription())
                .section(null)
                .materials(courseMaterial.getMaterials().stream().map(MaterialDTO::serializeFromEntity).toList())
                .build();
    }

    public static CourseMaterialDTO serializeFromEntity(CourseMaterial courseMaterial) {
        if (courseMaterial == null) {
            return null;
        }

        return CourseMaterialDTO
                .builder()
                .id(courseMaterial.getId())
                .name(courseMaterial.getName())
                .description(courseMaterial.getDescription())
                .section(SectionDTO.serializeLightweight(courseMaterial.getSection()))
                .materials(courseMaterial.getMaterials()
                        .stream().map(MaterialDTO::serializeFromEntity).toList())
                .build();
    }
}
