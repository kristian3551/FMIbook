package com.example.FMIbook.domain.course.achievement.exception;

import com.example.FMIbook.utils.exception.NotFoundException;

public class AchievementNotFoundException extends NotFoundException {
    public static final int CODE = 1701;
    public AchievementNotFoundException() {
        super(CODE);
    }

    public AchievementNotFoundException(String message) {
        super(message, CODE);
    }

    public AchievementNotFoundException(String message, Throwable cause) {
        super(message, cause, CODE);
    }

    public AchievementNotFoundException(Throwable cause) {
        super(cause, CODE);
    }

    public AchievementNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, CODE);
    }
}
