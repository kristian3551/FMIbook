package com.example.FMIbook.server.grade;

import com.example.FMIbook.domain.users.student.grade.exception.GradeNotFoundException;
import com.example.FMIbook.utils.exception.BaseExceptionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GradeExceptionHandler extends BaseExceptionHandler {
    public GradeExceptionHandler() {
        super(LoggerFactory.getLogger(GradeController.class));
    }

    @ExceptionHandler(GradeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGradeNotFoundException(GradeNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }
}
