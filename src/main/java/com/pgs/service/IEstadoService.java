package com.pgs.service;

import java.util.List;

import com.pgs.model.EstadoModel;

public interface IEstadoService {

	List<EstadoModel> listar();

	List<EstadoModel> listarParaGastos(List<Long> id);

	void guardar(EstadoModel modelo);

	EstadoModel buscarPorId(Long id);

	void eliminar(Long id);

}