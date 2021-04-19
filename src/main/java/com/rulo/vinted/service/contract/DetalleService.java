package com.rulo.vinted.service.contract;

import com.rulo.vinted.domain.Detalle;

import java.util.Set;

public interface DetalleService {

    Set<Detalle> findAll();
    Detalle findById(long id);
    Detalle addDetalle(Detalle detalle);
    Detalle updateDetalle(long id, Detalle detalle);
    Detalle changeRealizado(long id, boolean realizado);
    void deleteDetalle(long id);

}
