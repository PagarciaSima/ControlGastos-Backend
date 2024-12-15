package com.pgs.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.service.GastoPorDiaService;

@RestController
@RequestMapping("/api/v1")
public class GastoPorDiaController {
	
	private GastoPorDiaService gastoPorDiaService;
	
	public GastoPorDiaController (GastoPorDiaService gastoPorDiaService) {
		this.gastoPorDiaService = gastoPorDiaService;
	}
	
	@GetMapping("/gastos-por-dia")
	public ResponseEntity<?> getAllGastosFijosMesEnCurso() {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoPorDiaService.listarPorAnioMes(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
}
