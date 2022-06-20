package com.ninjaone.rmmplatform.exception;

public class DeviceAlreadyExistsException extends RuntimeException {

    public DeviceAlreadyExistsException(String message) {
        super(message);
    }

    public DeviceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
