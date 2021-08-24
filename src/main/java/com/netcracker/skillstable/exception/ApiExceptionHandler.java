package com.netcracker.skillstable.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import java.io.IOException;
import java.time.ZonedDateTime;


@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), status, ZonedDateTime.now());
        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ApiResponse> handleResourceAlreadyExistsException(ResourceAlreadyExistsException exception) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), status, ZonedDateTime.now());
        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(UsernameNotFoundException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), status, ZonedDateTime.now());
        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<ApiResponse> handlePasswordException(PasswordException exception) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), status, ZonedDateTime.now());
        return new ResponseEntity<>(apiResponse, status);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiResponse> handleValidationException(ValidationException exception) {
        HttpStatus status = HttpStatus.CONFLICT;
        ApiResponse apiResponse = new ApiResponse(exception.getMessage(), status, ZonedDateTime.now());
        return new ResponseEntity<>(apiResponse, status);
    }
}

@Data
@AllArgsConstructor
class ApiResponse {
    private final String message;
    private final HttpStatus status;
    private final ZonedDateTime timestamp;
}
