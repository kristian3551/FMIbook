package com.example.FMIbook.utils.exception;

import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

public class BaseExceptionHandler {
    private final Logger logger;

    public BaseExceptionHandler(Logger logger) {
        this.logger = logger;
    }

    public ResponseEntity<Map<String, Object>> handleNotFoundException(NotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 404);
        errorResponse.put("code", ex.getCode());
        errorResponse.put("message", ex.getMessage());
        logger.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 404);
        errorResponse.put("code", ex.getCode());
        errorResponse.put("message", ex.getMessage());
        logger.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Map<String, Object>> handleInternalError(RuntimeException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 500);
        errorResponse.put("message", ex.getMessage() != null ? ex.getMessage() : "Error occurred");
        logger.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Map<String, Object>> handleApiError(ApiException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", 400);
        errorResponse.put("code", ex.getCode());
        errorResponse.put("message", ex.getMessage());
        logger.error(errorResponse.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        errorResponse.put("errors", errors);
        errorResponse.put("status", 400);
        errorResponse.put("message", "validation errors");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
