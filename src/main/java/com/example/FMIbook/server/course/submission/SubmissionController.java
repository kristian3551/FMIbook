package com.example.FMIbook.server.course.submission;

import com.example.FMIbook.domain.course.task.submission.SubmissionDTO;
import com.example.FMIbook.domain.course.task.submission.SubmissionRequest;
import com.example.FMIbook.domain.course.task.submission.SubmissionService;
import com.example.FMIbook.domain.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/courses/tasks/submissions")
public class SubmissionController {
    private final SubmissionService submissionService;

    @Autowired
    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping
    public SubmissionDTO addOne(@RequestParam(value = "description", required = false) String description,
                                @RequestParam("studentId") UUID studentId,
                                @RequestParam("taskId") UUID taskId,
                                @RequestParam(value = "files", required = false) MultipartFile[] files,
                                @AuthenticationPrincipal User user) throws IOException {
        SubmissionRequest submissionRequest = SubmissionRequest.builder()
                .taskId(taskId)
                .studentId(studentId)
                .description(description)
                .files(files)
                .build();
        return submissionService.addOne(submissionRequest, user);
    }

    @DeleteMapping("{submissionId}")
    public void delete(@PathVariable UUID submissionId, @AuthenticationPrincipal User user) throws IOException {
        submissionService.delete(submissionId, user);
    }

    @PutMapping("{submissionId}")
    public SubmissionDTO updateOne(@PathVariable UUID submissionId,
                                 @RequestParam(value = "description", required = false) String description,
                                   @RequestParam(value = "gradeId", required = false) UUID gradeId,
                                 @RequestParam(value = "files", required = false) MultipartFile[] files,
                                 @AuthenticationPrincipal User user) throws IOException {
        SubmissionRequest submissionRequest = SubmissionRequest.builder()
                .description(description)
                .files(files)
                .gradeId(gradeId)
                .build();
        return submissionService.update(submissionId, submissionRequest, user);
    }
}
