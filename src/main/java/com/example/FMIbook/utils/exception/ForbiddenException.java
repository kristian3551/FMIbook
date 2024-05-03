package com.example.FMIbook.utils.exception;

public class ForbiddenException extends ApiException{
    public ForbiddenException(int code) {
        super(code);
    }

    public ForbiddenException(String message, int code) {
        super(message, code);
    }

    public ForbiddenException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public ForbiddenException(Throwable cause, int code) {
        super(cause, code);
    }

    public ForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
