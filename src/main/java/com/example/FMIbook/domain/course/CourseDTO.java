package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.course.posts.PostDTO;
import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.course.task.TaskResponseDTO;
import com.example.FMIbook.domain.department.DepartmentDTO;
import com.example.FMIbook.domain.users.student.StudentDTO;
import com.example.FMIbook.domain.grade.GradeDTO;
import com.example.FMIbook.domain.users.teacher.TeacherDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    private UUID id;

    private String name;

    private Integer year;

    private String semester;

    private String category;

    @Pattern(regexp = "(compulsory|selectable)", message = "type is invalid")
    private String type;

    private String description;

    private List<StudentDTO> students;

    private List<TeacherDTO> teachers;

    private List<SectionDTO> sections;

    private DepartmentDTO department;

    private List<GradeDTO> grades;

    private List<TaskResponseDTO> tasks;

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", semester='" + semester + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", students=" + students +
                ", teachers=" + teachers +
                ", grades=" + grades +
                '}';
    }

    public static CourseDTO serializeLightweight(Course course) {
        if (course == null) {
            return null;
        }
        return CourseDTO
                .builder()
                .id(course.getId())
                .name(course.getName())
                .year(course.getYear())
                .semester(course.getSemester())
                .type(course.getType())
                .description(course.getDescription())
                .department(null)
                .students(new ArrayList<>())
                .teachers(new ArrayList<>())
                .grades(new ArrayList<>())
                .sections(new ArrayList<>())
                .category(course.getCategory())
                .tasks(new ArrayList<>())
                .build();
    }

    public static CourseDTO serializeFromEntity(Course course) {
        if (course == null) {
            return null;
        }
        List<StudentDTO> students = course.getStudents().stream().map(StudentDTO::serializeLightweight).toList();
        List<TeacherDTO> teachers = course.getTeachers().stream().map(TeacherDTO::serializeLightweight).toList();
        List<GradeDTO> grades = course.getGrades().stream().map(GradeDTO::serializeLightweight).toList();
        List<SectionDTO> sections = course.getSections().stream().map(SectionDTO::serializeLightweight).toList();

        DepartmentDTO departmentDTO = DepartmentDTO.serializeLightweight(course.getDepartment());


        List<TaskResponseDTO> tasks = course.getTasks().stream().map(TaskResponseDTO::serializeFromEntity).toList();

        return CourseDTO
                .builder()
                .id(course.getId())
                .name(course.getName())
                .year(course.getYear())
                .semester(course.getSemester())
                .category(course.getCategory())
                .type(course.getType())
                .description(course.getDescription())
                .students(students)
                .teachers(teachers)
                .department(departmentDTO)
                .tasks(tasks)
                .grades(grades)
                .sections(sections)
                        .build();
    }
}
