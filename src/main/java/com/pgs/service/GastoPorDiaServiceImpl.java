package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.GastoPorDiaModel;
import com.pgs.repository.IGastoPorDiaResitory;

import lombok.AllArgsConstructor;

/**
 * Implementation of the {@link IGastoPorDiaService} interface that provides methods for managing {@link GastoPorDiaModel} entities.
 * This service interacts with the repository to handle CRUD operations related to "gastos por día" (expenses per day) records.
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class GastoPorDiaServiceImpl implements IGastoPorDiaService {

	private IGastoPorDiaResitory gastoPorDiaResitory;

	/**
	 * Retrieves all {@link GastoPorDiaModel} entities from the database, sorted by their ID in descending order.
	 * 
	 * @return A list of all {@link GastoPorDiaModel} entities.
	 */
	@Override
	public List<GastoPorDiaModel> listar() {
		return this.gastoPorDiaResitory.findAll(Sort.by("id").descending());
	}
	
	/**
	 * Retrieves a list of {@link GastoPorDiaModel} entities filtered by the provided month and year.
	 * This method is typically used when retrieving daily expenses for a specific month and year.
	 * 
	 * @param mes The month (1 to 12) for which to retrieve the daily expenses.
	 * @param anio The year for which to retrieve the daily expenses.
	 * @return A list of {@link GastoPorDiaModel} entities that match the specified month and year.
	 */
	@Override
	public List<GastoPorDiaModel> listarPorAnioMes(Integer mes, Integer anio) {
		return this.gastoPorDiaResitory.findAllByMonth(mes, anio);
	}
	
	/**
	 * Saves the given {@link GastoPorDiaModel} entity to the database.
	 * If the entity already exists, it will be updated.
	 * 
	 * @param modelo The {@link GastoPorDiaModel} entity to be saved.
	 */
	@Override
	public void guardar (GastoPorDiaModel modelo) {
		this.gastoPorDiaResitory.save(modelo);
	}
	
	/**
	 * Retrieves a specific {@link GastoPorDiaModel} by its ID.
	 * 
	 * @param id The ID of the {@link GastoPorDiaModel} entity to retrieve.
	 * @return The {@link GastoPorDiaModel} entity, or null if not found.
	 */
	@Override
	public GastoPorDiaModel buscarPorId(Long id) {
		Optional<GastoPorDiaModel> optional = this.gastoPorDiaResitory.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	/**
	 * Deletes the {@link GastoPorDiaModel} entity with the specified ID.
	 * 
	 * @param id The ID of the {@link GastoPorDiaModel} entity to be deleted.
	 */
	@Override
	public void eliminar(Long id) {
		this.gastoPorDiaResitory.deleteById(id);
	}
}
