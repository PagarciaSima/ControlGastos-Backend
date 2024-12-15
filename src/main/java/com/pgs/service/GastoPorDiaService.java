package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.GastoPorDiaModel;
import com.pgs.repository.IGastoPorDiaResitory;

@Service
public class GastoPorDiaService {

	private IGastoPorDiaResitory gastoPorDiaResitory;

	public GastoPorDiaService(IGastoPorDiaResitory gastoPorDiaResitory) {
		this.gastoPorDiaResitory = gastoPorDiaResitory;
	}
	
	public List<GastoPorDiaModel> listar() {
		return this.gastoPorDiaResitory.findAll(Sort.by("id").descending());
	}
	
	public List<GastoPorDiaModel> listarPorAnioMes(Integer mes, Integer anio) {
		return this.gastoPorDiaResitory.findAllByMonth(mes, anio);
	}
	
	public void guardar (GastoPorDiaModel modelo) {
		this.gastoPorDiaResitory.save(modelo);
	}
	
	public GastoPorDiaModel buscarPorId(Long id) {
		Optional<GastoPorDiaModel> optional = this.gastoPorDiaResitory.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.gastoPorDiaResitory.deleteById(id);
	}
}
