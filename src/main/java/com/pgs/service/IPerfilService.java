package com.pgs.service;

import java.util.List;

import com.pgs.model.PerfilModel;

public interface IPerfilService {

	List<PerfilModel> listar();

	void guardar(PerfilModel modelo);

	PerfilModel buscarPorId(Long id);

	void eliminar(Long id);

}