package com.example.FMIbook.domain.course.course_material.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class CourseMaterialNotFoundException extends NotFoundException {
    public static final int CODE = 2001;
    public CourseMaterialNotFoundException() {
        super("course material not found", CODE);
    }

    public CourseMaterialNotFoundException(String message) {
        super(message, CODE);
    }

    public CourseMaterialNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CourseMaterialNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public CourseMaterialNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
