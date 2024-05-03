package com.example.FMIbook.domain.course.posts;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRequestDTO {
    @Pattern(regexp = ".+", message = "title is empty")
    private String title;
    @Pattern(regexp = ".+", message = "description is empty")
    private String description;
    @Min(value = 0, message = "rate cannot be negative")
    private Integer rate;
    private UUID courseId;
    private UUID userId;
    private UUID parentId;
}
