package com.bank.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bank.dto.ErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorResponse> handleInsufficientBalance(InsufficientBalanceException
			ex,HttpServletRequest request)
	{
       
		logger.error("Insufficient Balance : {}",ex.getMessage(),ex);
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), 
				"Bad Request", ex.getMessage(),request.getRequestURI());

		return new ResponseEntity<>(errorResponse , HttpStatus.BAD_REQUEST);
	}



	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleAccountNotFound(
			AccountNotFoundException ex, HttpServletRequest request) {
		
		logger.error("Account Not Found : {}",ex.getMessage(),ex);
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage(),
				request.getRequestURI()
				);
		
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorResponse> handleAccessDenied(
			AccessDeniedException ex, HttpServletRequest request) {
		
		 logger.error("Access Denied : {}",ex.getMessage(),ex);
		 
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.FORBIDDEN.value(),
				"Forbidden",
				"You are not authorized to perform this action",
				request.getRequestURI()
				);
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneralException(
			Exception ex, HttpServletRequest request) {
		
		logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
		
		ErrorResponse error = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				"Internal Server Error",
				ex.getMessage(),
				request.getRequestURI()
				);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
