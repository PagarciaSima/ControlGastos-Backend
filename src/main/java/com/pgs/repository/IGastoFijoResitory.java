package com.pgs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pgs.model.GastoFijoModel;

public interface IGastoFijoResitory extends JpaRepository<GastoFijoModel, Long>{

	@Query(
			name = "GastoFijoModel.findAllByMonth",
			value = "SELECT E FROM GastoFijoModel E WHERE MONTH(E.fecha)= :mes and YEAR(E.fecha)= :anio order by E.id desc"
	)
	List<GastoFijoModel> findAllByMonth(@Param("mes") Integer mes, @Param("anio") Integer anio);
}
