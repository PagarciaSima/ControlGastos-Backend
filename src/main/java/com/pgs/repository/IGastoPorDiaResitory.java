package com.pgs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pgs.model.GastoPorDiaModel;

public interface IGastoPorDiaResitory extends JpaRepository<GastoPorDiaModel, Long>{
	
	@Query ("SELECT E FROM GastoPorDiaModel E WHERE MONTH(E.fecha) = :mes and YEAR(E.fecha) = :anio ORDER BY E.id desc")
	List<GastoPorDiaModel> findAllByMonth(@Param ("mes") Integer mes, @Param("anio") Integer anio);
}
