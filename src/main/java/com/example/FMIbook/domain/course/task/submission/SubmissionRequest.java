package com.example.FMIbook.domain.course.task.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequest {
    private String description;
    private UUID studentId;
    private UUID taskId;
    private MultipartFile[] files;
    private UUID gradeId;
}
