package com.example.FMIbook.domain.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CourseRepository extends JpaRepository<Course, UUID> {
    Page<Course> findByNameIgnoreCaseContaining(String name, Pageable page);
    Page<Course> findByYear(Integer year, Pageable page);
}
