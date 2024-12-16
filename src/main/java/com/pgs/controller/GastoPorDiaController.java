package com.pgs.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.dto.GastoPorDiaDto;
import com.pgs.model.GastoPorDiaModel;
import com.pgs.model.ProveedorModel;
import com.pgs.service.ComunService;
import com.pgs.service.GastoPorDiaService;
import com.pgs.service.ProveedorService;

@RestController
@RequestMapping("/api/v1")
public class GastoPorDiaController {
	
	private GastoPorDiaService gastoPorDiaService;
	private ProveedorService proveedorService;
	private ComunService comunService;

	public GastoPorDiaController (GastoPorDiaService gastoPorDiaService,
			ProveedorService proveedorService,
			ComunService comunService
	) {
		this.gastoPorDiaService = gastoPorDiaService;
		this.proveedorService = proveedorService;
		this.comunService = comunService;
	}
	
	@GetMapping("/gastos-por-dia")
	public ResponseEntity<?> getAllGastosPorDiaMesEnCurso() {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoPorDiaService.listarPorAnioMes(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
	
	@PostMapping("/gastos-por-dia")
	public ResponseEntity<?> postGastosPorDiaMesEnCurso(@RequestBody GastoPorDiaDto dto) {
		ProveedorModel proveedor = this.proveedorService.buscarPorId(dto.getProveedoresId());
		if(null == proveedor) {
			return comunService.getResponseEntity(
					HttpStatus.NOT_FOUND, 
					ControlGastosConstants.NO_ENCONTRADO + " (Proveedor ID = " + dto.getProveedoresId() + ")"
		);
		} else {
			try {
				this.gastoPorDiaService.guardar(
					new GastoPorDiaModel(
						dto.getNeto(),
						dto.getIva(),
						dto.getTotal(),
						new Date (),
						dto.getDescripcion(),
						proveedor						
					)
				);
				
				return comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_CREAR_REGISTRO);
			} catch (Exception e) {
				return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
			}
		}
	}
}
