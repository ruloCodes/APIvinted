package com.rulo.vinted.service;

import com.rulo.vinted.domain.Detalle;
import com.rulo.vinted.exception.DetalleNotFoundException;
import com.rulo.vinted.repository.DetalleRepository;
import com.rulo.vinted.service.contract.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DetalleServiceImpl implements DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    /**
     * Devuelve la lista completa de detalles
     * */
    @Override
    public Set<Detalle> findAll() {
        return detalleRepository.findAll();
    }

    /**
     * Devuelve un detalle identificado por su id
     * */
    @Override
    public Detalle findById(long id) {
        return detalleRepository.findById(id)
                .orElseThrow(() -> new DetalleNotFoundException(id));
    }

    /**
     * Registra un nuevo detalle
     * */
    @Override
    public Detalle addDetalle(Detalle detalle) {
        return detalleRepository.save(detalle);
    }

    /**
     * Edita un detalle identificado por su id
     * */
    @Override
    public Detalle updateDetalle(long id, Detalle detalle) {
        Detalle detalleViejo = detalleRepository.findById(id)
                .orElseThrow(() -> new DetalleNotFoundException(id));
        detalleRepository.delete(detalleViejo);
        return detalleRepository.save(detalle);
    }

    /**
     * Cambia en un detalle, identificado por su id, el estado del atributo realizado
     * */
    @Override
    public Detalle changeRealizado(long id, boolean realizado) {
        Detalle detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new DetalleNotFoundException(id));

        detalle.setRealizado(realizado);
        return detalleRepository.save(detalle);
    }

    /**
     * Elimina un detalle identificado por su id
     * */
    @Override
    public void deleteDetalle(long id) {
        Detalle detalle = detalleRepository.findById(id)
                .orElseThrow(() -> new DetalleNotFoundException(id));

        detalleRepository.delete(detalle);
    }

}
