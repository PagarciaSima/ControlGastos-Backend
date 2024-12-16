package com.pgs.service;

import java.util.List;

import com.pgs.model.GastoPorDiaModel;

public interface IGastoPorDiaService {

	List<GastoPorDiaModel> listar();

	List<GastoPorDiaModel> listarPorAnioMes(Integer mes, Integer anio);

	void guardar(GastoPorDiaModel modelo);

	GastoPorDiaModel buscarPorId(Long id);

	void eliminar(Long id);

}