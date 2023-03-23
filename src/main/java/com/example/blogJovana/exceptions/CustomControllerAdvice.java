package com.example.blogJovana.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(NullPointerException.class) // exception handled
    public ResponseEntity<ErrorResponse> handleNullPointerExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                status
        );
    }

    // fallback method
    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            Exception e
    ) {
        // ... potential custom logic

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage(),
                        stackTrace // specifying the stack trace in case of 500s
                ),
                status
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundExceptions(
            Exception e
    ) {
        // casting the generic Exception e to CustomErrorException
        UserNotFoundException userNotFoundException = (UserNotFoundException) e;

        HttpStatus status = userNotFoundException.getStatus();

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        userNotFoundException.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        userNotFoundException.getMessage(),
                        stackTrace,
                        userNotFoundException.getData()
                ),
                status
        );
    }

    @ExceptionHandler(CategoryNotFound.class)
    public ResponseEntity<ErrorResponse> handleCategoryNotFoundExceptions(
            Exception e
    ) {
        // casting the generic Exception e to CustomErrorException
        CategoryNotFound categoryNotFound = (CategoryNotFound) e;

        HttpStatus status = categoryNotFound.getStatus();

        // converting the stack trace to String
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        categoryNotFound.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        categoryNotFound.getMessage(),
                        stackTrace,
                        categoryNotFound.getData()
                ),
                status
        );
    }
}
