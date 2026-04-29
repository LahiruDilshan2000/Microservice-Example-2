package com.example.microservice.customer.handler;

import com.example.microservice.customer.exception.CustomException;
import com.example.microservice.customer.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CustomException.class})
    public ResponseEntity<String> handle(CustomException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException ex){
        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors()
                .forEach(error ->  {
                    var fieldName = ((FieldError)error).getField();
                    var errorMsg = error.getDefaultMessage();
                    errors.put(fieldName, errorMsg);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
