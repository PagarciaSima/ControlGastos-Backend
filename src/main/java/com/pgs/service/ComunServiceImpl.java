package com.pgs.service;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
