package com.binaklet.binaklet.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException {

    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;

    public ApiException(String message, Throwable cause,  HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.throwable = cause;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
