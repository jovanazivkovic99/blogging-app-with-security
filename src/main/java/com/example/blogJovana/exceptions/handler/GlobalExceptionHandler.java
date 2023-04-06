package com.example.blogJovana.exceptions.handler;

import com.example.blogJovana.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException ex) {
        String errorMessage = String.format("User with email %s not found.", ex.getEmail());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        String errorMessage = String.format("Category with id %s not found", ex.getCategoryid());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleCategoryAlreadyExistsException(CategoryAlreadyExistsException ex) {
        String errorMessage = String.format("Category with title %s already exists", ex.getTitle());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        String errorMessage = String.format("User with email %s already registered.", ex.getEmail());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedActionException(UnauthorizedActionException ex) {
        String errorMessage = String.format("User %s not authorized to perform this action.", ex.getEmail());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException ex) {
        String errorMessage = String.format("Post with id %s not found.", ex.getPostId());
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
