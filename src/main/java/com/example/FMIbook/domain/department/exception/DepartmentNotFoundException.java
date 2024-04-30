package com.example.FMIbook.domain.department.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class DepartmentNotFoundException extends NotFoundException {
    public static final int CODE = 1501;
    public DepartmentNotFoundException() {
        super("department not found", CODE);
    }

    public DepartmentNotFoundException(String message) {
        super(message, CODE);
    }

    public DepartmentNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public DepartmentNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public DepartmentNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
