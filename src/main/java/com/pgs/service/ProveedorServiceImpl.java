package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.ProveedorModel;
import com.pgs.repository.IProveedorRepository;

import lombok.AllArgsConstructor;

/**
 * Implementation of the service for managing suppliers (proveedores).
 * Provides methods to list, save, find, and delete suppliers.
 * 
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class ProveedorServiceImpl implements IProveedorService {

    private IProveedorRepository proveedorRepository;
    
    /**
     * Retrieves all suppliers, sorted in descending order by their identifier.
     *
     * @return a list of {@link ProveedorModel} objects representing the suppliers.
     */
    @Override
    public List<ProveedorModel> listar() {
        return this.proveedorRepository.findAll(Sort.by("id").descending());
    }
    
    /**
     * Saves a new supplier or updates an existing one in the database.
     *
     * @param modelo the supplier to be saved or updated.
     */
    @Override
    public void guardar(ProveedorModel modelo) {
        this.proveedorRepository.save(modelo);
    }
    
    /**
     * Finds a supplier by its unique identifier.
     *
     * @param id the identifier of the supplier to find.
     * @return the supplier with the provided id, or {@code null} if not found.
     */
    @Override
    public ProveedorModel buscarPorId(Long id) {
        Optional<ProveedorModel> optional = this.proveedorRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }
    
    /**
     * Deletes a supplier from the database by its unique identifier.
     *
     * @param id the identifier of the supplier to delete.
     */
    @Override
    public void eliminar(Long id) {
        this.proveedorRepository.deleteById(id);
    }
}
