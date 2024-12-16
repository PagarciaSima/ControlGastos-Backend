package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.PerfilModel;
import com.pgs.repository.IPerfilRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PerfilServiceImpl implements IPerfilService {

	private IPerfilRepository perfilRepository;
	
	@Override
	public List<PerfilModel> listar() {
		return this.perfilRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public void guardar (PerfilModel modelo) {
		this.perfilRepository.save(modelo);
	}
	
	@Override
	public PerfilModel buscarPorId(Long id) {
		Optional<PerfilModel> optional = this.perfilRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.perfilRepository.deleteById(id);
	}
}
