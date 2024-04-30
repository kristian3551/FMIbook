package com.example.FMIbook.server.student;

import com.example.FMIbook.utils.exception.NotFoundException;

public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException() {
        super("student not found", 1000);
    }

    public StudentNotFoundException(String message, int code) {
        super(message, code);
    }

    public StudentNotFoundException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public StudentNotFoundException(Throwable cause, int code) {
        super(cause, code);
    }

    public StudentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
