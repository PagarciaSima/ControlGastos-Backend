package com.pgs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pgs.constantes.ControlGastosConstants;
import com.pgs.model.UsuarioModel;
import com.pgs.repository.IUsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements IUsuarioService {

	private IUsuarioRepository usuarioRepository;
	private IEstadoService estadosService;

	@Override
	public List<UsuarioModel> listar() {
		return this.usuarioRepository.findAll(Sort.by("id").descending());
	}
	
	@Override
	public void guardar (UsuarioModel modelo) {
		this.usuarioRepository.save(modelo);
	}
	
	@Override
	public UsuarioModel buscarPorId(Long id) {
		Optional<UsuarioModel> optional = this.usuarioRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Override
	public void eliminar(Long id) {
		this.usuarioRepository.deleteById(id);
	}
	
	@Override
	public UsuarioModel buscarPorCorreo(String correo) {
		return this.usuarioRepository.findByCorreo(correo);
	}
	
	@Override
	public UsuarioModel buscarPorCorreoActivo(String correo) {
		Optional<UsuarioModel> optional = this.usuarioRepository
				.findByCorreoAndEstadosId(correo, this.estadosService.buscarPorId(ControlGastosConstants.ESTADO_ACTIVO));
		return optional.isPresent() ? optional.get() : null;
	}
}
