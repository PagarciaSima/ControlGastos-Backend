package com.pgs.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.dto.GastosFijosRequestDto;
import com.pgs.model.GastoFijoModel;
import com.pgs.model.ProveedorModel;
import com.pgs.service.ComunService;
import com.pgs.service.EstadoService;
import com.pgs.service.GastoFijoService;
import com.pgs.service.ProveedorService;

@RestController
@RequestMapping("/api/v1")
public class GastoFijoController {
	
	private GastoFijoService gastoFijoService;
	private EstadoService estadoService;
	private ProveedorService proveedorService;
	private ComunService comunService;

	public GastoFijoController(GastoFijoService gastoFijoService, EstadoService estadoService,
			ProveedorService proveedorService, ComunService comunService) {
		this.gastoFijoService = gastoFijoService;
		this.estadoService = estadoService;
		this.proveedorService = proveedorService;
		this.comunService = comunService;
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
	
	@PostMapping("/gastos-fijos")
	public ResponseEntity<?> postGastosFijos(@RequestBody GastosFijosRequestDto dto) {
		ProveedorModel proveedor = this.proveedorService.buscarPorId(dto.getProveedoresId());
		if(null == proveedor) {
			return comunService.getResponseEntity(
					HttpStatus.NOT_FOUND, 
					ControlGastosConstants.NO_ENCONTRADO + " (Proveedor ID = " + dto.getProveedoresId() + ")"
		);
		} else {
			try {
				this.gastoFijoService.guardar(
					new GastoFijoModel(
						dto.getNombre(),
						dto.getMonto(),
						new Date (),
						this.estadoService.buscarPorId(ControlGastosConstants.ESTADO_NO_PAGADO),
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
