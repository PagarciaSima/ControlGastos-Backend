package com.pgs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponseDto {

	private Long id;
	private String nombre;
	private String perfil;
	private Long perfilId;
	private String token;
}
