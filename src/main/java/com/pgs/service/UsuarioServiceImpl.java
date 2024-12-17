package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.UsuarioModel;
import com.pgs.repository.IUsuarioRepository;

import lombok.AllArgsConstructor;

/**
 * Implementation of the service for managing users (usuarios).
 * Provides methods to list, save, find, and delete users, as well as find users by email and active status.
 * 
 * @author Pablo Garcia Simavilla
 */
@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

    private IUsuarioRepository usuarioRepository;
    private IEstadoService estadosService;

    /**
     * Retrieves all users, sorted in descending order by their identifier.
     *
     * @return a list of {@link UsuarioModel} objects representing the users.
     */
    @Override
    public List<UsuarioModel> listar() {
        return this.usuarioRepository.findAll(Sort.by("id").descending());
    }
    
    /**
     * Saves a new user or updates an existing one in the database.
     *
     * @param modelo the user to be saved or updated.
     */
    @Override
    public void guardar(UsuarioModel modelo) {
        this.usuarioRepository.save(modelo);
    }
    
    /**
     * Finds a user by its unique identifier.
     *
     * @param id the identifier of the user to find.
     * @return the user with the provided id, or {@code null} if not found.
     */
    @Override
    public UsuarioModel buscarPorId(Long id) {
        Optional<UsuarioModel> optional = this.usuarioRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }
    
    /**
     * Deletes a user from the database by its unique identifier.
     *
     * @param id the identifier of the user to delete.
     */
    @Override
    public void eliminar(Long id) {
        this.usuarioRepository.deleteById(id);
    }
    
    /**
     * Finds a user by its email address.
     *
     * @param correo the email address of the user to find.
     * @return the user with the provided email address, or {@code null} if not found.
     */
    @Override
    public UsuarioModel buscarPorCorreo(String correo) {
        return this.usuarioRepository.findByCorreo(correo);
    }
    
    /**
     * Finds a user by its email address and active status.
     * Only returns the user if the status is active.
     *
     * @param correo the email address of the user to find.
     * @return the user with the provided email address and active status, or {@code null} if not found.
     */
    @Override
    public UsuarioModel buscarPorCorreoActivo(String correo) {
        Optional<UsuarioModel> optional = this.usuarioRepository
                .findByCorreoAndEstadosId(correo, this.estadosService.buscarPorId(ControlGastosConstants.ESTADO_ACTIVO));
        return optional.isPresent() ? optional.get() : null;
    }
}
