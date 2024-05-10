package com.example.FMIbook.domain.course.grade;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeDTO {
    private UUID id;

    private StudentDTO student;

    private CourseDTO course;

    private Integer percentage;

    private Double grade;

    @Override
    public String toString() {
        return "Grade{" +
                "uuid=" + id +
                ", student=" + student +
                ", course=" + course +
                ", percentage=" + percentage +
                ", grade=" + grade +
                '}';
    }

    public GradeDTO(StudentDTO student, CourseDTO course, Integer percentage, Double grade) {
        this.student = student;
        this.course = course;
        this.percentage = percentage;
        this.grade = grade;
    }

    public static GradeDTO serializeLightweight(Grade grade, boolean withStudent, boolean withCourse) {
        if (grade == null) {
            return null;
        }
        return GradeDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .percentage(grade.getPercentage())
                .course(withCourse ? CourseDTO.serializeLightweight(grade.getCourse()) : null)
                .student(withStudent ? StudentDTO.serializeLightweight(grade.getStudent()) : null)
                .build();
    }

    public static GradeDTO serializeFromEntity(Grade grade) {
        if (grade == null) {
            return null;
        }

        return new GradeDTO(
                grade.getId(),
                StudentDTO.serializeLightweight(grade.getStudent()),
                CourseDTO.serializeLightweight(grade.getCourse()),
                grade.getPercentage(),
                grade.getGrade()
        );
    }
}
