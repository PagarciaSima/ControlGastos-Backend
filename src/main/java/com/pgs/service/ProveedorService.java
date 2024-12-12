package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.ProveedorModel;
import com.pgs.repository.IProveedorRepository;

@Service
public class ProveedorService {

	private IProveedorRepository proveedorRepository;

	public ProveedorService(IProveedorRepository proveedorRepository) {
		this.proveedorRepository = proveedorRepository;
	}
	
	public List<ProveedorModel> listar() {
		return this.proveedorRepository.findAll(Sort.by("id").descending());
	}
	
	public void guardar (ProveedorModel modelo) {
		this.proveedorRepository.save(modelo);
	}
	
	public ProveedorModel buscarPorId(Long id) {
		Optional<ProveedorModel> optional = this.proveedorRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.proveedorRepository.deleteById(id);
	}
}
