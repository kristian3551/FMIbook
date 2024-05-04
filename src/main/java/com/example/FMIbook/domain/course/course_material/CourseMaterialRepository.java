package com.example.FMIbook.domain.course.course_material;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseMaterialRepository extends JpaRepository<CourseMaterial, UUID> {
}
