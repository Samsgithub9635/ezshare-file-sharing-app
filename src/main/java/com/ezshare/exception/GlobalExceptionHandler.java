package com.ezshare.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(FileNotFoundException.class) // which class of exception we are handling is mentioned here that is of FileNotFoundException.class
    public ResponseEntity<?> handleFilenotFoundException(FileNotFoundException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
