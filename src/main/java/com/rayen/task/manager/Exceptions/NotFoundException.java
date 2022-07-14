package com.rayen.task.manager.Exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends apiBaseException{
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getCodeState() {
        return HttpStatus.NOT_FOUND;
    }
}
