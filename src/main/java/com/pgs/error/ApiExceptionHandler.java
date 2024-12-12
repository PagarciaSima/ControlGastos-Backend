package com.pgs.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.pgs.constantes.ControlGastosConstants;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ApiErrorResponse> noHandlerFoundException (
			NoHandlerFoundException ex, HttpServletRequest request
	) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(404, ControlGastosConstants.NO_ENCONTRADO);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body(apiErrorResponse);
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ApiErrorResponse> httpMessageNotReadableException (
			HttpMessageNotReadableException ex, HttpServletRequest request
	) {
		ApiErrorResponse apiErrorResponse = new ApiErrorResponse(400, ControlGastosConstants.MAL_FORMADA);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
				.body(apiErrorResponse);
	}
	
}
