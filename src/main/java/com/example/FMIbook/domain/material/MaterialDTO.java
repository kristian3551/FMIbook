package com.example.FMIbook.domain.material;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaterialDTO {
    private UUID id;
    private String name;
    private String url;
    private LocalDateTime createdAt;

    public static MaterialDTO serializeFromEntity(Material material) {
        return MaterialDTO
                .builder()
                .id(material.getId())
                .name(material.getOriginalName())
                .url(material.getUrl())
                .createdAt(material.getCreatedAt())
                .build();
    }
}
