package com.pgs.dto;

import lombok.Data;

@Data
public class GastosFijosRequestDto {

	private String nombre;
	private Long monto;
	private Long estadosId;
	private Long proveedoresId;
}
