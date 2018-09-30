package com.wjaronski.debter.server.controller;

import com.wjaronski.debter.server.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Wojciech Jaronski
 */
@Slf4j
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler({UserAlreadyExistsException.class})
  protected ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
    String message = ex.getMessage();
    log.warn(message, ex);
    return handleExceptionInternal(ex, message, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
}
