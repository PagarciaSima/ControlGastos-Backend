package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.GastoFijoModel;
import com.pgs.repository.IGastoFijoResitory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GastoFijoServiceImpl implements IGastoFijoService {

	private IGastoFijoResitory gastoFijoRepository;
	
	@Override
	public List<GastoFijoModel> listarPorMesYanio(Integer mes, Integer anio) {
		return this.gastoFijoRepository.findAllByMonth(mes, anio);
	}
	
	@Override
	public List<GastoFijoModel> listar() {
		return this.gastoFijoRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public void guardar (GastoFijoModel modelo) {
		this.gastoFijoRepository.save(modelo);
	}
	
	@Override
	public GastoFijoModel buscarPorId(Long id) {
		Optional<GastoFijoModel> optional = this.gastoFijoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.gastoFijoRepository.deleteById(id);
	}
}
