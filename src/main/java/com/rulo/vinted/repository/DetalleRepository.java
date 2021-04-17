package com.rulo.vinted.repository;

import com.rulo.vinted.domain.Detalle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DetalleRepository extends CrudRepository<Detalle, Long> {

    Set<Detalle> findAll();

}
