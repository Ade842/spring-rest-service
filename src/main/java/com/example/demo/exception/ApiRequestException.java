package com.example.demo.exception;

public class ApiRequestException extends  RuntimeException {
  public ApiRequestException(final String message) {
    super(message);
  }

  public ApiRequestException(final String message, final Throwable cause) {
    super(message, cause);
  }


}
