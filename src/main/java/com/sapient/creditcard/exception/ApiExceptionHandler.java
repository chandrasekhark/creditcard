package com.sapient.creditcard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sapient.creditcard.dto.ApiErrorResponse;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(IncorrectCardNumber.class)
	public ResponseEntity<ApiErrorResponse> handleApiException(IncorrectCardNumber ex) {
		return new ResponseEntity<>(new ApiErrorResponse("error-0001", ex.getMessage()), HttpStatus.BAD_REQUEST);
	}
}
