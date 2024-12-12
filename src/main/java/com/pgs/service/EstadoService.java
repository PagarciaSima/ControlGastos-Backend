package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.EstadoModel;
import com.pgs.repository.IEstadoRepository;

@Service
public class EstadoService {

	private IEstadoRepository estadoRepository;

	public EstadoService(IEstadoRepository estadoRepository) {
		this.estadoRepository = estadoRepository;
	}
	
	public List<EstadoModel> listar() {
		return this.estadoRepository.findAll(Sort.by("id").descending());
	}
	
	public List<EstadoModel> listarParaGastos(List<Long> id) {
		return this.estadoRepository.findByIdIn(id);
	}
	
	public void guardar (EstadoModel modelo) {
		this.estadoRepository.save(modelo);
	}
	
	public EstadoModel buscarPorId(Long id) {
		Optional<EstadoModel> optional = this.estadoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.estadoRepository.deleteById(id);
	}
}
