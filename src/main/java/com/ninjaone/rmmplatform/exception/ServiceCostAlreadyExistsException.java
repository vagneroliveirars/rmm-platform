package com.ninjaone.rmmplatform.exception;

public class ServiceCostAlreadyExistsException extends RuntimeException {

    public ServiceCostAlreadyExistsException() {
    }

    public ServiceCostAlreadyExistsException(String message) {
        super(message);
    }

    public ServiceCostAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceCostAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public ServiceCostAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
