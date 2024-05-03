package com.example.FMIbook.domain.policy.exception;

import com.example.FMIbook.utils.exception.ForbiddenException;

public class CannotCreateException extends ForbiddenException {
    public static final int CODE = 704;
    public CannotCreateException() {
        super("no rights to create entity", CODE);
    }

    public CannotCreateException(String message) {
        super(message, CODE);
    }

    public CannotCreateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotCreateException(Throwable cause) {
        super(cause, CODE);
    }

    public CannotCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
