package com.pgs.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gastos_fijos")
@Data
public class GastoFijoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private String nombre;
	private Long monto;
	private Date fecha;
	
	// Foreign Key - estados
	@ManyToOne()
	@JoinColumn(name = "estados_Id")
	private EstadoModel estadosId;
	
	// Foreign Key - proveedores
	@ManyToOne()
	@JoinColumn(name = "proveedores_id")
	private ProveedorModel proveedoresId;
	
	public GastoFijoModel() {
		super();
	}
	
	public GastoFijoModel(String nombre, Long monto, Date fecha, EstadoModel estadosId, ProveedorModel proveedoresId) {
		super();
		this.nombre = nombre;
		this.monto = monto;
		this.fecha = fecha;
		this.estadosId = estadosId;
		this.proveedoresId = proveedoresId;
	}	
	
}
