package com.example.FMIbook.server.course.course_material;

import com.example.FMIbook.domain.course.course_material.CourseMaterialDTO;
import com.example.FMIbook.domain.course.course_material.CourseMaterialRequest;
import com.example.FMIbook.domain.course.course_material.CourseMaterialService;
import com.example.FMIbook.domain.users.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/courses/materials")
public class CourseMaterialController {
    private final CourseMaterialService courseMaterialService;

    @Autowired
    public CourseMaterialController(CourseMaterialService courseMaterialService) {
        this.courseMaterialService = courseMaterialService;
    }

    @GetMapping("{materialId}")
    public CourseMaterialDTO findOne(@PathVariable UUID materialId, @AuthenticationPrincipal User user) {
        return courseMaterialService.findOne(materialId, user);
    }

    @PostMapping
    public CourseMaterialDTO addOne(@RequestParam("name") String name,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam("sectionId") UUID sectionId,
                                    @RequestParam(value = "files", required = false) MultipartFile[] files,
                                    @AuthenticationPrincipal User user) throws IOException {
        CourseMaterialRequest materialDto = CourseMaterialRequest
                .builder()
                .name(name)
                .description(description)
                .sectionId(sectionId)
                .files(files)
                .build();
        return courseMaterialService.addOne(materialDto, user);
    }

    @DeleteMapping("{materialId}")
    public void delete(@PathVariable UUID materialId, @AuthenticationPrincipal User user) throws IOException {
        courseMaterialService.delete(materialId, user);
    }
}
