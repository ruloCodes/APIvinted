package com.rulo.vinted.service;

import com.rulo.vinted.domain.Pedido;
import com.rulo.vinted.exception.PedidoNotFoundException;
import com.rulo.vinted.repository.PedidoRepository;
import com.rulo.vinted.service.contract.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    /**
     * Devuelve la lista completa de pedidos
     * */
    @Override
    public Set<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    /**
     * Devuelve un pedido identificado por su id
     * */
    @Override
    public Pedido findById(long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
    }

    /**
     * Registra un nuevo pedido
     * */
    @Override
    public Pedido addPedido(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    /**
     * Edita un pedido identificado por su id
     * */
    @Override
    public Pedido updatePedido(long id, Pedido pedido) {
        Pedido pedidoViejo = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        pedidoRepository.delete(pedidoViejo);
        return pedidoRepository.save(pedido);
    }

    /**
     * Cambia en un pedido, identificado por su id, el estado del atributo pagado
     * */
    @Override
    public Pedido changePagado(long id, boolean pagado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        pedido.setPagado(pagado);
        return pedidoRepository.save(pedido);
    }

    /**
     * Elimina un pedido identificado por su id
     * */
    @Override
    public void deletePedido(long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        pedidoRepository.delete(pedido);
    }

}
