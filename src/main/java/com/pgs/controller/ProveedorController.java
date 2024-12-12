package com.pgs.controller;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.ProveedorModel;
import com.pgs.service.ProveedorService;

@RestController
@RequestMapping("/api/v1")
public class ProveedorController {
	
	private ProveedorService proveedorService;

	public ProveedorController(ProveedorService proveedorService) {
		this.proveedorService = proveedorService;
	}
	
	@GetMapping("/proveedores")
	public ResponseEntity<?> getAllProveedores() {
		return ResponseEntity.status(HttpStatus.OK).body(this.proveedorService.listar());
	}
	
	@GetMapping("/proveedores/{id}")
	public ResponseEntity<?> getProveedorById(@PathVariable (name = "id") Long id) {
		ProveedorModel proveedor = this.proveedorService.buscarPorId(id);
		if(null == proveedor) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String> () {

				private static final long serialVersionUID = 1L;

				{
					put("estado", HttpStatus.BAD_REQUEST.toString());
					put("mensaje", ControlGastosConstants.NO_ENCONTRADO + " (ID = " + id + ")");
				}
			});
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(proveedor);
		}
	}
	
}
