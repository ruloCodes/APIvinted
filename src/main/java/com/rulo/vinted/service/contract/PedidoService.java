package com.rulo.vinted.service.contract;

import com.rulo.vinted.domain.Pedido;

import java.util.Set;

public interface PedidoService {

    Set<Pedido> findAll();

}
