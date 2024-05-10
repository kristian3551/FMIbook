package com.example.FMIbook.server.course.grade;

import com.example.FMIbook.domain.course.grade.GradeDTO;
import com.example.FMIbook.domain.course.grade.GradeRequestDTO;
import com.example.FMIbook.domain.course.grade.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/courses/grades")
public class GradeController {
    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping("{gradeId}")
    public GradeDTO findOne(@PathVariable UUID gradeId) {
        return gradeService.getOne(gradeId);
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
