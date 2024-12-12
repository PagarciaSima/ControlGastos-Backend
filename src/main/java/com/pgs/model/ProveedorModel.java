package com.pgs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proveedores")
@Data
public class ProveedorModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	
	
	public ProveedorModel() {
		super();
	}
	
	public ProveedorModel(String nombre) {
		super();
		this.nombre = nombre;
	}
	
}
