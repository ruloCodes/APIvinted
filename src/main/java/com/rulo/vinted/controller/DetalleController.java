//package com.rulo.vinted.controller;
//
//import com.rulo.vinted.domain.Detalle;
//import com.rulo.vinted.domain.Pedido;
//import com.rulo.vinted.domain.Prenda;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//
//@RestController
//@Tag(name = "Detalles", description = "Detalles de pedidos")
//public class DetalleController {
//
//private final Logger logger = LoggerFactory.getLogger(DetalleController.class);
//
//    @GetMapping
//    public ResponseEntity<Detalle> getDetalle() {
//        return new ResponseEntity<>(new Detalle(), HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<Detalle> addDetalle() {
//        Detalle detalle = Detalle.builder()
//                .detalle("Detalle de la línea")
//                .subtotal(17.95f)
//                .cantidad(3)
//                .fechaPedido(LocalDate.now())
//                .pedido(new Pedido())
//                .prenda(new Prenda())
//                .realizado(false)
//                .build();
//        return new ResponseEntity<>(detalle, HttpStatus.OK);
//    }
//
//    @PutMapping
//    public ResponseEntity<Detalle> editDetalle() {
//        Detalle detalle = Detalle.builder()
//                .detalle("Detalle de la línea")
//                .subtotal(17.95f)
//                .cantidad(3)
//                .fechaPedido(LocalDate.now())
//                .pedido(new Pedido())
//                .prenda(new Prenda())
//                .realizado(false)
//                .build();
//        return new ResponseEntity<>(detalle, HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<Detalle> deleteDetalle() {
//        Detalle detalle = Detalle.builder()
//                .detalle("Detalle de la línea")
//                .subtotal(17.95f)
//                .cantidad(3)
//                .fechaPedido(LocalDate.now())
//                .pedido(new Pedido())
//                .prenda(new Prenda())
//                .realizado(false)
//                .build();
//        return new ResponseEntity<>(detalle, HttpStatus.OK);
//    }
//
//    @PatchMapping
//    public ResponseEntity<Detalle> changeUnits() {
//        Detalle detalle = Detalle.builder()
//                .detalle("Detalle de la línea")
//                .subtotal(17.95f)
//                .cantidad(3)
//                .fechaPedido(LocalDate.now())
//                .pedido(new Pedido())
//                .prenda(new Prenda())
//                .realizado(false)
//                .build();
//        return new ResponseEntity<>(detalle, HttpStatus.OK);
//    }
//
//}
