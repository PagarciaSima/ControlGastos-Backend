package com.pgs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuariosResponseDto {
	private Long id;
	private String nombre;
	private String correo;
	private String perfil;
	private Long perfilId;
	private Long EstadoId;
	private String estado;
	
}
