package com.rulo.vinted.controller;

import com.rulo.vinted.domain.Pedido;
import com.rulo.vinted.exception.PedidoNotFoundException;
import com.rulo.vinted.reponse.Response;
import com.rulo.vinted.service.contract.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static com.rulo.vinted.reponse.Response.NOT_FOUND;

@RestController
@Tag(name = "Pedidos", description = "Gestión de los pedidos de prendas")
public class PedidoController {

    private final Logger logger = LoggerFactory.getLogger(PedidoController.class);

    @Autowired
    PedidoService pedidoService;

    @Operation(summary = "Devuelve la lista completa de los pedidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos.", content = @Content(schema = @Schema(implementation = Pedido.class)))
    })
    @GetMapping(value = "/pedidos", produces = "application/json")
    public ResponseEntity<Set<Pedido>> getPedidos() {
        logger.info("inicio getPedidos");
        Set<Pedido> pedidos = pedidoService.findAll();
        logger.info("fin getPedidos");
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve un pedido identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido correcto.", content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "El pedido no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/pedidos/{id}", produces = "application/json")
    public ResponseEntity<Pedido> getPedido(@PathVariable long id) {
        logger.info("inicio getPedido");
        Pedido pedido = pedidoService.findById(id);
        logger.info("fin getPedido");
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo pedido.")
    @ApiResponses(value =
            @ApiResponse(responseCode = "201", description = "Pedido registrado con éxito.", content = @Content(schema = @Schema(implementation = Pedido.class))))
    @PostMapping(value = "/pedidos", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Pedido> registrarPedido(@RequestBody Pedido nuevoPedido) {
        logger.info("inicio registrarPedido");
        Pedido pedido = pedidoService.addPedido(nuevoPedido);
        logger.info("fin registrarPedido");
        return new ResponseEntity<>(pedido, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un pedido registrado, identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido modificado con éxito", content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "El pedido no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/pedidos/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Pedido> modificarPedido(@PathVariable long id, @RequestBody Pedido pedidoEditado) {
        logger.info("inicio modificarPedido");
        Pedido pedido = pedidoService.updatePedido(id, pedidoEditado);
        logger.info("fin modificarPedido");
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @Operation(summary = "Cambia el estado pagado de un pedido.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cambio realizado", content = @Content(schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "El pedido no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/pedidos/{id}/{pagado}", produces = "application/json")
    public ResponseEntity<Pedido> editaPagado(@PathVariable long id, @PathVariable boolean pagado) {
        logger.info("inicio editaPagado");
        Pedido pedido = pedidoService.changePagado(id, pagado);
        logger.info("fin editaPagado");
        return new ResponseEntity<>(pedido, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un pedido identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido eliminado con éxito", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El pedido no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/pedidos/{id}", produces = "application/json")
    public ResponseEntity<Response> eliminaPedido(@PathVariable long id) {
        logger.info("inicio eliminaPedido");
        pedidoService.deletePedido(id);
        logger.info("fin eliminaPedido");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(PedidoNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PedidoNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        logger.error(pnfe.getMessage(), pnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}