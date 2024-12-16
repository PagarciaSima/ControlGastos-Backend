package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.VariableGlobalModel;
import com.pgs.repository.IVariableGlobalRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VariableGlobalServiceImpl implements IVariableGlobalService {

	private IVariableGlobalRepository variableGLobalRepository;
	
	@Override
	public List<VariableGlobalModel> listar() {
		return this.variableGLobalRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public void guardar (VariableGlobalModel modelo) {
		this.variableGLobalRepository.save(modelo);
	}
	
	@Override
	public VariableGlobalModel buscarPorId(Long id) {
		Optional<VariableGlobalModel> optional = this.variableGLobalRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.variableGLobalRepository.deleteById(id);
	}
}
