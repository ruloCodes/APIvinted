package com.rulo.vinted.service.contract;

import com.rulo.vinted.domain.Usuario;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface UsuarioService {

    Set<Usuario> findAll();
    Usuario findByEmailAndPassword(String email, String password);
    Map<String, Integer> findTopVentas();
    Set<Usuario> findByQuerys(boolean mayorEdad, boolean esVendedor, LocalDate fechaRegistro);
    Usuario addUsuario(Usuario usuario);
    Usuario editUsuario(String email, String password, Usuario usuario);
    Usuario changePass(long usuarioId, String oldPassword, String newPassword);
    void deleteUsuario(long usuarioId, String password);

}