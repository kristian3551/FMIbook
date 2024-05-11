package com.example.FMIbook.utils;

import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.utils.jwt.JwtService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TeacherTestUtils {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JwtService jwtService;

    private static final AtomicInteger index = new AtomicInteger(0);

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

    private static final String TEACHER_TEST_NAME = "TestTeacher";

    public List<Map<String, Object>> getTeachers(String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/teachers")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        if (result.getResponse().getContentAsString().isEmpty()) {
            return new ArrayList<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }

    public Map<String, Object> addTeacher(Teacher teacher, String token) throws Exception {
        String studentContent = String.format("""
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
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/teachers")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentContent))
                .andReturn();
        if (result.getResponse().getContentAsString().isEmpty()) {
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }

    public void deleteTeacher(UUID id, String token) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/teachers/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public Map<String, Object> getTeacherDetails(UUID id, String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/teachers/" + id)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        if (result.getResponse().getContentAsString().isEmpty()) {
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }
}
