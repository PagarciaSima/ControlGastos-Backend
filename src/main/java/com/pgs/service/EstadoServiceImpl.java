package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.EstadoModel;
import com.pgs.repository.IEstadoRepository;

import lombok.AllArgsConstructor;

/**
 * Implementation of the {@link IEstadoService} interface that provides methods for managing {@link EstadoModel} entities.
 * This service interacts with the repository to handle CRUD operations related to "estado" (state) records.
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class EstadoServiceImpl implements IEstadoService {

	private IEstadoRepository estadoRepository;
	
	/**
	 * Retrieves all {@link EstadoModel} entities from the database, sorted by their ID in descending order.
	 * 
	 * @return A list of all {@link EstadoModel} entities.
	 */
	@Override
	public List<EstadoModel> listar() {
		return this.estadoRepository.findAll(Sort.by("id").descending());
	}
	
	/**
	 * Retrieves a list of {@link EstadoModel} entities where the ID matches any of the provided IDs.
	 * This method is typically used when filtering "estado" records for certain operations like managing gastos.
	 * 
	 * @param id A list of IDs of the {@link EstadoModel} entities to retrieve.
	 * @return A list of matching {@link EstadoModel} entities.
	 */
	@Override
	public List<EstadoModel> listarParaGastos(List<Long> id) {
		return this.estadoRepository.findByIdIn(id);
	}
	
	/**
	 * Saves the given {@link EstadoModel} entity to the database.
	 * If the entity already exists, it will be updated.
	 * 
	 * @param modelo The {@link EstadoModel} entity to be saved.
	 */
	@Override
	public void guardar (EstadoModel modelo) {
		this.estadoRepository.save(modelo);
	}
	
	/**
	 * Retrieves a specific {@link EstadoModel} by its ID.
	 * 
	 * @param id The ID of the {@link EstadoModel} entity to retrieve.
	 * @return The {@link EstadoModel} entity, or null if not found.
	 */
	@Override
	public EstadoModel buscarPorId(Long id) {
		Optional<EstadoModel> optional = this.estadoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	/**
	 * Deletes the {@link EstadoModel} entity with the specified ID.
	 * 
	 * @param id The ID of the {@link EstadoModel} entity to be deleted.
	 */
	@Override
	public void eliminar(Long id) {
		this.estadoRepository.deleteById(id);
	}
}
