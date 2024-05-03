package com.example.FMIbook.domain.policy.exception;

import com.example.FMIbook.utils.exception.ForbiddenException;

public class CannotUpdateException extends ForbiddenException {
    public static final int CODE = 702;
    public CannotUpdateException() {
        super("no rights to update entity", CODE);
    }

    public CannotUpdateException(String message) {
        super(message, CODE);
    }

    public CannotUpdateException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotUpdateException(Throwable cause) {
        super(cause, CODE);
    }

    public CannotUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
