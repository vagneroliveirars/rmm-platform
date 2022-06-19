package com.ninjaone.rmmplatform.exception;

public class DeviceAlreadyExistsException extends RuntimeException {

    public DeviceAlreadyExistsException() {
    }

    public DeviceAlreadyExistsException(String message) {
        super(message);
    }

    public DeviceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeviceAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public DeviceAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
