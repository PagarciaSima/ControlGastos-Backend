package com.pgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pgs.model.ProveedorModel;

public interface IProveedorRepository extends JpaRepository<ProveedorModel, Long>{

}
