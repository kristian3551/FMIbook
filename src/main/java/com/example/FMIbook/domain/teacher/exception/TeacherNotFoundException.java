package com.example.FMIbook.domain.teacher.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class TeacherNotFoundException extends NotFoundException {
    public static final int CODE = 1201;
    public TeacherNotFoundException() {
        super(CODE);
    }

    public TeacherNotFoundException(String message) {
        super(message, CODE);
    }

    public TeacherNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public TeacherNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public TeacherNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
