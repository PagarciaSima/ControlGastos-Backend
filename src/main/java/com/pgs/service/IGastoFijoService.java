package com.pgs.service;

import java.util.List;

import com.pgs.model.GastoFijoModel;

public interface IGastoFijoService {

	List<GastoFijoModel> listarPorMesYanio(Integer mes, Integer anio);

	List<GastoFijoModel> listar();

	void guardar(GastoFijoModel modelo);

	GastoFijoModel buscarPorId(Long id);

	void eliminar(Long id);

}