package com.pgs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.service.EstadoService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class EstadoController {
	
	private EstadoService estadoService;

	public EstadoController(EstadoService estadoService) {
		this.estadoService = estadoService;
	}
	
	@GetMapping("/estados")
	public ResponseEntity<?> getAllEstados() {
		return ResponseEntity.status(HttpStatus.OK).body(this.estadoService.listar());
	}
	
	@GetMapping("/estados-gastos")
	public ResponseEntity<?> getAllEstadosGastos() {
		List<Long> ids = new ArrayList<>();
		ids.add(ControlGastosConstants.ESTADO_PAGADO);
		ids.add(ControlGastosConstants.ESTADO_NO_PAGADO);
		return ResponseEntity.status(HttpStatus.OK).body(this.estadoService.listarParaGastos(ids));
	}
}
