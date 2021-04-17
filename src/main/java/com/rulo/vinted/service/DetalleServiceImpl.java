package com.rulo.vinted.service;

import com.rulo.vinted.domain.Detalle;
import com.rulo.vinted.repository.DetalleRepository;
import com.rulo.vinted.service.contract.DetalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DetalleServiceImpl implements DetalleService {

    @Autowired
    private DetalleRepository detalleRepository;

    @Override
    public Set<Detalle> findAll() {
        return detalleRepository.findAll();
    }

}
