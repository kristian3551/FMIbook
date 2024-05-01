package com.example.FMIbook.domain.course.posts;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoursePostRepository extends JpaRepository<CoursePost, UUID> {
    @Query(value = "SELECT * FROM posts WHERE course_id = ?1 AND parent_post_id IS NULL",
    countQuery = "SELECT count(*) FROM posts WHERE course_id = ?1 AND parent_post_id IS NULL",
    nativeQuery = true)
    Page<CoursePost> findAllByCourse(UUID course, Pageable pageable);
}
