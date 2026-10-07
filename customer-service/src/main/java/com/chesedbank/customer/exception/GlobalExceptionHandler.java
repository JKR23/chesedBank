package com.chesedbank.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

/**RestControllerAdvice: catch every exception throw by RestController
 * and return appropriate message to the front end*/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerCustomerNotFoundException(CustomerNotFoundException e){

        ErrorResponse er = new ErrorResponse(
                LocalDateTime.now(),
                404,
                "Not found",
                e.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){
        Map<String, String> fieldErrors = e.getBindingResult() //get the validation errors
                .getFieldErrors() //gets the validation errors related to individual fields.
                .stream()
                .collect(Collectors.toMap( //Take every FieldError and create a Map<String, String> from it
                        FieldError::getField, //This determines the key.
                        error -> error.getDefaultMessage() != null //This is a ternary operator. and is defining the value
                                ? error.getDefaultMessage()
                                : "Invalid value",
                        (existing, replacement) -> existing //If the key already exists, keep the existing message.
                ));

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                "Request validation failed",
                fieldErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

}
