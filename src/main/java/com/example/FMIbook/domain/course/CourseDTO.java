package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.department.DepartmentDTO;
import com.example.FMIbook.domain.student.StudentDTO;
import com.example.FMIbook.domain.student.grade.GradeDTO;
import com.example.FMIbook.domain.teacher.TeacherDTO;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseDTO {
    private UUID id;

    private String name;

    private Integer year;

    private String semester;

    private String category;

    public CourseDTO() {
    }

    @Pattern(regexp = "(compulsory|selectable)", message = "type is invalid")
    private String type;

    private String description;

    private List<StudentDTO> students;

    private List<TeacherDTO> teachers;

    private List<SectionDTO> sections;

    private DepartmentDTO department;

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public void setSections(List<SectionDTO> sections) {
        this.sections = sections;
    }

    public List<SectionDTO> getSections() {
        return sections;
    }

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

    private List<GradeDTO> grades;

    public void setGrades(List<GradeDTO> grades) {
        this.grades = grades;
    }

    public List<GradeDTO> getGrades() {
        return grades;
    }

    public CourseDTO(UUID id,
                     String name,
                     Integer year,
                     String semester,
                     String category,
                     String type,
                     String description,
                     List<StudentDTO> students,
                     List<TeacherDTO> teachers,
                     DepartmentDTO department) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
        this.department = department;
    }

    public CourseDTO(String name,
                     Integer year,
                     String semester,
                     String category,
                     String type,
                     String description,
                     List<StudentDTO> students,
                     List<TeacherDTO> teachers) {
        this.name = name;
        this.year = year;
        this.semester = semester;
        this.category = category;
        this.type = type;
        this.description = description;
        this.students = students;
        this.teachers = teachers;
    }

    public void setTeachers(List<TeacherDTO> teachers) {
        this.teachers = teachers;
    }

    public List<TeacherDTO> getTeachers() {
        return teachers;
    }

    public List<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(List<StudentDTO> students) {
        this.students = students;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public String getSemester() {
        return semester;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
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

        CourseDTO result = new CourseDTO(
                course.getId(),
                course.getName(),
                course.getYear(),
                course.getSemester(),
                course.getCategory(),
                course.getType(),
                course.getDescription(),
                students,
                teachers,
                departmentDTO
        );

        result.setGrades(grades);
        result.setSections(sections);
        return result;
    }
}
