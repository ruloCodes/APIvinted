package com.rulo.vinted.controller;

import com.rulo.vinted.domain.Detalle;
import com.rulo.vinted.exception.DetalleNotFoundException;
import com.rulo.vinted.reponse.Response;
import com.rulo.vinted.service.contract.DetalleService;
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
@Tag(name = "Detalles", description = "Detalles de pedidos")
public class DetalleController {

    private final Logger logger = LoggerFactory.getLogger(DetalleController.class);

    @Autowired
    private DetalleService detalleService;

    @Operation(summary = "Devuelve la lista completa de los detalles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de detalles.", content = @Content(schema = @Schema(implementation = Detalle.class)))
    })
    @GetMapping(value = "/detalles", produces = "application/json")
    public ResponseEntity<Set<Detalle>> getDetalles() {
        logger.info("inicio getDetalles");
        Set<Detalle> detalles = detalleService.findAll();
        logger.info("fin getDetalles");
        return new ResponseEntity<>(detalles, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve un detalle identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle correcto.", content = @Content(schema = @Schema(implementation = Detalle.class))),
            @ApiResponse(responseCode = "404", description = "El detalle no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/detalles/{id}", produces = "application/json")
    public ResponseEntity<Detalle> getDetalle(@PathVariable long id) {
        logger.info("inicio getDetalle");
        Detalle detalle = detalleService.findById(id);
        logger.info("fin getDetalle");
        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo detalle.")
    @ApiResponses(value =
    @ApiResponse(responseCode = "201", description = "Detalle registrado con éxito.", content = @Content(schema = @Schema(implementation = Detalle.class))))
    @PostMapping(value = "/detalles", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Detalle> registrarDetalle(@RequestBody Detalle nuevoDetalle) {
        logger.info("inicio registrarDetalle");
        Detalle detalle = detalleService.addDetalle(nuevoDetalle);
        logger.info("fin registrarDetalle");
        return new ResponseEntity<>(detalle, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica un detalle registrado, identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle modificado con éxito", content = @Content(schema = @Schema(implementation = Detalle.class))),
            @ApiResponse(responseCode = "404", description = "El detalle no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/detalles/{id}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Detalle> modificarDetalle(@PathVariable long id, @RequestBody Detalle detalleEditado) {
        logger.info("inicio modificarDetalle");
        Detalle detalle = detalleService.updateDetalle(id, detalleEditado);
        logger.info("fin modificarDetalle");
        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    @Operation(summary = "Cambia el estado realizado de un detalle.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cambio realizado", content = @Content(schema = @Schema(implementation = Detalle.class))),
            @ApiResponse(responseCode = "404", description = "El detalle no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/detalles/{id}/{realizado}", produces = "application/json")
    public ResponseEntity<Detalle> editaRealizado(@PathVariable long id, @PathVariable boolean realizado) {
        logger.info("inicio editaRealizado");
        Detalle detalle = detalleService.changeRealizado(id, realizado);
        logger.info("fin editaRealizado");
        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un detalle identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle eliminado con éxito", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El detalle no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/detalles/{id}", produces = "application/json")
    public ResponseEntity<Response> eliminaDetalle(@PathVariable long id) {
        logger.info("inicio eliminaDetalle");
        detalleService.deleteDetalle(id);
        logger.info("fin eliminaDetalle");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(DetalleNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(DetalleNotFoundException dnfe) {
        Response response = Response.errorResponse(NOT_FOUND, dnfe.getMessage());
        logger.error(dnfe.getMessage(), dnfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
