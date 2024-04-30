package com.example.FMIbook.domain.course.section.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class SectionNotFoundException extends NotFoundException {
    public static final int CODE = 1401;
    public SectionNotFoundException() {
        super("section not found", CODE);
    }

    public SectionNotFoundException(String message) {
        super(message, CODE);
    }

    public SectionNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public SectionNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public SectionNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
