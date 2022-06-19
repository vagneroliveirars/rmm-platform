package com.ninjaone.rmmplatform.controller;

import com.ninjaone.rmmplatform.controller.dto.ErrorResponseDTO;
import com.ninjaone.rmmplatform.exception.DeviceAlreadyExistsException;
import com.ninjaone.rmmplatform.exception.NotFoundException;
import com.ninjaone.rmmplatform.exception.ServiceAlreadyExistsException;
import com.ninjaone.rmmplatform.exception.ServiceCostAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception,
                                                                                   HttpServletRequest request) {
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(getBody(BAD_REQUEST, request.getRequestURI(), exception.getMessage()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundExceptionHandler(NotFoundException exception,
                                                                     HttpServletRequest request) {
        return ResponseEntity
                .status(NOT_FOUND)
                .body(getBody(NOT_FOUND, request.getRequestURI(), exception.getMessage()));
    }

    @ExceptionHandler({ServiceAlreadyExistsException.class, ServiceCostAlreadyExistsException.class, DeviceAlreadyExistsException.class})
    public ResponseEntity<ErrorResponseDTO> resourceAlreadyExistsExceptionHandler(Exception exception,
                                                                                  HttpServletRequest request) {
        return ResponseEntity
                .status(CONFLICT)
                .body(getBody(CONFLICT, request.getRequestURI(), exception.getMessage()));
    }

    private ErrorResponseDTO getBody(HttpStatus status, String path, String message) {
        return new ErrorResponseDTO(ZonedDateTime.now(), path, status.value(), status.getReasonPhrase(), message);
    }

}
