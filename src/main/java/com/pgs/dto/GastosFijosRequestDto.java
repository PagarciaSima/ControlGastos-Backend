package com.pgs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GastosFijosRequestDto {

	@NotEmpty(message = "El nombre no puede ser nulo")
	@Size(min = 1, max = 255, message = "El nombre debe tener entre 1 y 255 caracteres")
	private String nombre;

	@NotNull(message = "El monto no puede ser nulo")
	@Min(value = 0, message = "El monto debe ser mayor o igual a 0")
	private Long monto;

	@NotNull(message = "El ID del proveedor no puede ser nulo")
	private Long proveedoresId;

	@NotNull(message = "El ID del estado no puede ser nulo")
	private Long estadosId;
}
