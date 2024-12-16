package com.pgs.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.dto.GastoPorDiaDto;
import com.pgs.model.GastoPorDiaModel;
import com.pgs.model.ProveedorModel;
import com.pgs.service.ComunServiceImpl;
import com.pgs.service.GastoPorDiaServiceImpl;
import com.pgs.service.IGastoPorDiaService;
import com.pgs.service.IProveedorService;
import com.pgs.service.ProveedorServiceImpl;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class GastoPorDiaController {
	
	private IGastoPorDiaService gastoPorDiaService;
	private IProveedorService proveedorService;
	private ComunServiceImpl comunService;
	
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
	
	@PutMapping("/gastos-por-dia/{id}")
	public ResponseEntity<?> putGastosPorDia(@PathVariable ("id") Long id, @RequestBody GastoPorDiaDto dto) {
		GastoPorDiaModel gastoPorDiaModel = gastoPorDiaService.buscarPorId(id);
		ProveedorModel proveedor = this.proveedorService.buscarPorId(dto.getProveedoresId());
		if (null == gastoPorDiaModel || null == proveedor)
			return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
		gastoPorDiaModel.setIva(dto.getIva());
		gastoPorDiaModel.setDescripcion(dto.getDescripcion());
		gastoPorDiaModel.setNeto(dto.getNeto());
		gastoPorDiaModel.setProveedoresId(proveedor);
		this.gastoPorDiaService.guardar(gastoPorDiaModel);
		return comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_CREAR_REGISTRO);	
			
	}
	
	@DeleteMapping("/gastos-por-dia/{id}")
	public ResponseEntity<?> deleteGastosPorDia(@PathVariable ("id") Long id) {
		GastoPorDiaModel gastoPorDiaModel = gastoPorDiaService.buscarPorId(id);
		if ( null == gastoPorDiaModel)
			return comunService.getResponseEntity(HttpStatus.NOT_FOUND, ControlGastosConstants.NO_ENCONTRADO);
		gastoPorDiaService.eliminar(id);
		return comunService.getResponseEntity(HttpStatus.OK, ControlGastosConstants.EXITO_ELIMINAR);	
			
	}
}
