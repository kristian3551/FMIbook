package com.example.FMIbook.utils.exception;

public class NotFoundException extends ApiException{

    public NotFoundException(int code) {
        super(code);
    }

    public NotFoundException(String message, int code) {
        super(message, code);
    }

    public NotFoundException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public NotFoundException(Throwable cause, int code) {
        super(cause, code);
    }

    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
