package com.example.FMIbook.utils;

import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.student.StudentRepository;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.teacher.TeacherRepository;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.domain.users.user.UserRepository;
import com.example.FMIbook.utils.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@Component
public class AuthTestUtils {
    private final User user = new User(UUID.randomUUID(), "admin@abv.bg", "test123");
    private final Student student = new Student(
            UUID.randomUUID(),
            "authStudent",
            "6MI0800076",
            "authStudent@abv.bg",
            "test123",
            6,
            6,
            "",
            "bachelor"
    );
    private final Teacher teacher = new Teacher(UUID.randomUUID(),
            "authTeacher@abv.bg",
            "test123",
            "authTeacher",
            "professor");
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    public void addAuthEntities() {
        try {
            userRepository.save(user);
        } catch (Exception ignored) {
        }

        try {
            studentRepository.save(student);
        } catch (Exception ignored) {
        }

        try {
            teacherRepository.save(teacher);
        } catch (Exception ignored) {
        }
    }

    public void deleteAuthEntities() {
        try {
            userRepository.delete(user);
        } catch (Exception ignored) {
        }

        try {
            studentRepository.delete(student);
        } catch (Exception ignored) {
        }

        try {
            teacherRepository.delete(teacher);
        } catch (Exception ignored) {
        }
    }

    public String getAdminAccessToken() {
        return "Bearer " + jwtService.generateToken(user);
    }

    public String getStudentAccessToken() {
        return "Bearer " + jwtService.generateToken(student);
    }

    public String getTeacherAccessToken() {
        return "Bearer " + jwtService.generateToken(teacher);
    }
}
