package com.org.OhIForgotProvider.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j(topic = "Global Exception Handler")
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
 
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiException> handleNotFoundException(ResponseEntityException ex){
		log.error("404 Not Found: {}", ex.toString());
		ApiException ae = new ApiException(ex.getMessage(), HttpStatus.NOT_FOUND, ZonedDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ae);
	}
}
