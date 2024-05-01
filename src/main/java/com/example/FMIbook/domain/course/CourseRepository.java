package com.example.FMIbook.domain.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query(value = """
            UPDATE courses
            SET name = ?2,
                year = ?3,
                semester = ?4,
                category = ?5,
                type = ?6,
                description = ?7,
                department_id = ?8
            WHERE id = ?1
            """, nativeQuery = true)
    void updatePartially(UUID id,
                         String name,
                         Integer year,
                         String semester,
                         String category,
                         String type,
                         String description,
                         UUID department);
}
