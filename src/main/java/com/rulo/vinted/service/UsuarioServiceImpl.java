package com.rulo.vinted.service;

import com.rulo.vinted.domain.Detalle;
import com.rulo.vinted.domain.Prenda;
import com.rulo.vinted.domain.Usuario;
import com.rulo.vinted.exception.UnauthorizedException;
import com.rulo.vinted.exception.UserDuplicateException;
import com.rulo.vinted.exception.UserMissingDataException;
import com.rulo.vinted.exception.UserNotFoundException;
import com.rulo.vinted.repository.DetalleRepository;
import com.rulo.vinted.repository.UsuarioRepository;
import com.rulo.vinted.service.contract.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DetalleRepository detalleRepository;

    /**
     * Devuelve todos los usuarios
     * */
    @Override
    public Set<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    /**
     * Devuelve un usuario si corresponde con el email y el password
     * */
    @Override
    public Usuario findByEmailAndPassword(String email, String password) {
        return usuarioRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new UserNotFoundException(email + " " + password));
    }

    /**
     * Devuelve una lista de pares (usuario: nº ventas) con los
     * 10 usuarios con más ventas
     * */
    @Override
    public Map<String, Integer> findTopVentas() {
        Map<String, Integer> listaTop = new HashMap<>();

        for (Usuario u : getListaVendedores()) listaTop.merge(u.getNombre(), 1, Integer::sum);

        return listaTop.entrySet()
            .stream()
            .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
            .limit(10L)
            .collect(Collectors
                    .toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Devuelve los usuarios que cumplan con los requisitos de
     * mayoría de edad, prendas a la venta y fecha de registro
     * anterior a la indicada
     * */
    @Override
    public Set<Usuario> findByQuerys(boolean mayorEdad, boolean esVendedor, LocalDate fechaRegistro) {
        return usuarioRepository.findAll()
                .stream()
                .filter(usuario ->
                        usuario.isMayorEdad() == mayorEdad &&
                                getListaVendedores().contains(usuario) == esVendedor &&
                                usuario.getFechaAlta().isBefore(fechaRegistro))
                .collect(Collectors.toSet());
    }

    /**
     * Registra y devuelve un nuevo usuario
     * */
    @Override
    public Usuario addUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new UserDuplicateException(usuario.getEmail());

        if (usuario.getNombre().isEmpty() || usuario.getEmail().isEmpty() || usuario.getPassword().isEmpty())
            throw new UserMissingDataException();

        usuario.setFechaAlta(LocalDate.now());
        usuario.setMayorEdad(usuario.getEdad() >= 18);
        return usuarioRepository.save(usuario);
    }

    /**
     * Edita y devuelve un usuario identificado por su email y su password
     * */
    @Override
    public Usuario editUsuario(String email, String pasword, Usuario usuario) {
        Usuario usuarioViejo = usuarioRepository.findByEmailAndPassword(email, pasword)
                .orElseThrow(() -> new UserNotFoundException("Incorrect user data"));

        if (usuario.getNombre().isEmpty() || usuario.getEmail().isEmpty() || usuario.getPassword().isEmpty())
            throw new UserMissingDataException();

        usuario.setFechaAlta(usuarioViejo.getFechaAlta());
        usuarioRepository.delete(usuarioViejo);

        return usuarioRepository.save(usuario);
    }

    /**
     * Realiza un cambio de password a un usuario identificado por su id
     * y su password
     * */
    @Override
    public Usuario changePass(long usuarioId, String oldPassword, String newPassword) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UserNotFoundException(usuarioId));

        if (!usuario.getPassword().equals(oldPassword))
            throw new UnauthorizedException();

        usuario.setPassword(newPassword);

        return usuarioRepository.save(usuario);
    }

    /**
     * Elimina un usuario identificado por su id y su password
     * */
    @Override
    public void deleteUsuario(long usuarioId, String password) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UserNotFoundException(usuarioId));

        if (!usuario.getPassword().equals(password))
            throw new UnauthorizedException();

        usuarioRepository.delete(usuario);
    }

    private List<Usuario> getListaVendedores() {
        return detalleRepository.findAll()
                .stream().filter(Detalle::isRealizado)
                .map(Detalle::getPrenda)
                .map(Prenda::getVendedor)
                .collect(Collectors.toList());
    }
}
