package com.example.FMIbook.server.student;

import com.example.FMIbook.utils.exception.BaseExceptionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class StudentExceptionHandler extends BaseExceptionHandler {
    public StudentExceptionHandler() {
        super(LoggerFactory.getLogger(StudentController.class));
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFoundException(StudentNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }
}
