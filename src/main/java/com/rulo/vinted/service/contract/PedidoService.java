package com.rulo.vinted.service.contract;

import com.rulo.vinted.domain.Pedido;

import java.util.Set;

public interface PedidoService {

    Set<Pedido> findAll();
    Pedido findById(long id);
    Pedido addPedido(Pedido pedido);
    Pedido updatePedido(long id, Pedido pedido);
    Pedido changePagado(long id, boolean pagado);
    void deletePedido(long id);

}
