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
