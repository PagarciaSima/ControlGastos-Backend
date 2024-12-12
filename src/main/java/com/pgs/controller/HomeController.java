package com.pgs.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping("/")
	public ResponseEntity<?> getHome() {
		return ResponseEntity.status(HttpStatus.OK).body(new HashMap<String, String>() {
			private static final long serialVersionUID = 1L;

			{
				put("estado", HttpStatus.OK.toString());
				put("mensaje", "Saludos desde método get getHome");
			}
		});
		
	}
	
}
