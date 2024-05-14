package com.example.FMIbook.utils;

import com.example.FMIbook.domain.users.student.Student;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StudentTestUtils extends BaseTestUtils {
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final String USER_TEST_NAME = "TestStudent";

    public StudentTestUtils(MockMvc mvc) {
        super(mvc, "students");
    }

    public static Student generateTestStudent() {
        String name = USER_TEST_NAME + index.getAndIncrement();
        Student student = Student.builder()
                .name(name)
                .facultyNumber("6MI0800076")
                .semester(6)
                .group(6)
                .description("test")
                .degree("bachelor")
                .build();
        student.setId(UUID.randomUUID());
        student.setEmail(name + "@fmi.uni-sofia.bg");
        student.setPassword("test123");

        return student;
    }

    public Map<String, Object> addOne(Student student, String token) throws Exception {
        String content = String.format("""
                        {
                            "name": "%s",
                            "email": "%s",
                            "facultyNumber": "%s",
                            "password": "%s",
                            "semester": %d,
                            "group": %d,
                            "description": "%s",
                            "degree": "%s"
                        }
                        """,
                student.getName(),
                student.getEmail(),
                student.getFacultyNumber(),
                student.getPassword(),
                student.getSemester(),
                student.getGroup(),
                student.getDescription(),
                student.getDegree());
        return super.addOne(content, token);
    }

    public Map<String, Object> updateOne(Student student, String token) throws Exception {
        String content = String.format("""
                        {
                            "name": "%s",
                            "email": "%s",
                            "facultyNumber": "%s",
                            "semester": %d,
                            "group": %d,
                            "description": "%s",
                            "degree": "%s"
                        }
                        """,
                student.getName(),
                student.getEmail(),
                student.getFacultyNumber(),
                student.getSemester(),
                student.getGroup(),
                student.getDescription(),
                student.getDegree());
        return super.updateOne(student.getId(), content, token);
    }
}
