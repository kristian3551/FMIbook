package com.example.FMIbook.server.course;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.CourseService;
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
    public CourseDTO addOne(@RequestBody @Valid Course course) {
        return courseService.addOne(course);
    }

    @PutMapping("{courseId}")
    public CourseDTO update(@PathVariable UUID courseId,
                             @RequestBody @Valid CourseDTO courseDto) {
        return courseService.update(courseId, courseDto);
    }

    @PutMapping("{courseId}/students")
    public CourseDTO enrollStudents(@PathVariable UUID courseId,
                                    @RequestBody List<UUID> studentIds) {
        return courseService.setStudents(courseId, studentIds);
    }

    @PutMapping("{courseId}/teachers")
    public CourseDTO setTeachers(@PathVariable UUID courseId,
                                    @RequestBody List<UUID> teacherIds) {
        return courseService.setTeachers(courseId, teacherIds);
    }

    @DeleteMapping("{courseId}")
    public void delete(@PathVariable UUID courseId) {
        courseService.delete(courseId);
    }
}
