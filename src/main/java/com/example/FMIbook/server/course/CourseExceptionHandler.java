package com.example.FMIbook.server.course;

import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
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
    public ResponseEntity<Map<String, Object>> handleStudentNotFoundException(CourseNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }

    @ExceptionHandler(SectionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFoundException(SectionNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }
}

