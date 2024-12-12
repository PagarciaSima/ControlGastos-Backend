package com.pgs.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "gastos_por_dia")
@Data
public class GastoPorDiaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Long neto;
	private Long iva;
	private Long total;
	private Date fecha;
	@Column(length = 65535, columnDefinition = "text")
	private String descripcion;
	
	// Foreign Key - proveedores
	@ManyToOne()
	@JoinColumn(name = "proveedores_id")
	private ProveedorModel proveedoresId;
	
	public GastoPorDiaModel() {
		super();
	}

	public GastoPorDiaModel(Long neto, Long iva, Long total, Date fecha, String descripcion,
			ProveedorModel proveedoresId) {
		super();
		this.neto = neto;
		this.iva = iva;
		this.total = total;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.proveedoresId = proveedoresId;
	}
	
}
