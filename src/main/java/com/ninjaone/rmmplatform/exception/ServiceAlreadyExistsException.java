package com.ninjaone.rmmplatform.exception;

public class ServiceAlreadyExistsException extends RuntimeException {

    public ServiceAlreadyExistsException(String message) {
        super(message);
    }

    public ServiceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
