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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LoginController {

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private IUsuarioService usuarioService;
	private IJwtService jwtService;
	private IComunService comunService;
	
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
