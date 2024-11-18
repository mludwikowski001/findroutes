package com.mludwikowski.countryroutes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoRouteException extends RuntimeException {
    public NoRouteException(String message) {
        super(message);
    }
}
