package com.example.FMIbook.domain.course.achievement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, UUID> {
    @Query(value = """
            SELECT * FROM achievements WHERE course_id = ?1
            """, nativeQuery = true)
    List<Achievement> findByCourse(UUID courseId, Pageable pageable);
}
