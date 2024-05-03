package com.example.FMIbook.domain.course.task.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class TaskNotFoundException extends NotFoundException {
    public static final int CODE = 1801;
    public TaskNotFoundException() {
        super("task not found", CODE);
    }

    public TaskNotFoundException(String message) {
        super(message, CODE);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public TaskNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public TaskNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
