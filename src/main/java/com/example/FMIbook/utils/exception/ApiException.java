package com.example.FMIbook.utils.exception;

public class ApiException extends RuntimeException{
    private int code;

    public ApiException(int code) {
        this.code = code;
    }

    public ApiException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ApiException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ApiException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
