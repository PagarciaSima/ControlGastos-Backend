package com.pgs.service;

import java.util.List;

import com.pgs.model.ProveedorModel;

public interface IProveedorService {

	List<ProveedorModel> listar();

	void guardar(ProveedorModel modelo);

	ProveedorModel buscarPorId(Long id);

	void eliminar(Long id);

}