package com.example.ProductManagementRestAPI.Exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalTime;

@RestControllerAdvice
public class GlobalExceptionHandler {




    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFound(ResourceNotFound ex, HttpServletRequest request)
    {
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(ex.getMessage())
                .statusCode( HttpStatus.NOT_FOUND.value())
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .path(request.getRequestURI())
                .time(LocalTime.now())

                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFound(UserNotFound ex, HttpServletRequest request)
    {
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(ex.getMessage())
                .statusCode( HttpStatus.NOT_FOUND.value())
                .status(String.valueOf(HttpStatus.NOT_FOUND))
                .path(request.getRequestURI())
                .time(LocalTime.now())

                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }


    @ExceptionHandler(UserNameAndPasswordInvalid.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFound(UserNameAndPasswordInvalid ex, HttpServletRequest request)
    {
        ErrorMessage errorMessage=ErrorMessage.builder()
                .message(ex.getMessage())
                .statusCode( HttpStatus.UNAUTHORIZED.value())
                .status(String.valueOf(HttpStatus.UNAUTHORIZED))
                .path(request.getRequestURI())
                .time(LocalTime.now())

                .build();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }


}
