package com.example.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundHandling(final ResourceNotFoundException exception, final WebRequest request) {
    ApiException errorDetails =
        new ApiException("Resource could not be found", exception.getMessage(), HttpStatus.NOT_FOUND, "404");
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globalExceptionHandling(final Exception exception, final WebRequest request) {
    ApiException errorDetails =
        new ApiException("Internal server error", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "500");
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ApiRequestException.class)
  public ResponseEntity<?> methodArgumentNotValidException(final ApiRequestException exception, final WebRequest request) {
    ApiException errorDetails =
        new ApiException("Argument is not the expected type or resource already exists", exception.getMessage(), HttpStatus.BAD_REQUEST, "400");
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }

  @Override
  @Nonnull
  protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers,
        final HttpStatus status, final WebRequest request) {

    List<String> errors = new ArrayList<String>();

    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + " -> " + error.getDefaultMessage());
    }

    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + " -> " + error.getDefaultMessage());
    }

    ApiException apiError = new ApiException("Argument is not the expected type", ex.getMessage(), HttpStatus.BAD_REQUEST, "400");
    return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);

  }

  @Override
  @Nonnull
  protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex,
      final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
    String error = ex.getParameterName() + " -> parameter is missing in request";

    ApiException apiError = new ApiException("Parameter is missing in request", ex.getMessage(), HttpStatus.BAD_REQUEST, "400");
    return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
  }

  @Override
  @Nonnull
  protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex, final HttpHeaders headers,
      final HttpStatus status, final WebRequest request) {
    String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

    ApiException apiError = new ApiException("No handler found", ex.getMessage(), HttpStatus.NOT_FOUND, "404");
    return new ResponseEntity<Object>(apiError, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({ ConstraintViolationException.class })
  public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex, final WebRequest request) {
    List<String> errors = new ArrayList<String>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
      errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
          + violation.getMessage());
    }

    ApiException apiError = new ApiException("Constraint violation", ex.getMessage(), HttpStatus.BAD_REQUEST, "400");
    return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);

  }


  @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
  public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex, final WebRequest request) {
    String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

    ApiException apiError = new ApiException("Method argument type mismatch", ex.getMessage(), HttpStatus.BAD_REQUEST, "400");
    return new ResponseEntity<Object>(apiError, HttpStatus.BAD_REQUEST);
  }
}
