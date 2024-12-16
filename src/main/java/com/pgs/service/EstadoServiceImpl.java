package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.EstadoModel;
import com.pgs.repository.IEstadoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EstadoServiceImpl implements IEstadoService {

	private IEstadoRepository estadoRepository;
	
	@Override
	public List<EstadoModel> listar() {
		return this.estadoRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public List<EstadoModel> listarParaGastos(List<Long> id) {
		return this.estadoRepository.findByIdIn(id);
	}
	
	@Override
	public void guardar (EstadoModel modelo) {
		this.estadoRepository.save(modelo);
	}
	
	@Override
	public EstadoModel buscarPorId(Long id) {
		Optional<EstadoModel> optional = this.estadoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.estadoRepository.deleteById(id);
	}
}
