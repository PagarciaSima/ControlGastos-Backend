package com.pgs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.dto.ProveedorRequestDto;
import com.pgs.model.ProveedorModel;
import com.pgs.service.ComunService;
import com.pgs.service.ProveedorService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class ProveedorController {
	
	private ProveedorService proveedorService;
	private ComunService comunService;

	public ProveedorController(ProveedorService proveedorService, ComunService comunService) {
		this.proveedorService = proveedorService;
		this.comunService = comunService;
	}
	
	@GetMapping("/proveedores")
	public ResponseEntity<?> getAllProveedores() {
		return ResponseEntity.status(HttpStatus.OK).body(this.proveedorService.listar());
	}
	
	@GetMapping("/proveedores/{id}")
	public ResponseEntity<?> getProveedorById(@PathVariable (name = "id") Long id) {
		ProveedorModel proveedor = this.proveedorService.buscarPorId(id);
		if(null == proveedor) {
			return comunService.getResponseEntity(
					HttpStatus.NOT_FOUND, 
					ControlGastosConstants.NO_ENCONTRADO + " (Proveedor ID = " + id + ")"
			);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(proveedor);
		}
	}
	
	@PostMapping("/proveedores")
	public ResponseEntity<?> postProveedor(@RequestBody ProveedorRequestDto dto) {
		try {
			this.proveedorService.guardar(new ProveedorModel(dto.getNombre()));
			return comunService.getResponseEntity(
					HttpStatus.CREATED,
					ControlGastosConstants.EXITO_CREAR_REGISTRO			
			);
		} catch (Exception e) {
			return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
		}
	}

	@PutMapping("/proveedores/{id}")
	public ResponseEntity<?> putProveedor(@PathVariable (name = "id") Long id, @RequestBody ProveedorRequestDto dto) {
		ProveedorModel proveedor = this.proveedorService.buscarPorId(id);
		if (proveedor != null) {
			proveedor.setNombre(dto.getNombre());
			proveedorService.guardar(proveedor);
			return comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_ACTUALIZAR_REGISTRO);

		} else {
			return comunService.getResponseEntity(
					HttpStatus.NOT_FOUND, 
					ControlGastosConstants.NO_ENCONTRADO + " (ID = " + id + ")"
			);
		}
	}
	
}
