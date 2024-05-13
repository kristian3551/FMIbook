package com.example.FMIbook.domain.course.grade;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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

    private Boolean isFinal = false;

    private LocalDateTime createdAt;

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
                .createdAt(grade.getCreatedAt())
                .build();
    }

    public static GradeDTO serializeFromEntity(Grade grade) {
        if (grade == null) {
            return null;
        }

        return GradeDTO.builder()
                .id(grade.getId())
                .grade(grade.getGrade())
                .percentage(grade.getPercentage())
                .course(CourseDTO.serializeLightweight(grade.getCourse()))
                .student(StudentDTO.serializeLightweight(grade.getStudent()))
                .isFinal(grade.getIsFinal())
                .createdAt(grade.getCreatedAt())
                .build();
    }

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
}
