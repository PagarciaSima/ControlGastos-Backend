package com.pgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.model.EstadoModel;

public interface IEstadoRepository extends JpaRepository<EstadoModel, Long>{

}
