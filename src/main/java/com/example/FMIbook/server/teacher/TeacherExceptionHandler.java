package com.example.FMIbook.server.teacher;

import com.example.FMIbook.domain.teacher.exception.TeacherNotFoundException;
import com.example.FMIbook.utils.exception.BaseExceptionHandler;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class TeacherExceptionHandler extends BaseExceptionHandler {
    public TeacherExceptionHandler() {
        super(LoggerFactory.getLogger(TeacherController.class));
    }

    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTeacherNotFoundException(TeacherNotFoundException ex) {
        return this.handleNotFoundException(ex);
    }
}
