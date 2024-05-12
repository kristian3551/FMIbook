package com.example.FMIbook.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

@Data
public class BaseTestUtils {
    private MockMvc mvc;

    private String path;

    public BaseTestUtils(MockMvc mvc, String path) {
        this.mvc = mvc;
        this.path = path;
    }

    public List<Map<String, Object>> findAll(String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/" + path)
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

    protected Map<String, Object> addOne(String content, String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .post("/api/" + path)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andReturn();
        if (result.getResponse().getContentAsString().isEmpty()) {
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }

    protected Map<String, Object> updateOne(UUID id, String content, String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .put("/api/" + path + "/" + id)
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andReturn();
        if (result.getResponse().getContentAsString().isEmpty()) {
            return new HashMap<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<>() {
                });
    }

    public void delete(UUID id, String token) throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete("/api/" + path + "/" + id)
                .header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON));
    }

    public Map<String, Object> getDetails(UUID id, String token) throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/" + path + "/" + id)
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
