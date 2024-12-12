package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.VariableGlobalModel;
import com.pgs.repository.IVariableGlobalRepository;

@Service
public class VariableGlobalService {

	private IVariableGlobalRepository variableGLobalRepository;

	public VariableGlobalService(IVariableGlobalRepository variableGLobalRepository) {
		this.variableGLobalRepository = variableGLobalRepository;
	}
	
	public List<VariableGlobalModel> listar() {
		return this.variableGLobalRepository.findAll(Sort.by("id").descending());
	}
	
	public void guardar (VariableGlobalModel modelo) {
		this.variableGLobalRepository.save(modelo);
	}
	
	public VariableGlobalModel buscarPorId(Long id) {
		Optional<VariableGlobalModel> optional = this.variableGLobalRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.variableGLobalRepository.deleteById(id);
	}
}
