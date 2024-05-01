package com.example.FMIbook.domain.course.exception;

import com.example.FMIbook.utils.exception.DomainException;

public class CourseUpdateException extends DomainException {
    public static final int CODE = 1102;
    public CourseUpdateException() {
        super("course cannot be updated", CODE);
    }

    public CourseUpdateException(String message) {
        super(message, CODE);
    }

    public CourseUpdateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CourseUpdateException(Throwable cause) {
        super(cause, CODE);
    }

    public CourseUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
