package com.pgs.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface IComunService {
	public ResponseEntity<?> getResponseEntity(HttpStatus httpStatus, String mensaje);
}