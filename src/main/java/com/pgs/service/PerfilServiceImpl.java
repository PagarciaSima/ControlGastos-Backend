package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.model.PerfilModel;
import com.pgs.repository.IPerfilRepository;

import lombok.AllArgsConstructor;

/**
 * Implementation of the service for managing user profiles.
 * Provides methods to list, save, find, and delete profiles.
 * 
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class PerfilServiceImpl implements IPerfilService {

    private IPerfilRepository perfilRepository;
    
    /**
     * Retrieves all profiles, sorted in descending order by their identifier.
     *
     * @return a list of {@link PerfilModel} objects representing the profiles.
     */
    @Override
    public List<PerfilModel> listar() {
        return this.perfilRepository.findAll(Sort.by("id").descending());
    }
    
    /**
     * Saves a new profile or updates an existing one in the database.
     *
     * @param modelo the profile to be saved or updated.
     */
    @Override
    public void guardar(PerfilModel modelo) {
        this.perfilRepository.save(modelo);
    }
    
    /**
     * Finds a profile by its unique identifier.
     *
     * @param id the identifier of the profile to find.
     * @return the profile with the provided id, or {@code null} if not found.
     */
    @Override
    public PerfilModel buscarPorId(Long id) {
        Optional<PerfilModel> optional = this.perfilRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }
    
    /**
     * Deletes a profile from the database by its unique identifier.
     *
     * @param id the identifier of the profile to delete.
     */
    @Override
    public void eliminar(Long id) {
        this.perfilRepository.deleteById(id);
    }
}
