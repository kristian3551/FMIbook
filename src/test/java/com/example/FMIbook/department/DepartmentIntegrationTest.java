package com.example.FMIbook.department;

import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.utils.AuthTestUtils;
import com.example.FMIbook.utils.DepartmentTestUtils;
import org.junit.jupiter.api.Test;
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
public class DepartmentIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private DepartmentTestUtils departmentTestUtils;

    @Autowired
    private AuthTestUtils authTestUtils;

    @Test
    public void testAddStudent() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> response = departmentTestUtils.addDepartment(department, authTestUtils.getAdminAccessToken());

        departmentTestUtils.deleteDepartment(UUID.fromString((String) response.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.get("name").equals(department.getName()), "Name is wrong");
    }

    @Test
    public void testGetStudentDetails() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> addedStudent = departmentTestUtils.addDepartment(department, authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = departmentTestUtils.getDepartmentDetails(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());
        departmentTestUtils.deleteDepartment(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultStudent.get("name").equals(department.getName()), "Name is wrong");
    }

    @Test
    public void testGetNonExistingStudent() throws Exception {
        Map<String, Object> response = departmentTestUtils.getDepartmentDetails(
                UUID.randomUUID(),
                authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.containsKey("code"), "Code is empty");
        Assert.isTrue(((Integer) response.get("code")) == 1501, "Code is wrong");
        Assert.isTrue(response.containsKey("status"), "Status is empty");
        Assert.isTrue(((Integer) response.get("status")) == 404, "Status is wrong");
        Assert.isTrue(response.get("message").equals("department not found"), "Message is wrong");
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> addedStudent = departmentTestUtils.addDepartment(
                department,
                authTestUtils.getAdminAccessToken());

        departmentTestUtils.deleteDepartment(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = departmentTestUtils.getDepartmentDetails(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultStudent.containsKey("code"), "Student is found but should not");
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> addedStudent = departmentTestUtils.addDepartment(
                department,
                authTestUtils.getAdminAccessToken());

        department.setId(UUID.fromString((String) addedStudent.get("id")));
        department.setName("Updated" + department.getName());

        Map<String, Object> updatedStudent = departmentTestUtils.updateDepartment(
                department,
                authTestUtils.getAdminAccessToken());

        departmentTestUtils.deleteDepartment(UUID.fromString((String) addedStudent.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(updatedStudent.get("name").equals(department.getName()), "Student name is not updated");
    }

    @Test
    public void testAddStudentByStudentShouldNotWork() throws Exception {
        Department department = DepartmentTestUtils.generateTestDepartment();

        Map<String, Object> response = departmentTestUtils.addDepartment(department, authTestUtils.getStudentAccessToken());
        Assert.isTrue(response.isEmpty(), "Student is created but should not");
        response = departmentTestUtils.addDepartment(department, authTestUtils.getTeacherAccessToken());
        Assert.isTrue(response.isEmpty(), "Student is created but should not");
    }
}

