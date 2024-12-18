package com.pgs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GastoPorDiaDto {

	@NotNull(message = "El valor neto no puede ser nulo")
	@Min(value = 0, message = "El valor neto debe ser mayor o igual a 0")
	private Long neto;

	@NotNull(message = "El valor IVA no puede ser nulo")
	@Min(value = 0, message = "El valor IVA debe ser mayor o igual a 0")
	private Long iva;

	@NotNull(message = "El valor total no puede ser nulo")
	@Min(value = 0, message = "El valor total debe ser mayor o igual a 0")
	private Long total;

	@Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
	private String descripcion;

	@NotNull(message = "El ID del proveedor no puede ser nulo")
	private Long proveedoresId;
}
