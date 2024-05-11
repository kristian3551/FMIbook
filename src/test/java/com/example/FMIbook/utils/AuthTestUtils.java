package com.example.FMIbook.utils;

import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.utils.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

@Component
public class AuthTestUtils {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtService jwtService;

    public String getAdminAccessToken() {
        User user = new User(UUID.randomUUID(), "admin@abv.bg", "test123");
        return "Bearer " + jwtService.generateToken(user);
    }

    public String getStudentAccessToken() {
        Student student = new Student();
        return "Bearer " + jwtService.generateToken(student);
    }

    public String getTeacherAccessToken() {
        Teacher teacher = new Teacher();
        return "Bearer " + jwtService.generateToken(teacher);
    }
}
