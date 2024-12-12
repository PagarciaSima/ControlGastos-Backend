package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.PerfilModel;
import com.pgs.repository.IPerfilRepository;

@Service
public class PerfilService {

	private IPerfilRepository perfilRepository;

	public PerfilService(IPerfilRepository perfilRepository) {
		this.perfilRepository = perfilRepository;
	}
	
	public List<PerfilModel> listar() {
		return this.perfilRepository.findAll(Sort.by("id").descending());
	}
	
	public void guardar (PerfilModel modelo) {
		this.perfilRepository.save(modelo);
	}
	
	public PerfilModel buscarPorId(Long id) {
		Optional<PerfilModel> optional = this.perfilRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.perfilRepository.deleteById(id);
	}
}
