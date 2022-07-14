package com.rayen.task.manager.Exceptions;

import org.springframework.http.HttpStatus;

public class conflictException extends apiBaseException{
    public conflictException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getCodeState() {
        return HttpStatus.CONFLICT;
    }
}
