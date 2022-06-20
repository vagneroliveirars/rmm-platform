package com.ninjaone.rmmplatform.exception;

public class ServiceCostAlreadyExistsException extends RuntimeException {

    public ServiceCostAlreadyExistsException(String message) {
        super(message);
    }

    public ServiceCostAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
