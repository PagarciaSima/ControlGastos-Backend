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
import com.pgs.dto.GastosFijosRequestDto;
import com.pgs.model.GastoFijoModel;
import com.pgs.model.ProveedorModel;
import com.pgs.service.IComunService;
import com.pgs.service.IEstadoService;
import com.pgs.service.IGastoFijoService;
import com.pgs.service.IProveedorService;

import lombok.AllArgsConstructor;

/**
 * 
 * Controller for managing fixed expenses (Gastos Fijos).
 * Provides endpoints to create, update, delete, and retrieve fixed expenses.
 * 
 * @author Pablo Garcia Simavilla
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class GastoFijoController {
	
	private IGastoFijoService gastoFijoService;
	private IEstadoService estadoService;
	private IProveedorService proveedorService;
	private IComunService comunService;

	/**
     * Retrieves all fixed expenses for the current month and year.
     *
     * @return a {@link ResponseEntity} containing the list of fixed expenses for the current month and year.
     */
	@GetMapping("/gastos-fijos")
	public ResponseEntity<?> getAllGastosFijosMesEnCurso() {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoFijoService.listarPorMesYanio(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
	
	/**
     * Retrieves all fixed expenses for the specified month in the current year.
     *
     * @param mes the month to filter fixed expenses.
     * @return a {@link ResponseEntity} containing the list of fixed expenses for the specified month.
     */
	@GetMapping("/gastos-fijos-por-mes/{mes}")
	public ResponseEntity<?> getAllGastosFijosPorMes(@PathVariable ("mes") Integer mes) {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoFijoService.listarPorMesYanio(mes, fechaActual.getYear()));
	}
	
	/**
     * Creates a new fixed expense.
     *
     * @param dto the {@link GastosFijosRequestDto} containing the details of the fixed expense to create.
     * @return a {@link ResponseEntity} indicating the success or failure of the operation.
     */
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
	
	/**
     * Updates an existing fixed expense by its ID.
     *
     * @param id  the ID of the fixed expense to update.
     * @param dto the {@link GastosFijosRequestDto} containing the updated details.
     * @return a {@link ResponseEntity} indicating the success or failure of the operation.
     */
	@PutMapping("/gastos-fijos/{id}")
	public ResponseEntity<?> putGastosFijos(@PathVariable ("id") Long id, @RequestBody GastosFijosRequestDto dto) {
		GastoFijoModel gastosFijos = gastoFijoService.buscarPorId(id);
		ProveedorModel proveedor = this.proveedorService.buscarPorId(dto.getProveedoresId());
		if (null == gastosFijos || null == proveedor)
			return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
		gastosFijos.setNombre(dto.getNombre());
		gastosFijos.setEstadosId(this.estadoService.buscarPorId(dto.getEstadosId()));
		gastosFijos.setMonto(dto.getMonto());
		gastosFijos.setProveedoresId(proveedor);
		this.gastoFijoService.guardar(gastosFijos);
		return comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_CREAR_REGISTRO);	
			
	}
	
	/**
     * Deletes an existing fixed expense by its ID.
     *
     * @param id the ID of the fixed expense to delete.
     * @return a {@link ResponseEntity} indicating the success or failure of the operation.
     */
	@DeleteMapping("/gastos-fijos/{id}")
	public ResponseEntity<?> deleteGastosFijos(@PathVariable ("id") Long id) {
		GastoFijoModel gastoFijo = gastoFijoService.buscarPorId(id);
		if ( null == gastoFijo)
			return comunService.getResponseEntity(HttpStatus.NOT_FOUND, ControlGastosConstants.NO_ENCONTRADO);
		gastoFijoService.eliminar(id);
		return comunService.getResponseEntity(HttpStatus.OK, ControlGastosConstants.EXITO_ELIMINAR);	
			
	}

}
