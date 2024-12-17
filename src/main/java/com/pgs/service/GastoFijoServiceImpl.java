package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.GastoFijoModel;
import com.pgs.repository.IGastoFijoResitory;

import lombok.AllArgsConstructor;

/**
 * Implementation of the {@link IGastoFijoService} interface that provides methods for managing {@link GastoFijoModel} entities.
 * This service interacts with the repository to handle CRUD operations related to "gastos fijos" (fixed expenses) records.
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class GastoFijoServiceImpl implements IGastoFijoService {

	private IGastoFijoResitory gastoFijoRepository;
	
	/**
	 * Retrieves a list of {@link GastoFijoModel} entities filtered by the provided month and year.
	 * This method is typically used when retrieving fixed expenses for a specific month and year.
	 * 
	 * @param mes The month (1 to 12) for which to retrieve the fixed expenses.
	 * @param anio The year for which to retrieve the fixed expenses.
	 * @return A list of {@link GastoFijoModel} entities that match the specified month and year.
	 */
	@Override
	public List<GastoFijoModel> listarPorMesYanio(Integer mes, Integer anio) {
		return this.gastoFijoRepository.findAllByMonth(mes, anio);
	}
	
	/**
	 * Retrieves all {@link GastoFijoModel} entities from the database, sorted by their ID in descending order.
	 * 
	 * @return A list of all {@link GastoFijoModel} entities.
	 */
	@Override
	public List<GastoFijoModel> listar() {
		return this.gastoFijoRepository.findAll(Sort.by("id").descending());
	}
	
	/**
	 * Saves the given {@link GastoFijoModel} entity to the database.
	 * If the entity already exists, it will be updated.
	 * 
	 * @param modelo The {@link GastoFijoModel} entity to be saved.
	 */
	@Override
	public void guardar (GastoFijoModel modelo) {
		this.gastoFijoRepository.save(modelo);
	}
	
	/**
	 * Retrieves a specific {@link GastoFijoModel} by its ID.
	 * 
	 * @param id The ID of the {@link GastoFijoModel} entity to retrieve.
	 * @return The {@link GastoFijoModel} entity, or null if not found.
	 */
	@Override
	public GastoFijoModel buscarPorId(Long id) {
		Optional<GastoFijoModel> optional = this.gastoFijoRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	/**
	 * Deletes the {@link GastoFijoModel} entity with the specified ID.
	 * 
	 * @param id The ID of the {@link GastoFijoModel} entity to be deleted.
	 */
	@Override
	public void eliminar(Long id) {
		this.gastoFijoRepository.deleteById(id);
	}
}
