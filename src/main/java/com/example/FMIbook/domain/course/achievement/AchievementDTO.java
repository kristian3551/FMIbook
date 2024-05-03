package com.example.FMIbook.domain.course.achievement;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.student.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementDTO {
    private UUID id;
    private String name;
    private String description;
    private StudentDTO student;
    private CourseDTO course;

    public static AchievementDTO serializeFromEntity(Achievement achievement) {
        if (achievement == null) {
            return null;
        }

        if (achievement.getStudent() != null) {
            achievement.getStudent().setAchievements(new ArrayList<>());
            achievement.getStudent().setCourses(new ArrayList<>());
            achievement.getStudent().setGrades(new ArrayList<>());
        }

        if (achievement.getCourse() != null) {
            achievement.getCourse().setStudents(new ArrayList<>());
            achievement.getCourse().setGrades(new ArrayList<>());
            achievement.getCourse().setTeachers(new ArrayList<>());
            achievement.getCourse().setSections(new ArrayList<>());
            achievement.getCourse().setDepartment(null);
        }

        return new AchievementDTO(
                achievement.getId(),
                achievement.getName(),
                achievement.getDescription(),
                StudentDTO.serializeFromEntity(achievement.getStudent()),
                CourseDTO.serializeFromEntity(achievement.getCourse())
        );
    }
}
