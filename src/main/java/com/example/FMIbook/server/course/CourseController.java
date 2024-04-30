package com.example.FMIbook.server.course;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.CourseRequestDTO;
import com.example.FMIbook.domain.course.CourseService;
import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.course.section.SectionRequestDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/courses")
public class CourseController {
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseDTO> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort) {
        return courseService.findAll(limit, offset, sort);
    }

    @GetMapping("{studentId}")
    public CourseDTO findOne(@PathVariable UUID studentId) {
        return courseService.getOne(studentId);
    }

    @PostMapping
    public CourseDTO addOne(@RequestBody @Valid CourseRequestDTO course) {
        return courseService.addOne(course);
    }

    @PutMapping("{courseId}")
    public CourseDTO update(@PathVariable UUID courseId,
                             @RequestBody @Valid CourseRequestDTO courseDto) {
        return courseService.update(courseId, courseDto);
    }

    @DeleteMapping("{courseId}")
    public void delete(@PathVariable UUID courseId) {
        courseService.delete(courseId);
    }

    @PostMapping("sections")
    public SectionDTO addSection(@Valid @RequestBody SectionRequestDTO sectionDto) {
        return courseService.addSection(sectionDto);
    }

    @DeleteMapping("sections/{sectionId}")
    public void addSection(@PathVariable UUID sectionId) {
        courseService.deleteSection(sectionId);
    }

    @PutMapping("sections/{sectionId}")
    public SectionDTO updateSection(@PathVariable UUID sectionId, @RequestBody SectionRequestDTO sectionDto) {
        return courseService.updateSection(sectionId, sectionDto);
    }
}
