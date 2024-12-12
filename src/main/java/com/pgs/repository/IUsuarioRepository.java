package com.pgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.model.UsuarioModel;

public interface IUsuarioRepository extends JpaRepository<UsuarioModel, Long>{

}
