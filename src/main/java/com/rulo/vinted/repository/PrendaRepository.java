package com.rulo.vinted.repository;

import com.rulo.vinted.domain.Prenda;
import com.rulo.vinted.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PrendaRepository extends CrudRepository<Prenda, Long> {

    Set<Prenda> findAll();
    Set<Prenda> findByCategoria(String categoria);
    Set<Prenda> findByVendedor(Usuario vendedor);

}
