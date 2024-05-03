package com.example.FMIbook.server;

import com.example.FMIbook.utils.exception.BaseExceptionHandler;
import com.example.FMIbook.domain.users.user.exception.UserNotFoundException;
import com.example.FMIbook.utils.exception.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {
    public GlobalExceptionHandler() {
        super(LoggerFactory.getLogger(GlobalExceptionHandler.class));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        return super.handleValidationError(ex);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFoundExceptions(
            NotFoundException ex) {
        return super.handleNotFoundException(ex);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolationException(
            ConstraintViolationException ex) {
        return super.handleConstraintException(ex);
    }
}
