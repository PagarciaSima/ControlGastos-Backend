package com.pgs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "estados")
@Data
public class EstadoModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@NotNull(message = "El nombre no puede ser nulo")  
	@Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")  
	private String nombre;
}
