package com.example.FMIbook.utils;

import com.example.FMIbook.domain.users.teacher.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TeacherTestUtils extends BaseTestUtils {
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final String TEACHER_TEST_NAME = "TestTeacher";
    private MockMvc mvc;

    @Autowired
    public TeacherTestUtils(MockMvc mvc) {
        super(mvc, "teachers");
    }

    public static Teacher generateTestTeacher() {
        String name = TEACHER_TEST_NAME + index.getAndIncrement();
        Teacher teacher = Teacher.builder()
                .name(name)
                .degree("professor")
                .build();
        teacher.setId(UUID.randomUUID());
        teacher.setEmail(name + "@fmi.uni-sofia.bg");
        teacher.setPassword("test123");

        return teacher;
    }

    public Map<String, Object> addOne(Teacher teacher, String token) throws Exception {
        String content = String.format("""
                        {
                            "name": "%s",
                            "email": "%s",
                            "password": "%s",
                            "degree": "%s"
                        }
                        """,
                teacher.getName(),
                teacher.getEmail(),
                teacher.getPassword(),
                teacher.getDegree());
        return super.addOne(content, token);
    }

    public Map<String, Object> updateOne(Teacher teacher, String token) throws Exception {
        String content = String.format("""
                        {
                            "name": "%s",
                            "email": "%s",
                            "degree": "%s"
                        }
                        """,
                teacher.getName(),
                teacher.getEmail(),
                teacher.getDegree());
        return super.updateOne(teacher.getId(), content, token);
    }
}
