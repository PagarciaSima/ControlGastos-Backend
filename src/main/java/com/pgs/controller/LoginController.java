package com.pgs.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.dto.JwtResponseDto;
import com.pgs.dto.LoginDto;
import com.pgs.jwt.IJwtService;
import com.pgs.model.UsuarioModel;
import com.pgs.service.IComunService;
import com.pgs.service.IUsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

/**
 * Controller responsible for handling user login and authentication requests.
 * Provides endpoints for user login and token generation.
 * 
 * @author Pablo Garcia Simavilla
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name = "Login", description = "API for managing login")
public class LoginController {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private IUsuarioService usuarioService;
	private IJwtService jwtService;
	private IComunService comunService;
	
	 /**
     * Authenticates a user by email and password, and generates a JWT token if successful.
     *
     * @param dto The login data transfer object containing the user's email and password.
     * @return ResponseEntity containing a JWT token and user details if authentication is successful, 
     *         or an error message and HTTP status if authentication fails.
     */
	
	@Operation(
	    summary = "Authenticate user and generate JWT token",
	    description = "Authenticate user using email and password. Returns a JWT token if successful.",
	    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	        description = "User credentials",
	        required = true,
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = LoginDto.class),
	            examples = @ExampleObject(
	                name = "Login Example",
	                value = "{ \"correo\": \"pgs@hotmail.com\", \"password\": \"patata\" }"
	            )
	        )
	    ),
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "Authentication successful, JWT token returned",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = JwtResponseDto.class)
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "Authentication failed, invalid credentials",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"Unexpected error\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Server Error Example",
	                    value = "{ \"error\": \"An unexpected error occurred\" }"
	                )
	            )
	        )
	    }
	)
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody LoginDto dto) {
		UsuarioModel usuario = this.usuarioService.buscarPorCorreoActivo(dto.getCorreo());
		if (null == usuario ) 
			return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
		else {
			if (this.bCryptPasswordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
				String token = this.jwtService.generateToken(usuario.getCorreo());
				return ResponseEntity.status(HttpStatus.OK).body(
						new JwtResponseDto(
							usuario.getId(), usuario.getNombre(), usuario.getPerfilId().getNombre(),
							usuario.getPerfilId().getId(), token
						)
				);
			} else
				return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
		}
	}
	
	 /**
     * Refreshes the JWT token for a user based on their ID.
     *
     * @param id The ID of the user whose token needs to be refreshed.
     * @return ResponseEntity containing the new JWT token and user details if successful, 
     *         or an error message and HTTP status if the user is not found.
     */
	
	@Operation(
	    summary = "Refresh JWT token for a user",
	    description = "Generates a new JWT token for the user based on their ID.",
	    parameters = {
	        @io.swagger.v3.oas.annotations.Parameter(
	            name = "id",
	            description = "The ID of the user",
	            required = true,
	            example = "1"
	        )
	    },
	    responses = {
	        @ApiResponse(
	            responseCode = "200",
	            description = "JWT token refreshed successfully",
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = JwtResponseDto.class),
	                examples = @ExampleObject(
	                    name = "Success Example",
	                    value = "{ \"id\": 1, \"nombre\": \"Pablo\", \"perfil\": \"Admin\", \"perfilId\": 101, \"token\": \"jwt_token_value\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "400",
	            description = "User not found or invalid request",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Error Example",
	                    value = "{ \"error\": \"Unexpected error\" }"
	                )
	            )
	        ),
	        @ApiResponse(
	            responseCode = "500",
	            description = "Internal server error",
	            content = @Content(
	                mediaType = "application/json",
	                examples = @ExampleObject(
	                    name = "Server Error Example",
	                    value = "{ \"error\": \"An unexpected error occurred\" }"
	                )
	            )
	        )
	    }
	)
	@GetMapping("/auth/refresh/{id}")
	public ResponseEntity<?> refresh (@PathVariable ("id") Long id){
		UsuarioModel usuario = this.usuarioService.buscarPorId(id);
		if(null == usuario)
			return comunService.getResponseEntity(HttpStatus.BAD_REQUEST, ControlGastosConstants.ERROR_INESPERADO);
		else {
			return ResponseEntity.status(HttpStatus.OK).body(
					new JwtResponseDto(
						usuario.getId(), usuario.getNombre(), usuario.getPerfilId().getNombre(),
						usuario.getPerfilId().getId(), jwtService.generateToken(usuario.getCorreo())
					)
			);
		}

	}
}
