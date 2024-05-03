package com.example.FMIbook.domain.course;

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

    public static CourseDTO serializeFromEntity(Course course) {
        if (course == null) {
            return null;
        }
        List<StudentDTO> students = course.getStudents() != null
                ? course.getStudents().stream().map(student -> {
                    student.setCourses(null);
                    return StudentDTO.serializeFromEntity(student);
        }).toList()
                : new ArrayList<>();
        List<TeacherDTO> teachers = course.getTeachers() != null
                ? course.getTeachers().stream().map(teacher -> {
            teacher.setCourses(null);
            return TeacherDTO.serializeFromEntity(teacher);
        }).toList()
                : new ArrayList<>();
        List<GradeDTO> grades = course.getGrades() != null
                ? course.getGrades().stream().map(grade -> {
                    grade.setCourse(null);
                    return GradeDTO.serializeFromEntity(grade);
        }).toList()
                : new ArrayList<>();

        List<SectionDTO> sections = course.getSections() != null
                ? course.getSections().stream().map(section -> {
            section.setCourse(null);
            return SectionDTO.serializeFromEntity(section);
        }).toList()
                : new ArrayList<>();

        DepartmentDTO departmentDTO = null;

        if (course.getDepartment() != null) {
            departmentDTO = new DepartmentDTO(course.getDepartment().getId(), course.getDepartment().getName(), new ArrayList<>());
        }

        List<TaskResponseDTO> tasks = course.getTasks() != null
                ? course.getTasks().stream().map(TaskResponseDTO::serializeFromEntity).toList()
                : new ArrayList<>();

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
