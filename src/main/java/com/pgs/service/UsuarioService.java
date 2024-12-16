package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.UsuarioModel;
import com.pgs.repository.IUsuarioRepository;

@Service
public class UsuarioService {

	private IUsuarioRepository usuarioRepository;
	private EstadoService estadosService;

	public UsuarioService(IUsuarioRepository usuarioRepository, EstadoService estadosService) {
		this.usuarioRepository = usuarioRepository;
		this.estadosService = estadosService;
	}
	
	public List<UsuarioModel> listar() {
		return this.usuarioRepository.findAll(Sort.by("id").descending());
	}
	
	public void guardar (UsuarioModel modelo) {
		this.usuarioRepository.save(modelo);
	}
	
	public UsuarioModel buscarPorId(Long id) {
		Optional<UsuarioModel> optional = this.usuarioRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	public void eliminar(Long id) {
		this.usuarioRepository.deleteById(id);
	}
	
	public UsuarioModel buscarPorCorreo(String correo) {
		return this.usuarioRepository.findByCorreo(correo);
	}
	
	public UsuarioModel buscarPorCorreoActivo(String correo) {
		Optional<UsuarioModel> optional = this.usuarioRepository
				.findByCorreoAndEstadosId(correo, this.estadosService.buscarPorId(ControlGastosConstants.ESTADO_ACTIVO));
		return optional.isPresent() ? optional.get() : null;
	}
}
