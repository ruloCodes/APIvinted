package com.rulo.vinted.service;

import com.rulo.vinted.domain.Pedido;
import com.rulo.vinted.repository.PedidoRepository;
import com.rulo.vinted.service.contract.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Set<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

}
