package com.rulo.vinted.repository;

import com.rulo.vinted.domain.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    Set<Usuario> findAll();

}