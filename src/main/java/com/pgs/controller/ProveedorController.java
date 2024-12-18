package com.pgs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.pgs.service.IComunService;
import com.pgs.service.IProveedorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

/**
 * 
 * Controller for managing providers (suppliers).
 * Provides endpoints to get, create, and update provider data.
 * 
 * @author Pablo Garcia Simavilla
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Supplier", description = "API for managing 'Supplier' resources")
@SecurityRequirement(name = "bearerAuth")
public class ProveedorController {
	
	private IProveedorService proveedorService;
	private IComunService comunService;
	
	/**
     * Retrieves all providers.
     *
     * @return ResponseEntity containing the list of all providers.
     */
	@GetMapping("/proveedores")
	@Operation(
	    summary = "Retrieve all providers",
	    description = "Fetches a list of all providers from the system.",
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Successfully retrieved the list of providers",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = ProveedorModel.class, type = "array")
	            )
	        ),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    }
	)
	public ResponseEntity<?> getAllProveedores() {
		return ResponseEntity.status(HttpStatus.OK).body(this.proveedorService.listar());
	}
	
	/**
     * Retrieves a provider by its ID.
     *
     * @param id The ID of the provider to retrieve.
     * @return ResponseEntity containing the provider data if found, or an error message if not found.
     */
	@Operation(
	    summary = "Retrieve a provider by its ID",
	    description = "Fetches a provider from the system by the provided ID.",
	    parameters = {
	        @Parameter(name = "id", description = "The ID of the provider to retrieve", required = true, example = "1")
	    },
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Successfully retrieved the provider data",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = ProveedorModel.class)
	            )
	        ),
	        @ApiResponse(responseCode = "404", description = "Provider not found"),
	        @ApiResponse(responseCode = "500", description = "Internal Server Error")
	    }
	)
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
	
	 /**
     * Creates a new provider.
     *
     * @param dto The provider data transfer object containing the provider's information.
     * @return ResponseEntity indicating the result of the creation process (success or failure).
     */
	@Operation(
	    summary = "Create a new provider",
	    description = "Creates a new provider in the system using the provided data.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Provider data to be created",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ProveedorRequestDto.class)
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "Successfully created the provider",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Bad request due to an unexpected error"
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error"
	        )
	    }
	)
	@PostMapping("/proveedores")
	public ResponseEntity<?> postProveedor(@Valid @RequestBody ProveedorRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	        return comunService.getBindingResultError(bindingResult);
	    }
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

	/**
     * Updates an existing provider by its ID.
     *
     * @param id  The ID of the provider to update.
     * @param dto The provider data transfer object containing the updated provider's information.
     * @return ResponseEntity indicating the result of the update process (success or failure).
     */
	@Operation(
	    summary = "Update an existing provider",
	    description = "Updates the provider details by its ID with the new data provided.",
	    parameters = {
	        @Parameter(
	            name = "id",
	            description = "The ID of the provider to update",
	            required = true,
	            example = "1"
	        )
	    },
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Updated provider data",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ProveedorRequestDto.class)
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Successfully updated the provider",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "404",
	            description = "Provider not found"
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Bad request due to an unexpected error"
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error"
	        )
	    }
	)
	@PutMapping("/proveedores/{id}")
	public ResponseEntity<?> putProveedor(
			@PathVariable (name = "id") Long id, @Valid @RequestBody ProveedorRequestDto dto, BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
	        return comunService.getBindingResultError(bindingResult);
	    }
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
