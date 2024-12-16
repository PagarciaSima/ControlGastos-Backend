package com.pgs.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class UsuariosController {
 
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private IUsuarioService usuarioService;
	private IEstadoService estadoService;
	private IPerfilService perfilService;
	private IComunService comunService;

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
	
	@PostMapping("/usuario")
	public ResponseEntity<?> postUsuario(@RequestBody UsuariosRequestDto dto) {
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
}
