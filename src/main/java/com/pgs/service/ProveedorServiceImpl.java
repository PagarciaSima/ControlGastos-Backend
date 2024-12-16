package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.ProveedorModel;
import com.pgs.repository.IProveedorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProveedorServiceImpl implements IProveedorService {

	private IProveedorRepository proveedorRepository;
	
	@Override
	public List<ProveedorModel> listar() {
		return this.proveedorRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public void guardar (ProveedorModel modelo) {
		this.proveedorRepository.save(modelo);
	}
	
	@Override
	public ProveedorModel buscarPorId(Long id) {
		Optional<ProveedorModel> optional = this.proveedorRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.proveedorRepository.deleteById(id);
	}
}
