package com.rulo.vinted.repository;

import com.rulo.vinted.domain.Pedido;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    Set<Pedido> findAll();

}
