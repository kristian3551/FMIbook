package com.example.FMIbook.domain.student.grade.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class GradeNotFoundException extends NotFoundException {
    public static final int CODE = 1301;
    public GradeNotFoundException() {
        super("grade not found", CODE);
    }

    public GradeNotFoundException(String message) {
        super(message, CODE);
    }

    public GradeNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public GradeNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public GradeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
