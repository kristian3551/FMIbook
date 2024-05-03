package com.example.FMIbook.domain.policy.exception;

import com.example.FMIbook.utils.exception.ForbiddenException;

public class CannotReadException extends ForbiddenException {
    public static final int CODE = 701;
    public CannotReadException() {
        super("no rights to read entity", CODE);
    }

    public CannotReadException(String message) {
        super(message, CODE);
    }

    public CannotReadException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotReadException(Throwable cause) {
        super(cause, CODE);
    }

    public CannotReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
