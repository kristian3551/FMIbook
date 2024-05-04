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

    public static AchievementDTO serializeLightweight(Achievement achievement) {
        if (achievement == null) {
            return null;
        }

        return AchievementDTO.builder()
                .id(achievement.getId())
                .name(achievement.getName())
                .description(achievement.getDescription())
                .student(StudentDTO.serializeLightweight(achievement.getStudent()))
                .course(CourseDTO.serializeLightweight(achievement.getCourse()))
                .build();
    }

    public static AchievementDTO serializeFromEntity(Achievement achievement) {
        if (achievement == null) {
            return null;
        }

        return new AchievementDTO(
                achievement.getId(),
                achievement.getName(),
                achievement.getDescription(),
                StudentDTO.serializeLightweight(achievement.getStudent()),
                CourseDTO.serializeLightweight(achievement.getCourse())
        );
    }
}
