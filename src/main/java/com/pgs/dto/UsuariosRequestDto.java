package com.pgs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuariosRequestDto {
	private String nombre;
	private String correo;
	private String password;
}
