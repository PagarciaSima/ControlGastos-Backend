package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.GastoFijoModel;
import com.pgs.repository.IGastoFijoResitory;

@Service
public class GastoFijoService {

	private IGastoFijoResitory gastoFijoRepository;

	public GastoFijoService(IGastoFijoResitory gastoFijoRepository) {
		this.gastoFijoRepository = gastoFijoRepository;
	}
	
	public List<GastoFijoModel> listar() {
		return this.gastoFijoRepository.findAll(Sort.by("id").descending());
	}
	
	public void guardar (GastoFijoModel modelo) {
		this.gastoFijoRepository.save(modelo);
	}
	
	public GastoFijoModel buscarPorId(Long id) {
		Optional<GastoFijoModel> optional = this.gastoFijoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.gastoFijoRepository.deleteById(id);
	}
}
