package com.example.FMIbook.student;

import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.utils.AuthTestUtils;
import com.example.FMIbook.utils.StudentTestUtils;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentIntegrationTest {
    @Autowired
    private StudentTestUtils studentTestUtils;

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
    public void testAddStudent() throws Exception {
        Student student = StudentTestUtils.generateTestStudent();

        Map<String, Object> response = studentTestUtils.addOne(student, authTestUtils.getAdminAccessToken());

        studentTestUtils.delete(UUID.fromString((String) response.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.get("name").equals(student.getName()), "Name is wrong");
        Assert.isTrue(response.get("facultyNumber").equals("6MI0800076"), "FN is wrong");
        Assert.isTrue(response.get("semester").equals(6), "Semester is wrong");
        Assert.isTrue(response.get("degree").equals("bachelor"), "Degree is wrong");
        Assert.isTrue(response.get("email").equals(student.getEmail()), "Email is wrong");
        Assert.isTrue(((List<Object>) response.get("courses")).isEmpty(), "Courses are returned");
        Assert.isTrue(((List<Object>) response.get("achievements")).isEmpty(), "Achievements are returned");
        Assert.isTrue(((List<Object>) response.get("takenCourses")).isEmpty(), "Taken courses are returned");
    }

    @Test
    public void testGetStudentDetails() throws Exception {
        Student student = StudentTestUtils.generateTestStudent();

        Map<String, Object> addedStudent = studentTestUtils.addOne(student, authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = studentTestUtils.getDetails(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());
        studentTestUtils.delete(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultStudent.get("name").equals(student.getName()), "Name is wrong");
    }

    @Test
    public void testGetNonExistingStudent() throws Exception {
        Map<String, Object> response = studentTestUtils.getDetails(
                UUID.randomUUID(),
                authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.containsKey("code"), "Code is empty");
        Assert.isTrue(((Integer) response.get("code")) == 1001, "Code is wrong");
        Assert.isTrue(response.containsKey("status"), "Status is empty");
        Assert.isTrue(((Integer) response.get("status")) == 404, "Status is wrong");
        Assert.isTrue(response.get("message").equals("student not found"), "Message is wrong");
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Student student = StudentTestUtils.generateTestStudent();

        Map<String, Object> addedStudent = studentTestUtils.addOne(
                student,
                authTestUtils.getAdminAccessToken());

        studentTestUtils.delete(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = studentTestUtils.getDetails(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultStudent.containsKey("code"), "Student is found but should not");
    }

    @Test
    public void testUpdateStudent() throws Exception {
        Student student = StudentTestUtils.generateTestStudent();

        Map<String, Object> addedStudent = studentTestUtils.addOne(
                student,
                authTestUtils.getAdminAccessToken());

        student.setId(UUID.fromString((String) addedStudent.get("id")));
        student.setName("Updated" + student.getName());
        student.setDescription("Updated" + student.getDescription());
        student.setGroup(student.getGroup() + 1);

        Map<String, Object> updatedStudent = studentTestUtils.updateOne(
                student,
                authTestUtils.getAdminAccessToken());

        studentTestUtils.delete(UUID.fromString((String) addedStudent.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(updatedStudent.get("name").equals(student.getName()), "Student name is not updated");
        Assert.isTrue(updatedStudent.get("description").equals(student.getDescription()), "Student description is not updated");
        Assert.isTrue(updatedStudent.get("group").equals(student.getGroup()), "Student group is not updated");
    }

    @Test
    public void testAddStudentByStudentShouldNotWork() throws Exception {
        Student student = StudentTestUtils.generateTestStudent();

        Map<String, Object> response = studentTestUtils.addOne(student, authTestUtils.getStudentAccessToken());
        Assert.isTrue(response.isEmpty(), "Student is created but should not");
        response = studentTestUtils.addOne(student, authTestUtils.getTeacherAccessToken());
        Assert.isTrue(response.isEmpty(), "Student is created but should not");
    }

    @Test
    public void testAddNonValidStudent() throws Exception {
        Student student = StudentTestUtils.generateTestStudent();
        student.setEmail("asdasdasdasd");
        student.setName("");
        student.setFacultyNumber("qweqweqwe");
        student.setSemester(null);
        student.setGroup(null);
        student.setDegree("");

        Map<String, Object> errors = studentTestUtils.addOne(student, authTestUtils.getAdminAccessToken());

        Assert.isTrue(errors.get("status").equals(400), "Status is not returned");
        Assert.isTrue(errors.get("message").equals("validation errors"), "Message is not right");
        Assert.isTrue(
                ((Map<String, String>) errors.get("errors")).get("name").equals("name is empty"),
                "Name error message is not right"
        );
        Assert.isTrue(
                ((Map<String, String>) errors.get("errors")).get("email").equals("email is invalid"),
                "Email error message is not right"
        );
        Assert.isTrue(
                ((Map<String, String>) errors.get("errors")).get("facultyNumber").equals("faculty number is invalid"),
                "Email error message is not right"
        );
        Assert.isTrue(
                ((Map<String, String>) errors.get("errors")).get("semester").equals("semester is null"),
                "Semester error message is not right"
        );
        Assert.isTrue(
                ((Map<String, String>) errors.get("errors")).get("group").equals("group is null"),
                "Semester error message is not right"
        );
        Assert.isTrue(
                ((Map<String, String>) errors.get("errors")).get("degree").equals("degree is empty"),
                "Degree error message is not right"
        );
    }
}
