package com.example.FMIbook.utils;

import com.example.FMIbook.domain.users.student.Student;
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
public class StudentTestUtils {
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final String USER_TEST_NAME = "TestStudent";
    @Autowired
    private MockMvc mvc;

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

    public List<Map<String, Object>> getStudents(String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/students")
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

    public Map<String, Object> addStudent(Student student, String token) throws Exception {
        String studentContent = String.format("""
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
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/students")
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

    public Map<String, Object> updateStudent(Student student, String token) throws Exception {
        String studentContent = String.format("""
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
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .put("/api/students/" + student.getId())
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

    public void deleteStudent(UUID id, String token) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/students/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public Map<String, Object> getStudentDetails(UUID id, String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/students/" + id)
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
