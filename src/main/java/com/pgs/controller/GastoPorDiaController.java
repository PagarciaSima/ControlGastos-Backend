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
import com.pgs.service.IComunService;
import com.pgs.service.IGastoPorDiaService;
import com.pgs.service.IProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * Controller for managing daily expenses (GastosPorDia).
 * Provides endpoints for CRUD operations on daily expenses.
 * 
 * @author Pablo Garcia Simavilla
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Daily expenses", description = "API for managing 'Daily Expenses' resources")
@SecurityRequirement(name = "bearerAuth")
public class GastoPorDiaController {
	
	private IGastoPorDiaService gastoPorDiaService;
	private IProveedorService proveedorService;
	private IComunService comunService;
	
	/**
     * Retrieves all daily expenses for the current month and year.
     *
     * @return ResponseEntity containing a list of daily expenses and HTTP status.
     */
	@Operation(
		    summary = "Retrieve all daily expenses for the current month and year",
		    description = "Fetches all the daily expenses for the current month and year.",
		    responses = {
		        @ApiResponse(
		            responseCode = "200",
		            description = "Successfully retrieved the list of daily expenses",
		            content = @Content(
		                mediaType = "application/json",
		                schema = @Schema(implementation = GastoPorDiaModel.class)
		            )
		        ),
		        @ApiResponse(responseCode = "500", description = "Internal Server Error")
		    }
	)
	@GetMapping("/gastos-por-dia")
	public ResponseEntity<?> getAllGastosPorDiaMesEnCurso() {
		LocalDate fechaActual = LocalDate.now();
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(this.gastoPorDiaService.listarPorAnioMes(fechaActual.getMonthValue(), fechaActual.getYear()));
	}
	
	/**
     * Creates a new daily expense.
     *
     * @param dto Data transfer object containing the details of the daily expense to create.
     * @return ResponseEntity with a success or error message and HTTP status.
     */
	@Operation(
	    summary = "Create a new daily expense",
	    description = "Creates a new daily expense entry with the provided details.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "DTO containing the details of the daily expense to create",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = GastoPorDiaDto.class)
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "Successfully created the daily expense",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(responseCode = "400", description = "Bad request due to invalid data"),
	        @ApiResponse(responseCode = "404", description = "Provider not found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    }
	)
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
	
	 /**
     * Updates an existing daily expense by its ID.
     *
     * @param id The ID of the daily expense to update.
     * @param dto Data transfer object containing the updated details of the daily expense.
     * @return ResponseEntity with a success or error message and HTTP status.
     */
	@Operation(
	    summary = "Update an existing daily expense by its ID",
	    description = "Updates the details of an existing daily expense identified by the provided ID.",
	    parameters = {
	        @Parameter(name = "id", description = "The ID of the daily expense to update", required = true, example = "123")
	    },
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "DTO containing the updated details of the daily expense",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = GastoPorDiaDto.class)
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "Successfully updated the daily expense",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(responseCode = "400", description = "Bad request due to invalid data or missing expense/provider"),
	        @ApiResponse(responseCode = "404", description = "Daily expense not found or provider not found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    }
	)
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
	
	/**
     * Deletes a daily expense by its ID.
     *
     * @param id The ID of the daily expense to delete.
     * @return ResponseEntity with a success or error message and HTTP status.
     */
	@Operation(
	    summary = "Delete a daily expense by its ID",
	    description = "Deletes the daily expense identified by the provided ID.",
	    parameters = {
	        @Parameter(name = "id", description = "The ID of the daily expense to delete", required = true, example = "123")
	    },
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Successfully deleted the daily expense",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(responseCode = "404", description = "Daily expense not found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    }
	)
	@DeleteMapping("/gastos-por-dia/{id}")
	public ResponseEntity<?> deleteGastosPorDia(@PathVariable ("id") Long id) {
		GastoPorDiaModel gastoPorDiaModel = gastoPorDiaService.buscarPorId(id);
		if ( null == gastoPorDiaModel)
			return comunService.getResponseEntity(HttpStatus.NOT_FOUND, ControlGastosConstants.NO_ENCONTRADO);
		gastoPorDiaService.eliminar(id);
		return comunService.getResponseEntity(HttpStatus.OK, ControlGastosConstants.EXITO_ELIMINAR);	
			
	}
}
