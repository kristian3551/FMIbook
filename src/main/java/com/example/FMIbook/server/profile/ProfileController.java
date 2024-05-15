package com.example.FMIbook.server.profile;

import com.example.FMIbook.domain.users.student.StudentService;
import com.example.FMIbook.domain.users.teacher.TeacherService;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.domain.users.user.UserDTO;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/profile")
@Data
public class ProfileController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping
    public UserDTO getProfileInfo(@AuthenticationPrincipal User user) {
        if (user.isStudent()) {
            return studentService.getOne(user.getId());
        }
        if (user.isTeacher()) {
            return teacherService.getOne(user.getId());
        }
        return UserDTO.serializeFromEntity(user);
    }
}
