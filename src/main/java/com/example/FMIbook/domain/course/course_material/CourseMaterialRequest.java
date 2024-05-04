package com.example.FMIbook.domain.course.course_material;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseMaterialRequest {
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;
    @Pattern(regexp = ".+", message = "name is empty")
    private String description;
    private UUID sectionId;
    private MultipartFile[] files;
}
