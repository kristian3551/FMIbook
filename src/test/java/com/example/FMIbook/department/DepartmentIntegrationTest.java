package com.example.FMIbook.department;

import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.utils.AuthTestUtils;
import com.example.FMIbook.utils.DepartmentTestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DepartmentIntegrationTest {
    @Autowired
    private DepartmentTestUtils departmentTestUtils;

    @Autowired
    private AuthTestUtils authTestUtils;

    @BeforeAll
    public void addAuthEntities() {
        authTestUtils.addAuthEntities();
    }

    @AfterAll
    public void deleteAuthEntities() {
        authTestUtils.deleteAuthEntities();
    }

    @Test
    public void testAddDepartment() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> response = departmentTestUtils.addOne(department, authTestUtils.getAdminAccessToken());
        departmentTestUtils.delete(UUID.fromString((String) response.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.get("name").equals(department.getName()), "Name is wrong");
    }

    @Test
    public void testGetDepartmentDetails() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> addedDepartment = departmentTestUtils.addOne(department, authTestUtils.getAdminAccessToken());

        Map<String, Object> resultDepartment = departmentTestUtils.getDetails(
                UUID.fromString((String) addedDepartment.get("id")),
                authTestUtils.getAdminAccessToken());
        departmentTestUtils.delete(
                UUID.fromString((String) addedDepartment.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultDepartment.get("name").equals(department.getName()), "Name is wrong");
    }

    @Test
    public void testGetNonExistingDepartment() throws Exception {
        Map<String, Object> response = departmentTestUtils.getDetails(
                UUID.randomUUID(),
                authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.containsKey("code"), "Code is empty");
        Assert.isTrue(((Integer) response.get("code")) == 1501, "Code is wrong");
        Assert.isTrue(response.containsKey("status"), "Status is empty");
        Assert.isTrue(((Integer) response.get("status")) == 404, "Status is wrong");
        Assert.isTrue(response.get("message").equals("department not found"), "Message is wrong");
    }

    @Test
    public void testDeleteDepartment() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> addedDepartment = departmentTestUtils.addOne(
                department,
                authTestUtils.getAdminAccessToken());

        departmentTestUtils.delete(
                UUID.fromString((String) addedDepartment.get("id")),
                authTestUtils.getAdminAccessToken());

        Map<String, Object> resultDepartment = departmentTestUtils.getDetails(
                UUID.fromString((String) addedDepartment.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultDepartment.containsKey("code"), "Department is found but should not");
    }

    @Test
    public void testUpdateDepartment() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> addedDepartment = departmentTestUtils.addOne(
                department,
                authTestUtils.getAdminAccessToken());

        department.setId(UUID.fromString((String) addedDepartment.get("id")));
        department.setName("Updated" + department.getName());

        Map<String, Object> updatedDepartment = departmentTestUtils.updateOne(
                department,
                authTestUtils.getAdminAccessToken());

        departmentTestUtils.delete(UUID.fromString((String) addedDepartment.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(updatedDepartment.get("name").equals(department.getName()), "Department name is not updated");
    }

    @Test
    public void testAddDepartmentByStudentShouldNotWork() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> response = departmentTestUtils.addOne(department, authTestUtils.getStudentAccessToken());
        Assert.isTrue(response.isEmpty(), "Department is created but should not");
        response = departmentTestUtils.addOne(department, authTestUtils.getTeacherAccessToken());
        Assert.isTrue(response.isEmpty(), "Department is created but should not");
    }
}

