package com.example.FMIbook.domain.course.achievement;

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
public class AchievementRequestDTO {
    @Pattern(regexp = ".+", message = "name is empty")
    private String name;
    @Pattern(regexp = ".+", message = "description is empty")
    private String description;
    private UUID studentId;
    private UUID courseId;

    @Override
    public String toString() {
        return "AchievementRequestDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
