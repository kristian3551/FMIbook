package com.example.FMIbook.domain.users.student.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class StudentNotFoundException extends NotFoundException {
    public static final int CODE = 1001;
    public StudentNotFoundException() {
        super("student not found", CODE);
    }

    public StudentNotFoundException(String message) {
        super(message, CODE);
    }

    public StudentNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public StudentNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public StudentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
