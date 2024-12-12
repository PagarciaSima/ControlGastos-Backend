package com.pgs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.model.EstadoModel;

public interface IEstadoRepository extends JpaRepository<EstadoModel, Long>{

	List<EstadoModel> findByIdIn(List<Long> id);
}
