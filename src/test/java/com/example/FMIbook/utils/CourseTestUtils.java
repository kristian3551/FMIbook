package com.example.FMIbook.utils;

import com.example.FMIbook.domain.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CourseTestUtils extends BaseTestUtils {
    private static final AtomicInteger index = new AtomicInteger(0);
    private static final String COURSE_TEST_NAME = "TestCourse";

    @Autowired
    public CourseTestUtils(MockMvc mvc) {
        super(mvc, "courses");
    }

    public static Course generateTestEntity() {
        String name = COURSE_TEST_NAME + index.getAndIncrement();
        return Course
                .builder()
                .name(name)
                .year(2024)
                .semester("winter")
                .category("CSC")
                .type("selectable")
                .description("mnogo dobar kurs")
                .students(new ArrayList<>())
                .teachers(new ArrayList<>())
                .department(null)
                .tasks(new ArrayList<>())
                .sections(new ArrayList<>())
                .build();
    }

    public Map<String, Object> addOne(Course course, String token) throws Exception {
        String courseContent = String.format("""
                        {
                            "name": "%s",
                            "year": %d,
                            "semester": "%s",
                            "category": "%s",
                            "type": "%s",
                            "description": "%s!"
                        }
                        """,
                course.getName(),
                course.getYear(),
                course.getSemester(),
                course.getCategory(),
                course.getType(),
                course.getDescription());
        return super.addOne(courseContent, token);
    }

    public Map<String, Object> updateOne(Course course, String token) throws Exception {
        String courseContent = String.format("""
                        {
                            "name": "%s",
                            "year": %d,
                            "semester": "%s",
                            "category": "%s",
                            "type": "%s",
                            "description": "%s",
                            "students": [%s],
                            "teachers": [%s],
                            "department": %s
                        }
                        """,
                course.getName(),
                course.getYear(),
                course.getSemester(),
                course.getCategory(),
                course.getType(),
                course.getDescription(),
                String.join(", ",
                        course.getStudents()
                                .stream().map(student -> "\"" + student.getId() + "\"").toList()),
                String.join(", ",
                        course.getTeachers()
                                .stream().map(teacher -> "\"" + teacher.getId() + "\"").toList()),
                course.getDepartment() != null ? "\"" + course.getDepartment().getId() + "\"" : "null"
        );
        return super.updateOne(course.getId(), courseContent, token);
    }

    public Map<String, Object> updateOnePartially(Course course, String token) throws Exception {
        String courseContent = String.format("""
                        {
                            "name": "%s",
                            "year": %d,
                            "semester": "%s",
                            "category": "%s",
                            "type": "%s",
                            "description": "%s"
                        }
                        """,
                course.getName(),
                course.getYear(),
                course.getSemester(),
                course.getCategory(),
                course.getType(),
                course.getDescription()
        );
        return super.updateOne(course.getId(), courseContent, token);
    }
}
