package com.rulo.vinted.service;

import com.rulo.vinted.domain.Usuario;
import com.rulo.vinted.repository.UsuarioRepository;
import com.rulo.vinted.service.contract.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public Set<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Set<Usuario> findTopVentas() {
        /*
        * Mostrar los 10 vendedores con más ventas
        * */
        return null;
    }

}
