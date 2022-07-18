package com.btorrelio.tenpoapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Error BAD_REQUEST",ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errorResponse.addValidationError(error.getField(), error.getDefaultMessage())
        );
        ex.getBindingResult().getGlobalErrors().forEach(error ->
            errorResponse.addValidationError(error.getObjectName(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Error BAD_REQUEST",ex);
        String errorMessage = "Parameter '" + ex.getParameterName() + "' must not be null";
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Error BAD_REQUEST",ex);
        String errorMessage = ex.getName() + " should be of type " + ex.getRequiredType() != null ? ex.getRequiredType().getName() : "";
        ErrorResponse errorResponse = new ErrorResponse(errorMessage);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("Error NOT_FOUND",ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException ex) {
        log.error("Error CONFLICT",ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);

    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        log.error("Error INTERNAL_SERVER_ERROR",ex);
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }




}
