package com.example.FMIbook.server.course;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.CourseRequestDTO;
import com.example.FMIbook.domain.course.CourseService;
import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.course.achievement.AchievementRequestDTO;
import com.example.FMIbook.domain.course.grade.GradeDTO;
import com.example.FMIbook.domain.course.grade.GradeService;
import com.example.FMIbook.domain.course.posts.PostDTO;
import com.example.FMIbook.domain.course.posts.PostRequestDTO;
import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.course.section.SectionRequestDTO;
import com.example.FMIbook.domain.course.task.TaskRequestDTO;
import com.example.FMIbook.domain.course.task.TaskResponseDTO;
import com.example.FMIbook.domain.users.user.User;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/courses")
@Data
public class CourseController {
    private final CourseService courseService;
    private final GradeService gradeService;

    @GetMapping
    public List<CourseDTO> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String search) {
        return courseService.findAll(limit, offset, sort, search);
    }

    @GetMapping("{studentId}")
    public CourseDTO findOne(@PathVariable UUID studentId, @AuthenticationPrincipal User user) {
        return courseService.getOne(studentId, user);
    }

    @PostMapping
    public CourseDTO addOne(@RequestBody @Valid CourseRequestDTO course, @AuthenticationPrincipal User user) {
        return courseService.addOne(course, user);
    }

    @PutMapping("{courseId}")
    public CourseDTO update(@PathVariable UUID courseId,
                             @RequestBody @Valid CourseRequestDTO courseDto,
                            @AuthenticationPrincipal User user) {
        return courseService.update(courseId, courseDto, user);
    }

    @DeleteMapping("{courseId}")
    public void delete(@PathVariable UUID courseId, @AuthenticationPrincipal User user) {
        courseService.delete(courseId, user);
    }

    @PostMapping("sections")
    public SectionDTO addSection(@Valid @RequestBody SectionRequestDTO sectionDto, @AuthenticationPrincipal User user) {
        return courseService.addSection(sectionDto, user);
    }

    @DeleteMapping("sections/{sectionId}")
    public void addSection(@PathVariable UUID sectionId, @AuthenticationPrincipal User user) {
        courseService.deleteSection(sectionId, user);
    }

    @PutMapping("sections/{sectionId}")
    public SectionDTO updateSection(@PathVariable UUID sectionId,
                                    @RequestBody SectionRequestDTO sectionDto,
                                    @AuthenticationPrincipal User user) {
        return courseService.updateSection(sectionId, sectionDto, user);
    }

    @GetMapping("{courseId}/posts")
    public List<PostDTO> findPostsByCourse(
            @PathVariable UUID courseId,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort,
            @AuthenticationPrincipal User user) {
        return courseService.findAllPostsByCourse(courseId, limit, offset, sort, user);
    }

    @GetMapping("posts/{postId}")
    public PostDTO findPost(@PathVariable UUID postId, @AuthenticationPrincipal User user) {
        return courseService.getOnePost(postId, user);
    }

    @PostMapping("posts")
    public PostDTO addPost(@RequestBody @Valid PostRequestDTO postDto, @AuthenticationPrincipal User user) {
        return courseService.addPost(postDto, user);
    }

    @PutMapping("posts/{postId}")
    public PostDTO updatePost(@PathVariable UUID postId,
                              @RequestBody @Valid PostRequestDTO postDto,
                              @AuthenticationPrincipal User user) {
        return courseService.updatePost(postId, postDto, user);
    }

    @DeleteMapping("posts/{postId}")
    public void deletePost(@PathVariable UUID postId, @AuthenticationPrincipal User user) {
        courseService.deletePost(postId, user);
    }

    @GetMapping("{courseId}/achievements")
    public List<AchievementDTO> findAchievementsByCourse(@PathVariable UUID courseId,
                                                         @RequestParam(required = false) Integer limit,
                                                         @RequestParam(required = false) Integer offset,
                                                         @RequestParam(required = false) String sort,
                                                         @AuthenticationPrincipal User user) {
        return courseService.findAllAchievementsByCourse(courseId, limit, offset, sort, user);
    }

    @PostMapping("achievements")
    public AchievementDTO addPost(@RequestBody @Valid AchievementRequestDTO achievementDto, @AuthenticationPrincipal User user) {
        return courseService.addAchievement(achievementDto, user);
    }

    @PutMapping("achievements/{achievementId}")
    public AchievementDTO updatePost(@PathVariable UUID achievementId,
                                     @RequestBody @Valid AchievementRequestDTO achievementRequestDTO,
                                     @AuthenticationPrincipal User user) {
        return courseService.updateAchievement(achievementId, achievementRequestDTO, user);
    }

    @DeleteMapping("achievements/{achievementId}")
    public void deleteAchievement(@PathVariable UUID achievementId, @AuthenticationPrincipal User user) {
        courseService.deleteAchievement(achievementId, user);
    }

    @GetMapping("{courseId}/tasks")
    public List<TaskResponseDTO> findTasksByCourse(@PathVariable UUID courseId,
                                                   @RequestParam(required = false) Integer limit,
                                                   @RequestParam(required = false) Integer offset,
                                                   @RequestParam(required = false) String sort,
                                                   @AuthenticationPrincipal User user) {
        return courseService.findAllTasksByCourse(courseId, limit, offset, sort, user);
    }

    @PostMapping("tasks")
    public TaskResponseDTO addTask(@RequestBody @Valid TaskRequestDTO taskDto, @AuthenticationPrincipal User user) {
        return courseService.addTask(taskDto, user);
    }

    @PutMapping("tasks/{taskId}")
    public TaskResponseDTO updateTask(@PathVariable UUID taskId, @RequestBody TaskRequestDTO taskDto, @AuthenticationPrincipal User user) {
        return courseService.updateTask(taskId, taskDto, user);
    }

    @GetMapping("tasks/{taskId}")
    public TaskResponseDTO findOneTask(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        return courseService.findOneTask(taskId, user);
    }

    @DeleteMapping("tasks/{taskId}")
    public void deleteTask(@PathVariable UUID taskId, @AuthenticationPrincipal User user) {
        courseService.deleteTask(taskId, user);
    }

    @GetMapping("{courseId}/grades")
    public List<GradeDTO> findAllByCourse(
            @PathVariable UUID courseId,
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID studentId
    ) {
        return gradeService.findAllByCourse(courseId, user, limit, offset, sort, studentId);
    }
}
