package com.pgs.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.service.GastoFijoService;

@RestController
@RequestMapping("/api/v1")
public class GastoFijoController {
	
	private GastoFijoService gastoFijoService;

	public GastoFijoController(GastoFijoService gastoFijoService) {
		this.gastoFijoService = gastoFijoService;
	}
	
	@GetMapping("/gastos-fijos")
	public ResponseEntity<?> getAllGastosFijosMesEnCurso() {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoFijoService.listarPorMesYanio(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
	
	@GetMapping("/gastos-fijos-por-mes/{mes}")
	public ResponseEntity<?> getAllGastosFijosPorMes(@PathVariable ("mes") Integer mes) {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoFijoService.listarPorMesYanio(mes, fechaActual.getYear()));
	}

}
