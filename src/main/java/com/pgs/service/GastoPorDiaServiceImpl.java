package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.GastoPorDiaModel;
import com.pgs.repository.IGastoPorDiaResitory;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GastoPorDiaServiceImpl implements IGastoPorDiaService {

	private IGastoPorDiaResitory gastoPorDiaResitory;

	@Override
	public List<GastoPorDiaModel> listar() {
		return this.gastoPorDiaResitory.findAll(Sort.by("id").descending());
	}
	
	@Override
	public List<GastoPorDiaModel> listarPorAnioMes(Integer mes, Integer anio) {
		return this.gastoPorDiaResitory.findAllByMonth(mes, anio);
	}
	
	@Override
	public void guardar (GastoPorDiaModel modelo) {
		this.gastoPorDiaResitory.save(modelo);
	}
	
	@Override
	public GastoPorDiaModel buscarPorId(Long id) {
		Optional<GastoPorDiaModel> optional = this.gastoPorDiaResitory.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.gastoPorDiaResitory.deleteById(id);
	}
}
