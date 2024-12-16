package com.pgs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.model.EstadoModel;
import com.pgs.model.UsuarioModel;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long>{

	UsuarioModel findByCorreo(String correo);
	Optional<UsuarioModel> findByCorreoAndEstadosId(String correo, EstadoModel estado);
}
