package com.dominic.shop.exception;

import com.dominic.shop.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(IllegalArgumentException ex) {
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();

        if (cause instanceof UnrecognizedPropertyException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Unknown field: '" + e.getPropertyName() + "'");
        }

        if (cause instanceof InvalidFormatException e) {
            String field = e.getPath().isEmpty() ? "unknown" : e.getPath().getFirst().getFieldName();
            String expectedType = e.getTargetType() != null ? e.getTargetType().getSimpleName() : "unknown";
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Invalid value for field '" + field + "': '" + e.getValue() + "' is not a valid " + expectedType);
        }

        if (cause instanceof MismatchedInputException e) {
            String field = e.getPath().isEmpty() ? "unknown" : e.getPath().getFirst().getFieldName();
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Missing or invalid value for required field '" + field + "'");
        }

        if (cause instanceof JsonParseException e) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(),
                    "Malformed JSON: " + e.getOriginalMessage());
        }

        if (ex.getMessage() != null && ex.getMessage().contains("Required request body is missing")) {
            return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Request body is missing");
        }

        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Malformed JSON request");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneric(Exception ignored) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred");
    }

}
