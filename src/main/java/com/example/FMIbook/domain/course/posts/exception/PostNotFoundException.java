package com.example.FMIbook.domain.course.posts.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public static final int CODE = 1601;
    public PostNotFoundException() {
        super("post not found", CODE);
    }

    public PostNotFoundException(String message) {
        super(message, CODE);
    }

    public PostNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public PostNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public PostNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
