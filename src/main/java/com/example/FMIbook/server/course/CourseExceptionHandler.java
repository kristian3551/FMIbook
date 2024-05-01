package com.example.FMIbook.server.course;

import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
import com.example.FMIbook.domain.course.exception.CourseUpdateException;
import com.example.FMIbook.domain.course.posts.exception.PostNotFoundException;
import com.example.FMIbook.domain.course.section.exception.SectionNotFoundException;
import com.example.FMIbook.utils.exception.BaseExceptionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class CourseExceptionHandler extends BaseExceptionHandler {
    public CourseExceptionHandler() {
        super(LoggerFactory.getLogger(CourseController.class));
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCourseNotFound(CourseNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }

    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSectionNotFoundException(SectionNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePostNotFoundException(PostNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }

    @ExceptionHandler(CourseUpdateException.class)
    public ResponseEntity<Map<String, Object>> handleCourseUpdateOrderException(CourseUpdateException ex) {
        return this.handleDomainException(ex);
    }
}

