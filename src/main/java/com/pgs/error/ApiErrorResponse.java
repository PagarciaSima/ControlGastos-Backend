package com.pgs.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiErrorResponse {
	
	private int codigo;
	private String mensaje;
}
