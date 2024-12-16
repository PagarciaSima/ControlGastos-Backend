package com.pgs.service;

import java.util.List;

import com.pgs.model.UsuarioModel;

public interface IUsuarioService {

	List<UsuarioModel> listar();

	void guardar(UsuarioModel modelo);

	UsuarioModel buscarPorId(Long id);

	void eliminar(Long id);

	UsuarioModel buscarPorCorreo(String correo);

	UsuarioModel buscarPorCorreoActivo(String correo);

}