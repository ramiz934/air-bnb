package com.airbnb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(PropertyNotFoundException prop){
        return new  ResponseEntity<>(prop.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }
      @ExceptionHandler(ReviewAlreadyExistsException.class)
    public ResponseEntity<String> handleReviewAlreadyExists(ReviewAlreadyExistsException rev){
        return new  ResponseEntity<>(rev.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
