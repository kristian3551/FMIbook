package com.example.FMIbook.server.course;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.CourseRequestDTO;
import com.example.FMIbook.domain.course.CourseService;
import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.course.achievement.AchievementRequestDTO;
import com.example.FMIbook.domain.course.posts.PostDTO;
import com.example.FMIbook.domain.course.posts.PostRequestDTO;
import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.course.section.SectionRequestDTO;
import com.example.FMIbook.domain.course.task.TaskRequestDTO;
import com.example.FMIbook.domain.course.task.TaskResponseDTO;
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

    @GetMapping("{courseId}/posts")
    public List<PostDTO> findPostsByCourse(
            @PathVariable UUID courseId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort) {
        return courseService.findAllPostsByCourse(courseId, limit, offset, sort);
    }

    @GetMapping("posts/{postId}")
    public PostDTO findPost(@PathVariable UUID postId) {
        return courseService.getOnePost(postId);
    }

    @PostMapping("posts")
    public PostDTO addPost(@RequestBody @Valid PostRequestDTO postDto) {
        return courseService.addPost(postDto);
    }

    @PutMapping("posts/{postId}")
    public PostDTO updatePost(@PathVariable UUID postId, @RequestBody @Valid PostRequestDTO postDto) {
        return courseService.updatePost(postId, postDto);
    }

    @DeleteMapping("posts/{postId}")
    public void deletePost(@PathVariable UUID postId) {
        courseService.deletePost(postId);
    }

    @GetMapping("{courseId}/achievements")
    public List<AchievementDTO> findAchievementsByCourse(@PathVariable UUID courseId,
                                                         @RequestParam(required = false) Integer limit,
                                                         @RequestParam(required = false) Integer offset,
                                                         @RequestParam(required = false) String sort) {
        return courseService.findAllAchievementsByCourse(courseId, limit, offset, sort);
    }

    @PostMapping("achievements")
    public AchievementDTO addPost(@RequestBody @Valid AchievementRequestDTO achievementDto) {
        return courseService.addAchievement(achievementDto);
    }

    @PutMapping("achievements/{achievementId}")
    public AchievementDTO updatePost(@PathVariable UUID achievementId, @RequestBody @Valid AchievementRequestDTO achievementRequestDTO) {
        return courseService.updateAchievement(achievementId, achievementRequestDTO);
    }

    @DeleteMapping("achievements/{achievementId}")
    public void deleteAchievement(@PathVariable UUID achievementId) {
        courseService.deleteAchievement(achievementId);
    }

    @GetMapping("{taskId}/tasks")
    public List<TaskResponseDTO> findTasksByCourse(@PathVariable UUID taskId,
                                                   @RequestParam(required = false) Integer limit,
                                                   @RequestParam(required = false) Integer offset,
                                                   @RequestParam(required = false) String sort) {
        return courseService.findAllTasksByCourse(taskId, limit, offset, sort);
    }

    @PostMapping("tasks")
    public TaskResponseDTO addTask(@RequestBody @Valid TaskRequestDTO taskDto) {
        return courseService.addTask(taskDto);
    }

    @PutMapping("tasks/{taskId}")
    public TaskResponseDTO updateTask(@PathVariable UUID taskId, @RequestBody TaskRequestDTO taskDto) {
        return courseService.updateTask(taskId, taskDto);
    }

    @DeleteMapping("tasks/{taskId}")
    public void deleteTask(@PathVariable UUID taskId) {
        courseService.deleteTask(taskId);
    }
}
