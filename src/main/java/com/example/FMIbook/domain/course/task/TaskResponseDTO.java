package com.example.FMIbook.domain.course.task;

import com.example.FMIbook.domain.course.task.submission.SubmissionDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private List<SubmissionDTO> submissions;
    private LocalDateTime createdAt;

    public static TaskResponseDTO serializeLightweight(Task task) {
        if (task == null) {
            return null;
        }

        return TaskResponseDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .description(task.getDescription())
                .course(task.getCourse().getId())
                .type(task.getType())
                .submissions(new ArrayList<>())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static TaskResponseDTO serializeFromEntity(Task task) {
        if (task == null) {
            return null;
        }

        return TaskResponseDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .startDate(task.getStartDate())
                .endDate(task.getEndDate())
                .description(task.getDescription())
                .course(task.getCourse().getId())
                .type(task.getType())
                .submissions(task.getSubmissions() != null
                        ? task.getSubmissions().stream().map(SubmissionDTO::serializeFromEntity).toList()
                        : new ArrayList<>())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
