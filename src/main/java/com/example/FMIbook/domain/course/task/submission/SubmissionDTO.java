package com.example.FMIbook.domain.course.task.submission;

import com.example.FMIbook.domain.material.MaterialDTO;
import com.example.FMIbook.domain.users.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionDTO {
    private UUID id;
    private String description;
    private StudentDTO student;
    private List<MaterialDTO> materials;

    public static SubmissionDTO serializeLightweight(Submission submission) {
        if (submission == null) {
            return null;
        }
        return SubmissionDTO.builder()
                .id(submission.getId())
                .description(submission.getDescription())
                .student(null)
                .materials(submission.getMaterials().stream().map(MaterialDTO::serializeFromEntity).toList())
                .build();
    }

    public static SubmissionDTO serializeFromEntity(Submission submission) {
        if (submission == null) {
            return null;
        }

        return SubmissionDTO.builder()
                .id(submission.getId())
                .description(submission.getDescription())
                .student(StudentDTO.serializeLightweight(submission.getStudent()))
                .materials(submission.getMaterials().stream().map(MaterialDTO::serializeFromEntity).toList())
                .build();
    }
}
