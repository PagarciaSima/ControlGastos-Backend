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
import com.pgs.model.EstadoModel;
import com.pgs.service.IEstadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * Controller for managing "Estado" resources.
 * Provides endpoints to retrieve all "Estado" and specific "Estado" used for expenses.
 * 
 * This controller handles requests mapped to "/api/v1".
 * It uses {@link IEstadoService} to perform the operations.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "State", description = "API for managing 'State' resources")
@SecurityRequirement(name = "bearerAuth")
public class EstadoController {

    private IEstadoService estadoService;

    /**
     * Retrieves a list of all "Estado" objects.
     * 
     * Endpoint: GET /api/v1/estados
     * 
     * @return a {@link ResponseEntity} containing a list of all "Estado" objects with an HTTP status of 200 (OK).
     */
    @Operation(
        summary = "Get all Estados",
        description = "Retrieves a list of all available 'Estado' objects.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of all 'Estado' objects",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EstadoModel.class))
                )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/estados")
    public ResponseEntity<?> getAllEstados() {
        return ResponseEntity.status(HttpStatus.OK).body(this.estadoService.listar());
    }

    /**
     * Retrieves a list of "Estado" objects used for expenses.
     * 
     * Endpoint: GET /api/v1/estados-gastos
     * 
     * @return a {@link ResponseEntity} containing a filtered list of "Estado" objects with an HTTP status of 200 (OK).
     */
    @Operation(
        summary = "Get Estados for expenses",
        description = "Retrieves a list of 'Estado' objects filtered by specific expense-related IDs.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "List of 'Estado' objects used for expenses",
                content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EstadoModel.class))
                )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/estados-gastos")
    public ResponseEntity<?> getAllEstadosGastos() {
        List<Long> ids = new ArrayList<>();
        ids.add(ControlGastosConstants.ESTADO_PAGADO);
        ids.add(ControlGastosConstants.ESTADO_NO_PAGADO);
        return ResponseEntity.status(HttpStatus.OK).body(this.estadoService.listarParaGastos(ids));
    }
}
