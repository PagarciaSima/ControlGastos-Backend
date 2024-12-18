package com.pgs.service;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * Implementation of the {@link IComunService} interface that provides common utility methods for handling HTTP responses.
 * This service is used to simplify the creation of standardized responses with a message.
 * @author Pablo Garcia Simavilla
 */
@Service
public class ComunServiceImpl implements IComunService {

	/**
	 * Creates a {@link ResponseEntity} with the specified HTTP status and message.
	 * This method is commonly used for returning consistent responses in the application.
	 * 
	 * @param httpStatus The HTTP status code to be returned in the response.
	 * @param mensaje The message to be included in the response body.
	 * @return A {@link ResponseEntity} containing a {@link HashMap} with the message.
	 */
	public ResponseEntity<?> getResponseEntity(HttpStatus httpStatus, String mensaje) {
		return ResponseEntity.status(httpStatus).body(new HashMap<String, String> () {

			private static final long serialVersionUID = 1L;

			{
				put("mensaje", mensaje);
			}
		});
	}
	
	/**
	 * Creates a {@link ResponseEntity} containing the validation error messages from the {@link BindingResult}.
	 * This method is used to return a consistent response with the validation errors when the binding result contains errors.
	 * 
	 * @param bindingResult The {@link BindingResult} that contains the errors from the validation process.
	 * @return A {@link ResponseEntity} with the validation error messages as a {@link String} in the response body 
	 *         and an HTTP status code of {@link HttpStatus#BAD_REQUEST}.
	 */
	public ResponseEntity<?> getBindingResultError(BindingResult bindingResult) {
		StringBuilder errorMessages = new StringBuilder();
		for (ObjectError error : bindingResult.getAllErrors()) {
		    errorMessages.append(error.getDefaultMessage()).append("\n");
		}
		return new ResponseEntity<>(errorMessages.toString(), HttpStatus.BAD_REQUEST);
	}
}
