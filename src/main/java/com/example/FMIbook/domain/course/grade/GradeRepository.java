package com.example.FMIbook.domain.course.grade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, UUID> {
}