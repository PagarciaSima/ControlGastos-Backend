package com.pgs.dto;

import lombok.Data;

@Data
public class GastoPorDiaDto {

	private Long neto;
	private Long iva;
	private Long total;
	private String descripcion;
	private Long proveedoresId;
}
