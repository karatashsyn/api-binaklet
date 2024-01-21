package com.binaklet.binaklet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleException (ApiRequestException exc){
        ApiException newException = new ApiException(exc.getMessage(), exc, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(newException,HttpStatus.BAD_REQUEST);
    }
}
