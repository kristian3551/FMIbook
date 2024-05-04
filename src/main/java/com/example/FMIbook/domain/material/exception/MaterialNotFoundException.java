package com.example.FMIbook.domain.material.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class MaterialNotFoundException extends NotFoundException
{
    public static final int CODE = 1901;
    public MaterialNotFoundException() {
        super(CODE);
    }

    public MaterialNotFoundException(String message) {
        super(message, CODE);
    }

    public MaterialNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public MaterialNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public MaterialNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
