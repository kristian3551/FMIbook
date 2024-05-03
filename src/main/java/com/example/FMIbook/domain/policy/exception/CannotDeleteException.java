package com.example.FMIbook.domain.policy.exception;

import com.example.FMIbook.utils.exception.ForbiddenException;

public class CannotDeleteException extends ForbiddenException {
    public static final int CODE = 703;
    public CannotDeleteException() {
        super("no rights to delete entity", CODE);
    }

    public CannotDeleteException(String message) {
        super(message, CODE);
    }

    public CannotDeleteException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public CannotDeleteException(Throwable cause) {
        super(cause, CODE);
    }

    public CannotDeleteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
