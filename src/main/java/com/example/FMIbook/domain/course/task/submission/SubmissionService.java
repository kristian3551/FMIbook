package com.example.FMIbook.domain.course.task.submission;

import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.course.task.TaskRepository;
import com.example.FMIbook.domain.course.task.exception.TaskNotFoundException;
import com.example.FMIbook.domain.course.task.submission.exception.SubmissionNotFoundException;
import com.example.FMIbook.domain.grade.Grade;
import com.example.FMIbook.domain.grade.GradeRepository;
import com.example.FMIbook.domain.grade.exception.GradeNotFoundException;
import com.example.FMIbook.domain.material.Material;
import com.example.FMIbook.domain.material.MaterialService;
import com.example.FMIbook.domain.policy.CreatePolicy;
import com.example.FMIbook.domain.policy.DeletePolicy;
import com.example.FMIbook.domain.policy.UpdatePolicy;
import com.example.FMIbook.domain.policy.exception.CannotCreateException;
import com.example.FMIbook.domain.policy.exception.CannotDeleteException;
import com.example.FMIbook.domain.policy.exception.CannotUpdateException;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.student.StudentRepository;
import com.example.FMIbook.domain.users.student.exception.StudentNotFoundException;
import com.example.FMIbook.domain.users.user.User;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;
    private final TaskRepository taskRepository;
    private final StudentRepository studentRepository;
    private final MaterialService materialService;
    private final GradeRepository gradeRepository;

    @Autowired
    public SubmissionService(SubmissionRepository submissionRepository,
                             TaskRepository taskRepository,
                             StudentRepository studentRepository,
                             MaterialService materialService,
                             GradeRepository gradeRepository) {
        this.submissionRepository = submissionRepository;
        this.taskRepository = taskRepository;
        this.studentRepository = studentRepository;
        this.materialService = materialService;
        this.gradeRepository = gradeRepository;
    }

    @Transactional
    public SubmissionDTO addOne(SubmissionRequest submissionDto, User user) throws IOException {
        Optional<Student> studentOpt = studentRepository.findById(submissionDto.getStudentId());

        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException();
        }

        Optional<Task> taskOpt = taskRepository.findById(submissionDto.getTaskId());

        if (taskOpt.isEmpty()) {
            throw new TaskNotFoundException();
        }

        List<Material> materials = new ArrayList<>();

        for (MultipartFile file : submissionDto.getFiles()) {
            materials.add(materialService.addOne(file));
        }

        Submission submission = Submission.builder()
                .description(submissionDto.getDescription())
                .student(studentOpt.get())
                .task(taskOpt.get())
                .materials(materials)
                .build();

        if (!CreatePolicy.canCreateSubmission(user, submission)) {
            throw new CannotCreateException();
        }

        submissionRepository.save(submission);
        return SubmissionDTO.serializeFromEntity(submission);
    }

    public void delete(UUID id, User user) throws IOException {
        Optional<Submission> submission = submissionRepository.findById(id);

        if (submission.isEmpty()) {
            throw new SubmissionNotFoundException();
        }

        if (!DeletePolicy.canDeleteSubmission(user, submission.get())) {
            throw new CannotDeleteException();
        }

        for (Material material : submission.get().getMaterials()) {
            materialService.delete(material.getId());
        }

        submissionRepository.delete(submission.get());
    }

    public SubmissionDTO update(UUID id, SubmissionRequest submissionDto, User user) throws IOException {
        Optional<Submission> submissionOpt = submissionRepository.findById(id);

        if (submissionOpt.isEmpty()) {
            throw new SubmissionNotFoundException();
        }

        if (!UpdatePolicy.canModifySubmission(user, submissionOpt.get())) {
            throw new CannotUpdateException();
        }

        Submission submission = submissionOpt.get();

        if (submissionDto.getDescription() != null) {
            submission.setDescription(submissionDto.getDescription());
        }

        if (submissionDto.getFiles() != null) {
            for (Material material : submission.getMaterials()) {
                materialService.delete(material.getId());
            }

            List<Material> materials = new ArrayList<>();

            for (MultipartFile file : submissionDto.getFiles()) {
                materials.add(materialService.addOne(file));
            }

            submission.setMaterials(materials);
        }

        if (submissionDto.getGradeId() != null) {
            Optional<Grade> gradeOpt = gradeRepository.findById(submissionDto.getGradeId());

            if (gradeOpt.isEmpty()) {
                throw new GradeNotFoundException();
            }

            submission.setGrade(gradeOpt.get());
        }

        submissionRepository.save(submission);
        return SubmissionDTO.serializeFromEntity(submission);
    }
}
