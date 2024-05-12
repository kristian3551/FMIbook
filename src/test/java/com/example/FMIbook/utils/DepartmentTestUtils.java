package com.example.FMIbook.utils;

import com.example.FMIbook.domain.department.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DepartmentTestUtils extends BaseTestUtils {
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final String DEPARTMENT_TEST_NAME = "TestDepartment";
    private MockMvc mvc;

    @Autowired
    public DepartmentTestUtils(MockMvc mvc) {
        super(mvc, "departments");
    }

    public static Department generateTestDepartment() {
        String name = DEPARTMENT_TEST_NAME + index.getAndIncrement();
        return Department.builder()
                .name(name)
                .build();
    }

    public Map<String, Object> addOne(Department department, String token) throws Exception {
        String content = String.format("""
                        {
                            "name": "%s"
                        }
                        """,
                department.getName());
        return super.addOne(content, token);
    }

    public Map<String, Object> updateOne(Department department, String token) throws Exception {
        String content = String.format("""
                        {
                            "name": "%s"
                        }
                        """,
                department.getName());
        return super.updateOne(department.getId(), content, token);
    }
}
