package com.pgs.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.service.EstadoService;
import com.pgs.service.GastoFijoService;

@RestController
@RequestMapping("/api/v1")
public class GastoFijoController {
	
	private GastoFijoService gastoFijoService;

	public GastoFijoController(GastoFijoService gastoFijoService) {
		this.gastoFijoService = gastoFijoService;
	}
	
	@GetMapping("/gastos-fijos")
	public ResponseEntity<?> getAllGastosFijos() {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoFijoService.listarPorMesYanio(fechaActual.getMonthValue(), fechaActual.getYear()));
	}

}
