package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.VariableGlobalModel;
import com.pgs.repository.IVariableGlobalRepository;

import lombok.AllArgsConstructor;

/**
 * Implementation of the service for managing global variables.
 * Provides methods to list, save, find, and delete global variables.
 * 
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class VariableGlobalServiceImpl implements IVariableGlobalService {

    private IVariableGlobalRepository variableGLobalRepository;
    
    /**
     * Retrieves all global variables, sorted in descending order by their identifier.
     *
     * @return a list of {@link VariableGlobalModel} objects representing the global variables.
     */
    @Override
    public List<VariableGlobalModel> listar() {
        return this.variableGLobalRepository.findAll(Sort.by("id").descending());
    }
    
    /**
     * Saves a new global variable or updates an existing one in the database.
     *
     * @param modelo the global variable to be saved or updated.
     */
    @Override
    public void guardar(VariableGlobalModel modelo) {
        this.variableGLobalRepository.save(modelo);
    }
    
    /**
     * Finds a global variable by its unique identifier.
     *
     * @param id the identifier of the global variable to find.
     * @return the global variable with the provided id, or {@code null} if not found.
     */
    @Override
    public VariableGlobalModel buscarPorId(Long id) {
        Optional<VariableGlobalModel> optional = this.variableGLobalRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }
    
    /**
     * Deletes a global variable from the database by its unique identifier.
     *
     * @param id the identifier of the global variable to delete.
     */
    @Override
    public void eliminar(Long id) {
        this.variableGLobalRepository.deleteById(id);
    }
}
