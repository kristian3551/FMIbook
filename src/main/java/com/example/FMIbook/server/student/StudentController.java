package com.example.FMIbook.server.student;

import com.example.FMIbook.domain.student.Student;
import com.example.FMIbook.domain.student.StudentDTO;
import com.example.FMIbook.domain.student.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/students")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentDTO> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort) {
        return studentService.findAll(limit, offset, sort);
    }

    @GetMapping("{studentId}")
    public StudentDTO findOne(@PathVariable UUID studentId) {
        return studentService.getOne(studentId);
    }

    @PostMapping
    public StudentDTO addOne(@RequestBody Student student) {
        return studentService.addOne(student);
    }

    @PutMapping("{studentId}")
    public StudentDTO update(@PathVariable UUID studentId,
            @RequestBody @Valid StudentDTO studentDto) {
        return studentService.update(studentId, studentDto);
    }

    @DeleteMapping("{studentId}")
    public void delete(@PathVariable UUID studentId) {
        studentService.delete(studentId);
    }
}
