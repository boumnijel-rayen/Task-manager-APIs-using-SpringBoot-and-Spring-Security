package com.rayen.task.manager.Exceptions;

import org.springframework.http.HttpStatus;

public class forbiddenException extends apiBaseException{
    public forbiddenException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getCodeState() {
        return HttpStatus.FORBIDDEN;
    }
}
