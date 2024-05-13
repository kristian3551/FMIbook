package com.example.FMIbook.domain.course.grade;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GradeRequestDTO {
    private UUID id;
    @NotNull(message = "percentage is null")
    private Integer percentage;
    @NotNull(message = "grade is null")
    private Double grade;
    @NotNull(message = "studentId is null")
    private UUID studentId;
    @NotNull(message = "courseId is null")
    private UUID courseId;
    private Boolean isFinal = false;

    @Override
    public String toString() {
        return "GradeRequestDTO{" +
                "id=" + id +
                ", percentage=" + percentage +
                ", grade=" + grade +
                ", studentId=" + studentId +
                ", courseId=" + courseId +
                '}';
    }
}
