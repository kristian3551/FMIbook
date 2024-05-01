package com.example.FMIbook.utils.exception;

public class DomainException extends ApiException{
    public DomainException(int code) {
        super(code);
    }

    public DomainException(String message, int code) {
        super(message, code);
    }

    public DomainException(String message, Throwable cause, int code) {
        super(message, cause, code);
    }

    public DomainException(Throwable cause, int code) {
        super(cause, code);
    }

    public DomainException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace, code);
    }
}
