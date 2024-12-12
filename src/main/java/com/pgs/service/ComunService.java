package com.pgs.service;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ComunService {

	public ResponseEntity<?> getResponseEntity(String mensaje) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String> () {

			private static final long serialVersionUID = 1L;

			{
				put("mensaje", mensaje);
			}
		});
	}
}
