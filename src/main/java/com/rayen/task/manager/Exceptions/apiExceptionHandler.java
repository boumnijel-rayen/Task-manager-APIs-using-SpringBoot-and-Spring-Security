package com.rayen.task.manager.Exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class apiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(apiBaseException.class)
    public ResponseEntity<errorDetails> handlerApiExeptions(apiBaseException ex, WebRequest req){
        errorDetails error = new errorDetails(ex.getMessage(),req.getDescription(false));
        return new ResponseEntity<>(error,ex.getCodeState());
    }
}
