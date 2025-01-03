package com.pgs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.pgs.dto.UsuariosRequestDto;
import com.pgs.dto.UsuariosResponseDto;
import com.pgs.model.UsuarioModel;
import com.pgs.service.IComunService;
import com.pgs.service.IEstadoService;
import com.pgs.service.IPerfilService;
import com.pgs.service.IUsuarioService;

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
 * Controller for handling user-related operations.
 * Provides endpoints to:
 * <ul>
 *     <li>GET /api/v1/usuarios - Retrieve all users</li>
 *     <li>POST /api/v1/usuario - Create a new user</li>
 *     <li>PUT /api/v1/usuario/{id} - Update an existing user</li>
 * </ul>
 * 
 * Interacts with services for user management, profiles, and states.
 * 
 * @author Pablo Garcia Simavilla
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Tag(name = "Users", description = "API for managing 'User' resources")
@SecurityRequirement(name = "bearerAuth")
public class UsuariosController {
 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private IUsuarioService usuarioService;
	private IEstadoService estadoService;
	private IPerfilService perfilService;
	private IComunService comunService;

	/**
	 * Retrieves a list of all users.
	 * 
	 * @return ResponseEntity containing a list of users in the form of {@link UsuariosResponseDto}.
	 *         If successful, HTTP status 200 (OK) is returned.
	 */
	@Operation(
	    summary = "Get all users",
	    description = "Retrieves a list of all users with their details (name, email, profile, state).",
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Successfully retrieved the list of users",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = UsuariosResponseDto.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error"
	        )
	    }
	)
	@GetMapping("/usuarios")
	public ResponseEntity<?> getUsuarios() {
		List<UsuariosResponseDto> listaDto = new ArrayList<>();
		List<UsuarioModel> datos = this.usuarioService.listar();
		
		datos.forEach((dato) -> {
			listaDto.add(
				new UsuariosResponseDto(
						dato.getId(), dato.getNombre(), dato.getCorreo(), 
						dato.getPerfilId().getNombre(), dato.getPerfilId().getId(),
						dato.getEstadosId().getId(), dato.getEstadosId().getNombre()				
				)		
			);
		});
		return ResponseEntity.status(HttpStatus.OK).body(listaDto);
	}
	
	/**
	 * Creates a new user.
	 * 
	 * <p>Checks if the provided email is already in use. If the email is taken, returns HTTP status 409 
	 * (Conflict). Otherwise, a new user is created and saved, and HTTP status 201 (Created) is returned.</p>
	 *
	 * @param dto The request body containing the user data to be created, represented by 
	 *            {@link UsuariosRequestDto}.
	 * @return ResponseEntity indicating the result of the operation (either conflict or success).
	 */
	@Operation(
	    summary = "Create a new user",
	    description = "Creates a new user in the system if the provided email is not already in use.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "User data to be created",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = UsuariosRequestDto.class)
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "201",
	            description = "Successfully created the user",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "409",
	            description = "Conflict due to email already being in use"
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error"
	        )
	    }
	)
	@PostMapping("/usuario")
	public ResponseEntity<?> postUsuario(@Valid @RequestBody UsuariosRequestDto dto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
	        return comunService.getBindingResultError(bindingResult);
	    }
		UsuarioModel usuario = this.usuarioService.buscarPorCorreo(dto.getCorreo());
		if (null != usuario)
			return comunService.getResponseEntity(HttpStatus.CONFLICT, ControlGastosConstants.EMAIL_REPETIDO);
		else {
			this.usuarioService.guardar(new UsuarioModel(
				dto.getNombre(),
				dto.getCorreo(),
				this.bCryptPasswordEncoder.encode(dto.getPassword()),
				"",
				new Date(),
				this.perfilService.buscarPorId(ControlGastosConstants.PERFIL_USUARIO),
				this.estadoService.buscarPorId(ControlGastosConstants.ESTADO_ACTIVO)
			));
			return comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_CREAR_REGISTRO);

		}
	}
	
	/**
	 * Updates an existing user.
	 * 
	 * <p>Checks if the user exists based on the provided email. If the user is not found, returns HTTP 
	 * status 400 (Bad Request). If found, updates the user's details (name, email, password) and saves 
	 * the changes. Returns HTTP status 201 (Created) upon success.</p>
	 *
	 * @param id  The ID of the user to be updated.
	 * @param dto The request body containing the updated user data, represented by 
	 *            {@link UsuariosRequestDto}.
	 * @return ResponseEntity indicating the result of the operation (either not found or success).
	 */
	@Operation(
	    summary = "Update an existing user",
	    description = "Updates the user's details (name, email, password) by their ID.",
	    parameters = {
	        @Parameter(
	            name = "id",
	            description = "The ID of the user to update",
	            required = true,
	            example = "1"
	        )
	    },
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "Updated user data",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = UsuariosRequestDto.class)
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Successfully updated the user",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = String.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Bad request due to user not found"
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error"
	        )
	    }
	)
	@PutMapping("/usuario/{id}")
	public ResponseEntity<?> putUsuario(
			@PathVariable ("id") Long id, @Valid @RequestBody UsuariosRequestDto dto, BindingResult bindingResult
		) {
		if (bindingResult.hasErrors()) {
	        return comunService.getBindingResultError(bindingResult);
	    }
		UsuarioModel usuario = this.usuarioService.buscarPorCorreo(dto.getCorreo());
		if (null == usuario)
			return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.NO_ENCONTRADO);
		else {
			usuario.setNombre(dto.getNombre());
			usuario.setCorreo(dto.getCorreo());
			usuario.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
			this.usuarioService.guardar(usuario);
			return comunService.getResponseEntity(HttpStatus.CREATED, ControlGastosConstants.EXITO_ACTUALIZAR_REGISTRO);

		}
	}
	
}
