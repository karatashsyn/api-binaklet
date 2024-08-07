package com.binaklet.binaklet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleException (ApiRequestException exc){
        ApiException newException = new ApiException(exc.getMessage(), exc, HttpStatus.BAD_REQUEST);
        Map<String,String> exceptionMap = new HashMap<>();
        exceptionMap.put("message", newException.getMessage());
        exceptionMap.put("httpStatus", newException.getHttpStatus().toString());

        return new ResponseEntity<>(exceptionMap, HttpStatus.BAD_REQUEST);
    }
}
