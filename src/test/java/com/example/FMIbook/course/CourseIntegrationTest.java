package com.example.FMIbook.course;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.utils.AuthTestUtils;
import com.example.FMIbook.utils.CourseTestUtils;
import com.example.FMIbook.utils.StudentTestUtils;
import com.example.FMIbook.utils.TeacherTestUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CourseTestUtils courseTestUtils;

    @Autowired
    private StudentTestUtils studentTestUtils;

    @Autowired
    private TeacherTestUtils teacherTestUtils;

    @Autowired
    private AuthTestUtils authTestUtils;

    @BeforeAll
    public void beforeAllTests() {
        authTestUtils.addAuthEntities();
    }

    @Test
    public void testAddCourse() throws Exception {
        Course course = CourseTestUtils.generateTestEntity();

        Map<String, Object> response = courseTestUtils.addOne(course, authTestUtils.getAdminAccessToken());

        courseTestUtils.delete(UUID.fromString((String) response.get("id")), authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.get("name").equals(course.getName()), "Name is wrong");
        Assert.isTrue(((List<Object>) response.get("students")).isEmpty(), "Students are returned");
        Assert.isTrue(((List<Object>) response.get("teachers")).isEmpty(), "Teachers are returned");
        Assert.isTrue(response.get("department") == null, "Grades are returned");
    }

    @Test
    public void testGetCourseDetails() throws Exception {
        Course course = CourseTestUtils.generateTestEntity();

        Map<String, Object> addedEntity = courseTestUtils.addOne(course, authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = courseTestUtils.getDetails(
                UUID.fromString((String) addedEntity.get("id")),
                authTestUtils.getAdminAccessToken());
        courseTestUtils.delete(
                UUID.fromString((String) addedEntity.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultStudent.get("name").equals(course.getName()), "Name is wrong");
    }

    @Test
    public void testGetNonExistingCourse() throws Exception {
        Map<String, Object> response = courseTestUtils.getDetails(
                UUID.randomUUID(),
                authTestUtils.getAdminAccessToken());

        Assert.isTrue(response.containsKey("code"), "Code is empty");
        Assert.isTrue(((Integer) response.get("code")) == 1101, "Code is wrong");
        Assert.isTrue(response.containsKey("status"), "Status is empty");
        Assert.isTrue(((Integer) response.get("status")) == 404, "Status is wrong");
        Assert.isTrue(response.get("message").equals("course not found"), "Message is wrong");
    }

    @Test
    public void testDeleteStudent() throws Exception {
        Course course = CourseTestUtils.generateTestEntity();

        Map<String, Object> addedEntity = courseTestUtils.addOne(
                course,
                authTestUtils.getAdminAccessToken());

        courseTestUtils.delete(
                UUID.fromString((String) addedEntity.get("id")),
                authTestUtils.getAdminAccessToken());

        Map<String, Object> resultStudent = courseTestUtils.getDetails(
                UUID.fromString((String) addedEntity.get("id")),
                authTestUtils.getAdminAccessToken());
        Assert.isTrue(resultStudent.containsKey("code"), "Course is found but should not");
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Course course = CourseTestUtils.generateTestEntity();

        Map<String, Object> addedCourse = courseTestUtils.addOne(
                course,
                authTestUtils.getAdminAccessToken());

        course.setId(UUID.fromString((String) addedCourse.get("id")));
        course.setName("Updated" + course.getName());
        course.setDescription("Updated" + course.getDescription());

        Student student = StudentTestUtils.generateTestStudent();

        Map<String, Object> addedStudent = studentTestUtils.addOne(student, authTestUtils.getAdminAccessToken());
        student.setId(UUID.fromString((String) addedStudent.get("id")));
        course.getStudents().add(student);

        Map<String, Object> updatedCourse = courseTestUtils.updateOne(
                course,
                authTestUtils.getAdminAccessToken()
        );

        String newName = (String) updatedCourse.get("name");
        String newDescription = (String) updatedCourse.get("description");
        int newStudentsCount1 = ((List<Object>) updatedCourse.get("students")).size();

        course.setYear(2030);
        updatedCourse = courseTestUtils.updateOne(
                course,
                authTestUtils.getAdminAccessToken()
        );
        Integer newYear = ((Integer) updatedCourse.get("year"));

        addedCourse = courseTestUtils.getDetails(course.getId(), authTestUtils.getAdminAccessToken());
        int newStudentsCount2 = ((List<Object>) addedCourse.get("students")).size();

        Teacher teacher = TeacherTestUtils.generateTestTeacher();
        Map<String, Object> addedTeacher = teacherTestUtils.addOne(teacher, authTestUtils.getAdminAccessToken());
        teacher.setId(UUID.fromString((String) addedTeacher.get("id")));

        course.getTeachers().add(teacher);
        updatedCourse = courseTestUtils.updateOne(
                course,
                authTestUtils.getAdminAccessToken()
        );
        addedCourse = courseTestUtils.getDetails(course.getId(), authTestUtils.getAdminAccessToken());
        int newTeachersCount = ((List<Object>) addedCourse.get("teachers")).size();
        int newStudentsCount3 = ((List<Object>) addedCourse.get("students")).size();

        course.setStudents(new ArrayList<>());
        course.setTeachers(new ArrayList<>());
        courseTestUtils.updateOne(course, authTestUtils.getAdminAccessToken());
        courseTestUtils.delete(UUID.fromString((String) addedStudent.get("id")), authTestUtils.getAdminAccessToken());
        studentTestUtils.delete(student.getId(), authTestUtils.getAdminAccessToken());
        teacherTestUtils.delete(teacher.getId(), authTestUtils.getAdminAccessToken());

        Assert.isTrue(newStudentsCount1 == 1, "Course students are not updated");
        Assert.isTrue(newStudentsCount2 == 1, "Course students are not updated");
        Assert.isTrue(newStudentsCount3 == 1, "Course students are not updated");
        Assert.isTrue(newTeachersCount == 1, "Course teachers are not updated");
        Assert.isTrue(newYear == 2030, "Course year is not updated");
        Assert.isTrue(newName.equals(course.getName()), "Course name is not updated");
        Assert.isTrue(newDescription.equals(course.getDescription()), "Course description is not updated");
        Assert.isTrue(((List<Object>) updatedCourse.get("students")).size() == 1, "Course students are not updated");
    }

    @Test
    public void testAddNonValidCourseShouldReturnError() throws Exception {
        Course course = CourseTestUtils.generateTestEntity();
        course.setName("");
        course.setSemester("asdasd");
        course.setCategory("qweqwe");
        course.setType("asdasdasddsa");

        Map<String, Object> errors = courseTestUtils.addOne(course, authTestUtils.getAdminAccessToken());
        System.out.println(errors);
        Assert.isTrue(errors.get("status").equals(400), "Status is not returned");
        Assert.isTrue(errors.get("message").equals("validation errors"), "Message is not right");
        Assert.isTrue(((Map<String, Object>) errors.get("errors")).get("name").equals("name is empty"), "Name error message is not right");
        Assert.isTrue((
                (Map<String, Object>) errors.get("errors")).get("semester").equals("semester is invalid"),
                "Semester error message is not right"
        );
        Assert.isTrue((
                        (Map<String, Object>) errors.get("errors")).get("category").equals("category is invalid"),
                "Category error message is not right"
        );
        Assert.isTrue((
                        (Map<String, Object>) errors.get("errors")).get("type").equals("type is invalid"),
                "Type error message is not right"
        );
    }

    @Test
    public void testAddCourseByStudentShouldNotWork() throws Exception {
        Course course = CourseTestUtils.generateTestEntity();

        Map<String, Object> response = courseTestUtils.addOne(course, authTestUtils.getStudentAccessToken());
        Assert.isTrue(response.isEmpty(), "Course is created but should not");
    }
}
