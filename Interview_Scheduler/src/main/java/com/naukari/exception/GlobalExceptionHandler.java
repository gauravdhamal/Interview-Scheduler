package com.naukari.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MyErrorDetails> myMethodArgumentNotValidExceptionHandler(
			MethodArgumentNotValidException manve) {

		System.out.println("MethodArgumentNotValidException handler.");

		MyErrorDetails error = new MyErrorDetails(LocalDateTime.now(),
				manve.getBindingResult().getFieldError().getDefaultMessage(), "Validation error");

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<MyErrorDetails> myIllegalArgumentExceptionHandler(IllegalArgumentException iae,
			WebRequest req) {

		System.out.println("IllegalArgumentException handler.");

		MyErrorDetails error = new MyErrorDetails(LocalDateTime.now(), iae.getMessage(), req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<MyErrorDetails> noHandlerFoundExceptionHandler(NoHandlerFoundException e, WebRequest req) {

		System.out.println("NoHandlerFoundException handler.");

		MyErrorDetails err = new MyErrorDetails(LocalDateTime.now(), e.getMessage(), req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(err, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> myExceptionHandler(Exception exp, WebRequest req) {

		System.out.println("Exception handler.");

		MyErrorDetails error = new MyErrorDetails(LocalDateTime.now(), exp.getMessage(), req.getDescription(false));

		return new ResponseEntity<MyErrorDetails>(error, HttpStatus.BAD_REQUEST);
	}

}
