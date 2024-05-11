package com.example.FMIbook.utils;

import com.example.FMIbook.domain.department.Department;
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
public class DepartmentTestUtils {
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final String DEPARTMENT_TEST_NAME = "TestDepartment";
    @Autowired
    private MockMvc mvc;

    public static Department generateTestDepartment() {
        String name = DEPARTMENT_TEST_NAME + index.getAndIncrement();
        return Department.builder()
                .name(name)
                .build();
    }

    public List<Map<String, Object>> getDepartments(String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/departments")
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

    public Map<String, Object> addDepartment(Department department, String token) throws Exception {
        String studentContent = String.format("""
                        {
                            "name": "%s"
                        }
                        """,
                department.getName());
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/departments")
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

    public Map<String, Object> updateDepartment(Department department, String token) throws Exception {
        String studentContent = String.format("""
                        {
                            "name": "%s"
                        }
                        """,
                department.getName());
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .put("/api/departments/" + department.getId())
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

    public void deleteDepartment(UUID id, String token) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/departments/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public Map<String, Object> getDepartmentDetails(UUID id, String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/departments/" + id)
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
