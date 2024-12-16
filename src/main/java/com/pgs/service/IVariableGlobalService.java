package com.pgs.service;

import java.util.List;

import com.pgs.model.VariableGlobalModel;

public interface IVariableGlobalService {

	List<VariableGlobalModel> listar();

	void guardar(VariableGlobalModel modelo);

	VariableGlobalModel buscarPorId(Long id);

	void eliminar(Long id);

}