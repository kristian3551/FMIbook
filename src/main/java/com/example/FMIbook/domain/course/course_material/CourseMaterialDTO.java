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

    public static CourseMaterialDTO serializeFromEntity(CourseMaterial courseMaterial) {
        SectionDTO section = null;
        if (courseMaterial.getSection() != null) {
            courseMaterial.getSection().setCourseMaterials(new ArrayList<>());
            section = SectionDTO.serializeFromEntity(courseMaterial.getSection());
        }

        List<MaterialDTO> materials = courseMaterial.getMaterials() != null
                ? courseMaterial.getMaterials().stream().map(MaterialDTO::serializeFromEntity).toList()
                : new ArrayList<>();

        return CourseMaterialDTO
                .builder()
                .id(courseMaterial.getId())
                .name(courseMaterial.getName())
                .description(courseMaterial.getDescription())
                .section(section)
                .materials(materials)
                .build();
    }
}
