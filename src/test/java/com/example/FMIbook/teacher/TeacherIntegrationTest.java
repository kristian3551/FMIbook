package com.example.FMIbook.teacher;

import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.utils.AuthTestUtils;
import com.example.FMIbook.utils.TeacherTestUtils;
import org.junit.jupiter.api.Test;
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
public class TeacherIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TeacherTestUtils teacherTestUtils;

    @Autowired
    private AuthTestUtils authTestUtils;

    @Test
    public void testAddStudent() throws Exception {
        Teacher teacher = TeacherTestUtils.generateTestTeacher();

        Map<String, Object> response = teacherTestUtils.addTeacher(
                teacher, authTestUtils.getAdminAccessToken()
        );

        Assert.isTrue(response.get("name").equals(teacher.getName()), "Name is wrong");
        Assert.isTrue(response.get("degree").equals(teacher.getDegree()), "Degree is wrong");
        Assert.isTrue(response.get("email").equals(teacher.getEmail()), "Email is wrong");
        Assert.isTrue(((List<Object>)response.get("courses")).isEmpty(), "Courses are returned");

        teacherTestUtils.deleteTeacher(UUID.fromString((String) response.get("id")), authTestUtils.getAdminAccessToken());
    }

    @Test
    public void testGetStudentDetails() throws Exception {
        Teacher teacher = TeacherTestUtils.generateTestTeacher();

        Map<String, Object> addedStudent = teacherTestUtils.addTeacher(teacher, authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = teacherTestUtils.getTeacherDetails(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken()
        );
        Assert.isTrue(resultStudent.get("name").equals(teacher.getName()), "Name is wrong");
        teacherTestUtils.deleteTeacher(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken()
        );
    }

    @Test
    public void testGetNonExistingStudent() throws Exception {
        Map<String, Object> response = teacherTestUtils.getTeacherDetails(
                UUID.randomUUID(), authTestUtils.getAdminAccessToken()
        );

        Assert.isTrue(response.containsKey("code"), "Code is empty");
        Assert.isTrue(((Integer)response.get("code")) == 1201, "Code is wrong");
        Assert.isTrue(response.containsKey("status"), "Status is empty");
        Assert.isTrue(((Integer)response.get("status")) == 404, "Status is wrong");
        Assert.isTrue(response.get("message").equals("teacher not found"), "Message is wrong");
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Teacher teacher = TeacherTestUtils.generateTestTeacher();

        Map<String, Object> addedStudent = teacherTestUtils.addTeacher(
                teacher, authTestUtils.getAdminAccessToken()
        );

        teacherTestUtils.deleteTeacher(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken()
        );

        Map<String, Object> resultStudent = teacherTestUtils.getTeacherDetails(
                UUID.fromString((String) addedStudent.get("id")),
                authTestUtils.getAdminAccessToken()
        );
        Assert.isTrue(resultStudent.containsKey("code"), "Student is found but should not");
    }

    @Test
    public void testAddStudentByStudentShouldNotWork() throws Exception {
        Teacher teacher = TeacherTestUtils.generateTestTeacher();

        Map<String, Object> response = teacherTestUtils.addTeacher(teacher, authTestUtils.getStudentAccessToken());
        Assert.isTrue(response.isEmpty(), "Student is created but should not");
        response = teacherTestUtils.addTeacher(teacher, authTestUtils.getTeacherAccessToken());
        Assert.isTrue(response.isEmpty(), "Student is created but should not");
    }
}
