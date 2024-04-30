package com.example.FMIbook.domain.course.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class CourseNotFoundException extends NotFoundException {
    public static final int CODE = 1101;
    public CourseNotFoundException() {
        super("course not found", CODE);
    }

    public CourseNotFoundException(String message) {
        super(message, CODE);
    }

    public CourseNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CourseNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public CourseNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
