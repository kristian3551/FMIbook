package com.example.FMIbook.utils.user.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public static final int CODE = 901;
    public UserNotFoundException() {
        super("user not found", CODE);
    }

    public UserNotFoundException(String message) {
        super(message, CODE);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public UserNotFoundException(Throwable cause, int code) {
        super(cause, code);
    }

    public UserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
