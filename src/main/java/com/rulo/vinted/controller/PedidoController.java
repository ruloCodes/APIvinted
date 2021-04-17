//package com.rulo.vinted.controller;
//
//import com.rulo.vinted.domain.Detalle;
//import com.rulo.vinted.domain.Pedido;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//
//@RestController
//@Tag(name = "Pedidos", description = "Gestión de los pedidos de prendas")
//public class PedidoController {
//
//private final Logger logger = LoggerFactory.getLogger(PedidoController.class);
//
//    @GetMapping
//    public ResponseEntity<Pedido> getPedido() {
//        return new ResponseEntity<>(new Pedido(), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<Pedido> addPedido() {
//        Pedido pedido = Pedido.builder()
//                .cabecera("Cabecera del pedido")
//                .descuento(2)
//                .fechaCompra(LocalDate.now())
//                .lineas(new ArrayList<Detalle>())
//                .pagado(true)
//                .total(190.5f)
//                .build();
//        return new ResponseEntity<>(pedido, HttpStatus.OK);
//    }
//
//    @PutMapping
//    public ResponseEntity<Pedido> editPedido() {
//        Pedido pedido = Pedido.builder()
//                .cabecera("Cabecera del pedido")
//                .descuento(2)
//                .fechaCompra(LocalDate.now())
//                .lineas(new ArrayList<Detalle>())
//                .pagado(true)
//                .total(190.5f)
//                .build();
//        return new ResponseEntity<>(pedido, HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Pedido> deletePedido() {
//        Pedido pedido = Pedido.builder()
//                .cabecera("Cabecera del pedido")
//                .descuento(2)
//                .fechaCompra(LocalDate.now())
//                .lineas(new ArrayList<Detalle>())
//                .pagado(true)
//                .total(190.5f)
//                .build();
//        return new ResponseEntity<>(pedido, HttpStatus.OK);
//    }
//
//    @PatchMapping
//    public ResponseEntity<Pedido> changeDescuento() {
//        Pedido pedido = Pedido.builder()
//                .cabecera("Cabecera del pedido")
//                .descuento(2)
//                .fechaCompra(LocalDate.now())
//                .lineas(new ArrayList<Detalle>())
//                .pagado(true)
//                .total(190.5f)
//                .build();
//        return new ResponseEntity<>(pedido, HttpStatus.OK);
//    }
//
//}
