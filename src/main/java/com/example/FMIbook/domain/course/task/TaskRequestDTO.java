package com.example.FMIbook.domain.course.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDTO {
    @NotEmpty(message = "name is empty")
    private String name;
    @NotEmpty(message = "start date is empty")
    private String startDate;
    @NotEmpty(message = "end date is empty")
    private String endDate;
    @NotEmpty(message = "description is empty")
    private String description;
    @NotNull(message = "course id is null")
    private UUID courseId;
    @NotEmpty(message = "type is empty")
    private String type;
}
