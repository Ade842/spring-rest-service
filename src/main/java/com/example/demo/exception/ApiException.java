package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ApiException {

  private final String message;

  private final String error;

  private final HttpStatus httpStatus;

  private final String code;

  public ApiException(final String message, final String error, final HttpStatus httpStatus,
      final String code) {
    this.message = message;
    this.error = error;
    this.httpStatus = httpStatus;
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getError() {
    return error;
  }

  public String getCode() {
    return code;
  }
}
