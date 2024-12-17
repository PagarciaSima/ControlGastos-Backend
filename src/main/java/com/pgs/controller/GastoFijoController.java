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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Fixed expenses", description = "API for managing 'Fixed expenses' resources")
@SecurityRequirement(name = "bearerAuth")
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
	@Operation(
	    summary = "Retrieve all fixed expenses for the current month and year",
	    description = "Returns a list of fixed expenses for the current month and year.",
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "List of fixed expenses for the current month and year",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Success Example",
	                    value = "[{ \"id\": 1, \"descripcion\": \"Alquiler\", \"monto\": 500.0, \"mes\": 12, \"anio\": 2024 }]"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"An unexpected error occurred\" }"
	                )
	            )
	        )
	    }
	)
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
	@Operation(
	    summary = "Retrieve all fixed expenses for a specific month in the current year",
	    description = "Returns a list of fixed expenses for the specified month in the current year.",
	    parameters = {
	        @Parameter(
	            name = "mes",
	            description = "The month to filter fixed expenses (1-12)",
	            required = true,
	            example = "6"
	        )
	    },
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "List of fixed expenses for the specified month",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Success Example",
	                    value = "[{ \"id\": 2, \"descripcion\": \"Luz\", \"monto\": 100.0, \"mes\": 6, \"anio\": 2024 }]"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Invalid month provided",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"Invalid month value. Please provide a number between 1 and 12.\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"An unexpected error occurred\" }"
	                )
	            )
	        )
	    }
	)
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
	@Operation(
	    summary = "Create a new fixed expense",
	    description = "Creates a new fixed expense using the details provided in the request body.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Details of the fixed expense to be created",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = GastosFijosRequestDto.class),
	            examples = @ExampleObject(
	                name = "Request Example",
	                value = "{ \"nombre\": \"Internet\", \"monto\": 50.0, \"proveedoresId\": 1 }"
	            )
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "Fixed expense created successfully",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Success Example",
	                    value = "{ \"message\": \"Registro creado con éxito.\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "404",
	            description = "Provider not found",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"No encontrado (Proveedor ID = 1).\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Unexpected error",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"Ocurrió un error inesperado.\" }"
	                )
	            )
	        )
	    }
	)
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
	@Operation(
	    summary = "Update an existing fixed expense by ID",
	    description = "Updates the details of an existing fixed expense identified by its ID.",
	    parameters = {
	        @Parameter(
	            name = "id",
	            description = "The ID of the fixed expense to update",
	            required = true,
	            example = "1"
	        )
	    },
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Updated details of the fixed expense",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = GastosFijosRequestDto.class),
	            examples = @ExampleObject(
	                name = "Request Example",
	                value = "{ \"nombre\": \"Electricidad\", \"monto\": 100.0, \"proveedoresId\": 2, \"estadosId\": 1 }"
	            )
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "Fixed expense updated successfully",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Success Example",
	                    value = "{ \"message\": \"Registro creado con éxito.\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Invalid request or unexpected error",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"Ocurrió un error inesperado.\" }"
	                )
	            )
	        )
	    }
	)
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
	@Operation(
	    summary = "Delete an existing fixed expense by ID",
	    description = "Deletes an existing fixed expense identified by its ID.",
	    parameters = {
	        @Parameter(
	            name = "id",
	            description = "The ID of the fixed expense to delete",
	            required = true,
	            example = "1"
	        )
	    },
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Fixed expense deleted successfully",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Success Example",
	                    value = "{ \"message\": \"Registro eliminado con éxito.\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "404",
	            description = "Fixed expense not found",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"No encontrado.\" }"
	                )
	            )
	        )
	    }
	)
	@DeleteMapping("/gastos-fijos/{id}")
	public ResponseEntity<?> deleteGastosFijos(@PathVariable ("id") Long id) {
		GastoFijoModel gastoFijo = gastoFijoService.buscarPorId(id);
		if ( null == gastoFijo)
			return comunService.getResponseEntity(HttpStatus.NOT_FOUND, ControlGastosConstants.NO_ENCONTRADO);
		gastoFijoService.eliminar(id);
		return comunService.getResponseEntity(HttpStatus.OK, ControlGastosConstants.EXITO_ELIMINAR);	
			
	}


}
