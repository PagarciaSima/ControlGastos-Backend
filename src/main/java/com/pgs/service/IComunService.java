package com.pgs.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public interface IComunService {
	public ResponseEntity<?> getResponseEntity(HttpStatus httpStatus, String mensaje);
	public ResponseEntity<?> getBindingResultError(BindingResult bindingResult);

}