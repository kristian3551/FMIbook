package com.example.FMIbook.server.grade;

import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.domain.student.grade.GradeDTO;
import com.example.FMIbook.domain.student.grade.GradeRequestDTO;
import com.example.FMIbook.domain.student.grade.GradeService;
import com.example.FMIbook.domain.teacher.Teacher;
import com.example.FMIbook.domain.teacher.TeacherDTO;
import com.example.FMIbook.domain.teacher.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/grades")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<GradeDTO> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort) {
        return gradeService.findAll(limit, offset, sort);
    }

    @GetMapping("{gradeId}")
    public GradeDTO findOne(@PathVariable UUID teacherId) {
        return gradeService.getOne(teacherId);
    }

    @PostMapping
    public GradeDTO addOne(@RequestBody @Valid GradeRequestDTO gradeDto) {
        return gradeService.addOne(gradeDto);
    }

    @PutMapping("{gradeId}")
    public GradeDTO update(@PathVariable UUID gradeId,
                             @RequestBody GradeRequestDTO gradeDto) {
        return gradeService.update(gradeId, gradeDto);
    }

    @DeleteMapping("{gradeId}")
    public void delete(@PathVariable UUID gradeId) {
        gradeService.delete(gradeId);
    }
}