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
import com.pgs.service.IEstadoService;

import lombok.AllArgsConstructor;

/**
 * 
 * Controller for managing "Estado" resources.
 * Provides endpoints to retrieve all "Estado" and specific "Estado" used for expenses.
 * 
 * This controller handles requests mapped to "/api/v1".
 * It uses {@link IEstadoService} to perform the operations.
 * 
 * Cross-Origin Resource Sharing (CORS) is enabled for all origins with a maximum age of 3600 seconds.
 * 
 * Annotations:
 * <ul>
 *   <li>{@link RestController}: Marks this class as a Spring REST controller.</li>
 *   <li>{@link RequestMapping}: Maps requests to "/api/v1".</li>
 *   <li>{@link AllArgsConstructor}: Generates a constructor with all class fields as parameters.</li>
 *   <li>{@link CrossOrigin}: Enables CORS for all origins.</li>
 * </ul>
 * 
 * @author Pablo Garcia Simavilla
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EstadoController {
	
	private IEstadoService estadoService;
	
	 /**
     * Retrieves a list of all "Estado" objects.
     * 
     * Endpoint: GET /api/v1/estados
     * 
     * @return a {@link ResponseEntity} containing a list of all "Estado" objects with an HTTP status of 200 (OK).
     */
	@GetMapping("/estados")
	public ResponseEntity<?> getAllEstados() {
		return ResponseEntity.status(HttpStatus.OK).body(this.estadoService.listar());
	}
	
	/**
     * Retrieves a list of "Estado" objects used for expenses.
     * The list includes "Estado" with IDs matching {@link ControlGastosConstants#ESTADO_PAGADO}
     * and {@link ControlGastosConstants#ESTADO_NO_PAGADO}.
     * 
     * Endpoint: GET /api/v1/estados-gastos
     * 
     * @return a {@link ResponseEntity} containing a filtered list of "Estado" objects with an HTTP status of 200 (OK).
     */
	@GetMapping("/estados-gastos")
	public ResponseEntity<?> getAllEstadosGastos() {
		List<Long> ids = new ArrayList<>();
		ids.add(ControlGastosConstants.ESTADO_PAGADO);
		ids.add(ControlGastosConstants.ESTADO_NO_PAGADO);
		return ResponseEntity.status(HttpStatus.OK).body(this.estadoService.listarParaGastos(ids));
	}
}
