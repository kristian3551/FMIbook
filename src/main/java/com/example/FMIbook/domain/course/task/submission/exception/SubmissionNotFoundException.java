package com.example.FMIbook.domain.course.task.submission.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class SubmissionNotFoundException extends NotFoundException {
    public static final int CODE = 2101;
    public SubmissionNotFoundException() {
        super("submission not found", CODE);
    }

    public SubmissionNotFoundException(String message) {
        super(message, CODE);
    }

    public SubmissionNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public SubmissionNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public SubmissionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
