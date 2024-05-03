package com.example.FMIbook.domain.course.task;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class TaskResponseDTO {
    private UUID id;
    @NotEmpty(message = "name is empty")
    private String name;
    @NotNull(message = "start date is null")
    private LocalDateTime startDate;
    @NotNull(message = "start date is null")
    private LocalDateTime endDate;
    @NotEmpty(message = "description is empty")
    private String description;
    private UUID course;
    @NotEmpty(message = "type is empty")
    private String type;

    public static TaskResponseDTO serializeFromEntity(Task task) {
        if (task == null) {
            return null;
        }

        return new TaskResponseDTO(
                task.getId(),
                task.getName(),
                task.getStartDate(),
                task.getEndDate(),
                task.getDescription(),
                task.getCourse().getId(),
                task.getType()
        );
    }
}
