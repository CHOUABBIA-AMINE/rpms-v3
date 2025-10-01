package dz.mdn.rpms.exception.controller;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.ObjectMapper;

import dz.mdn.rpms.exception.domain.dto.ErrorResponse;
import dz.mdn.rpms.exception.domain.dto.ValidationError;
import dz.mdn.rpms.exception.domain.model.AuthenticationException;
import dz.mdn.rpms.exception.domain.model.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@Autowired
	private ObjectMapper objectMapper;
	
    //400 : Bad Request
    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class, MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                    ValidationError validationError = new ValidationError();
                    validationError.setField(error.getField());
                    validationError.setMessage(error.getDefaultMessage());
                    validationError.setRejectedValue(error.getRejectedValue());
                    return validationError;
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                "Invalid request content",
                request.getRequestURI()
        );
        errorResponse.setValidationErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //401 : Unauthorized
    @ExceptionHandler({AuthenticationException.class, BadCredentialsException.class})
    public void handleAuthentication(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());       
        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.UNAUTHORIZED,
                "Authentication Failed",
                ex.getMessage(),
                request.getRequestURI()
        );
        System.out.println(ex.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }
    
    //403 : Forbidden
    @ExceptionHandler({AccessDeniedException.class, AuthorizationDeniedException.class})
    public void handleAccessDenied(Exception ex, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.FORBIDDEN,
                "Access Denied",
                ex.getMessage(),
                request.getRequestURI()
        );
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

    //404 : Not Found
    @ExceptionHandler({ResourceNotFoundException.class, EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<ValidationError> errors = ex.getConstraintViolations().stream()
                .map(violation -> {
                    ValidationError validationError = new ValidationError();
                    String field = violation.getPropertyPath().toString();
                    validationError.setField(field.substring(field.lastIndexOf('.') + 1));
                    validationError.setMessage(violation.getMessage());
                    validationError.setRejectedValue(violation.getInvalidValue());
                    return validationError;
                })
                .collect(Collectors.toList());

        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Constraint Violation",
                "Invalid request parameters",
                request.getRequestURI()
        );
        errorResponse.setValidationErrors(errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //500 : Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
    	//System.out.println(ex.getClass());
        ErrorResponse errorResponse = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred : " + ex.getMessage(),
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse buildErrorResponse(HttpStatus status, String error, String message, String path) {
        ErrorResponse response = new ErrorResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status.value());
        response.setError(error);
        response.setMessage(message);
        response.setPath(path);
        return response;
    }
}