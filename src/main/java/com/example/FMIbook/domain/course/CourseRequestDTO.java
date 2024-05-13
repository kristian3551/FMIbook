package com.example.FMIbook.domain.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseRequestDTO {
    private UUID id;

    @Pattern(regexp = ".+", message = "name is blank")
    private String name;

    private Integer year;

    @Pattern(regexp = "(summer|winter)", message = "semester is invalid")
    private String semester;

    @Pattern(regexp = "(CSC|CSB|CP|M|AM)", message = "category is invalid")
    private String category;

    @Pattern(regexp = "(compulsory|selectable)", message = "type is invalid")
    private String type;

    @Pattern(regexp = ".+", message = "description is blank")
    private String description;

    private List<UUID> students;

    private List<UUID> teachers;

    private List<UUID> sections;

    private UUID department;

    private List<UUID> grades;

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
}

