package com.example.FMIbook.utils.exception;

public class UnauthorizedException extends DomainException{
    public static final int CODE = 801;
    public UnauthorizedException() {
        super(CODE);
    }

    public UnauthorizedException(String message) {
        super(message, CODE);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public UnauthorizedException(Throwable cause) {
        super(cause, CODE);
    }

    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
