package com.rulo.vinted.service.contract;

import com.rulo.vinted.domain.Prenda;

import java.util.List;
import java.util.Set;

public interface PrendaService {

    Set<Prenda> findAll();
    List<Prenda> findTopPuntos();
    Set<Prenda> findByCategoria(String categoria);
    Set<Prenda> findByQuery(String query);
    Set<Prenda> findByQuerys(String categoria, int talla, boolean nueva);
    Set<Prenda> findByVendedor(long usuarioId);
    Prenda addPrenda(long usuarioId, Prenda prenda);
    Prenda editPrenda(long prendaId, Prenda prenda);
    Prenda addPtsPrenda(long prendaId, int puntos);
    void deletePrenda(long prendaId);

}