package com.rulo.vinted.service;

import com.rulo.vinted.domain.Prenda;
import com.rulo.vinted.exception.PrendaNotFoundException;
import com.rulo.vinted.exception.UserNotFoundException;
import com.rulo.vinted.repository.PrendaRepository;
import com.rulo.vinted.repository.UsuarioRepository;
import com.rulo.vinted.service.contract.PrendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PrendaServiceImpl implements PrendaService {

    @Autowired
    private PrendaRepository prendaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Set<Prenda> findAll() {
        return prendaRepository.findAll();
    }

    /**
     * Devuelve las 10 prendas con mejor puntuación
     * */
    @Override
    public List<Prenda> findTopPuntos() {
        return prendaRepository.findAll()
                .stream()
                .sorted(Comparator.comparingInt(Prenda::getPuntos).reversed())
                .limit(10)
                .collect(Collectors.toList());
    }

    /**
     * Devuelve las prendas cuya categoría coincida con la aportada.
     * Si no se indica categoría se devuelve la lista completa.
     * */
    @Override
    public Set<Prenda> findByCategoria(String categoria) {
        if (categoria.equals(""))
            return prendaRepository.findAll();

        return prendaRepository.findByCategoria(categoria);
    }

    /**
     * Devuelve las prendas en cuya categoría, nombre o descripción, exista
     * alguna coincidencia con la query.
     * Si no se indica la query se devuelve la lista completa.
     * */
    @Override
    public Set<Prenda> findByQuery(String query) {
        return prendaRepository.findAll()
                .stream()
                .filter(prenda ->
                        prenda.getCategoria()
                            .toLowerCase()
                            .contains(query) ||
                        prenda.getNombre()
                            .toLowerCase()
                            .contains(query) ||
                        prenda.getDescripcion()
                            .toLowerCase()
                            .contains(query))
                .collect(Collectors.toSet());
    }

    /**
     * Devuelve una lista de prendas filtrada por los atributos
     * categoría, talla y nueva
     * */
    @Override
    public Set<Prenda> findByQuerys(String categoria, int talla, boolean nueva) {
        return prendaRepository.findAll()
                .stream()
                .filter(prenda ->
                        prenda.getCategoria()
                                .equalsIgnoreCase(categoria) &&
                                prenda.getTalla() == talla  &&
                                prenda.isNueva() == nueva)
                .collect(Collectors.toSet());
    }

    /**
     * Devuelve las prendas de un usuario concreto identificado por su id
     * */
    @Override
    public Set<Prenda> findByVendedor(long usuarioId) {
        return prendaRepository.findByVendedor(usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new UserNotFoundException(usuarioId)));
    }

    /**
     * Registra una nueva prenda asociada a un usuario identificado por su id
     * */
    @Override
    public Prenda addPrenda(long usuarioId, Prenda prenda) {
        prenda.setPuntos(0);
        prenda.setFechaSubida(LocalDate.now());
        prenda.setVendedor(usuarioRepository
                .findById(usuarioId)
                .orElseThrow(() -> new UserNotFoundException(usuarioId))
        );

        return prendaRepository.save(prenda);
    }

    /**
     * Edita los datos de una prenda (se sustituye la registrada por la editada)
     * Los datos del vendedor, fecha de subida y puntos no se modifican.
     * */
    @Override
    public Prenda editPrenda(long prendaId, Prenda prenda) {
        Prenda prendaVieja = prendaRepository.findById(prendaId)
                .orElseThrow(() -> new PrendaNotFoundException(prendaId));

        prenda.setVendedor(prendaVieja.getVendedor());
        prenda.setFechaSubida(prendaVieja.getFechaSubida());
        prenda.setPuntos(prendaVieja.getPuntos());
        prendaRepository.delete(prendaVieja);
        return prendaRepository.save(prenda);
    }

    /**
     * Elimina una prenda identificada por su id.
     * */
    @Override
    public void deletePrenda(long prendaId) {
        prendaRepository.findById(prendaId)
                .orElseThrow(() -> new PrendaNotFoundException(prendaId));
        prendaRepository.deleteById(prendaId);
    }

    /**
     * Añade puntos a una prenda identificada por su id
     * */
    @Override
    public Prenda addPtsPrenda(long prendaId, int pts) {
        Prenda prenda = prendaRepository.findById(prendaId)
                .orElseThrow(() -> new PrendaNotFoundException(prendaId));
        int puntosActuales = prenda.getPuntos();
        prenda.setPuntos(puntosActuales + pts);
        return prendaRepository.save(prenda);
    }

}