package com.rulo.vinted.service.contract;

import com.rulo.vinted.domain.Usuario;

import java.util.Set;

public interface UsuarioService {

    Set<Usuario> findAll();
    Set<Usuario> findTopVentas();

}
