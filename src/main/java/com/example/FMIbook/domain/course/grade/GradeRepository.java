package com.example.FMIbook.domain.course.grade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID> {
    @Query(value = "SELECT * FROM grades WHERE course_id = ?1",
            countQuery = "SELECT count(*) FROM posts WHERE course_id = ?1",
            nativeQuery = true)
    Page<Grade> findByCourse(UUID courseId, Pageable page);

    @Query(value = "SELECT * FROM grades WHERE course_id = ?1 AND student_id = ?2",
            countQuery = "SELECT count(*) FROM posts WHERE course_id = ?1 AND student_id = ?2",
            nativeQuery = true)
    Page<Grade> findByCourseAndStudent(UUID courseId, UUID studentId, Pageable page);
}
