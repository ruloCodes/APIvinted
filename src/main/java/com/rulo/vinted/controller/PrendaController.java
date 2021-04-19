package com.rulo.vinted.controller;

import com.rulo.vinted.domain.Prenda;
import com.rulo.vinted.exception.PrendaNotFoundException;
import com.rulo.vinted.exception.UserNotFoundException;
import com.rulo.vinted.reponse.Response;
import com.rulo.vinted.service.contract.PrendaService;
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
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Set;

import static com.rulo.vinted.reponse.Response.NOT_FOUND;

@RestController
@Tag(name = "Prendas", description = "Gestión de las prendas")
public class PrendaController {

    private final Logger logger = LoggerFactory.getLogger(PrendaController.class);

    @Autowired
    private PrendaService prendaService;

    @Operation(summary = "Devuelve una lista con las 10 prendas mejor puntuadas.")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200", description = "Lista top prendas.", content = @Content(schema = @Schema(implementation = Prenda.class)))
    )
    @GetMapping(value = "/prendas/top", produces = "application/json")
    public ResponseEntity<List<Prenda>> findTop() {
        logger.info("inicio findTop");
        List<Prenda> topPrendas = prendaService.findTopPuntos();
        logger.info("fin findTop");
        return new ResponseEntity<>(topPrendas, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve las prendas cuya categoría coincida con la aportada.\n" +
            "Si no se indica categoría se devuelve la lista completa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prendas filtrada por categoría.", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "200", description = "Lista completa de prendas.", content = @Content(schema = @Schema(implementation = Prenda.class)))
    })
    @GetMapping(value = "/prendas", produces = "application/json")
    public ResponseEntity<Set<Prenda>> getPrendas(@RequestParam(value = "categoria", defaultValue = "") String categoria) {
        logger.info("inicio getPrendas");
        Set<Prenda> prendas = prendaService.findByCategoria(categoria);
        logger.info("fin getPrendas");
        return new ResponseEntity<>(prendas, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve las prendas en cuya categoría, nombre o descripción, exista alguna coincidencia con la query.\n" +
            "Si no se indica query se devuelve la lista completa.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada.", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "200", description = "Lista completa.", content = @Content(schema = @Schema(implementation = Prenda.class)))
    })
    @GetMapping(value = "/prendas/filtro", produces = "application/json")
    public ResponseEntity<Set<Prenda>> findByQuery(@RequestParam(value = "query", defaultValue = "") String query) {
        logger.info("inicio findByQuery");
        Set<Prenda> prendas = prendaService.findByQuery(query);
        logger.info("fin findByQuery");
        return new ResponseEntity<>(prendas, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve las prendas cuya categoría, talla y estado, coincidan con los filtros.\n" +
            "Si no se indica alguno de los filtros se devuelve una lista vacía.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada de prendas", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "200", description = "Lista vacía", content = @Content(schema = @Schema(implementation = Prenda.class)))
    })
    @GetMapping(value = "/prendas/filtros", produces = "application/json")
    public ResponseEntity<Set<Prenda>> findByQuerys(@RequestParam(name = "categoria", defaultValue = "") String categoria,
                                                    @RequestParam(name = "talla", defaultValue = "1") int talla,
                                                    @RequestParam(name = "nueva", defaultValue = "true") boolean nueva) {
        logger.info("inicio findByQuerys");
        Set<Prenda> prendas = prendaService.findByQuerys(categoria, talla, nueva);
        logger.info("fin findByQuerys");
        return new ResponseEntity<>(prendas, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve las prendas de un usuario concreto identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prendas del usuario", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/prendas/usuario", produces = "application/json")
    public ResponseEntity<Set<Prenda>> findByUsuario(@RequestParam(value = "usuarioId") long usuarioId) {
        logger.info("inicio findByUsuario");
        Set<Prenda> prendas = prendaService.findByVendedor(usuarioId);
        logger.info("fin findByUsuario");
        return new ResponseEntity<>(prendas, HttpStatus.OK);
    }

    @Operation(summary = "Registra una nueva prenda asociada a un usuario identificado por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prenda registrada con éxito", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/prendas/{usuarioId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Prenda> subirPrenda(@PathVariable long usuarioId, @RequestBody Prenda nuevaPrenda) {
        logger.info("inicio subirPrenda");
        Prenda prenda = prendaService.addPrenda(usuarioId, nuevaPrenda);
        logger.info("fin subirPrenda");
        return new ResponseEntity<>(prenda, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica los atributos de una prenda identificada por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prenda modificada con éxito", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "404", description = "La prenda no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/prendas/{prendaId}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Prenda> modificarPrenda(@PathVariable long prendaId, @RequestBody Prenda prendaEditada) {
        logger.info("inicio modificarPrenda");
        Prenda prenda = prendaService.editPrenda(prendaId, prendaEditada);
        logger.info("fin modificarPrenda");
        return new ResponseEntity<>(prenda, HttpStatus.OK);
    }

    @Operation(summary = "Elimina una prenda identificada por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina la prenda con éxito", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "No existe la prenda con ese id", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/prendas/{prendaId}", produces = "application/json")
    public ResponseEntity<Response> eliminaPrenda(@PathVariable long prendaId) {
        logger.info("inicio eliminaPrenda");
        prendaService.deletePrenda(prendaId);
        logger.info("fin eliminaPrenda");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @Operation(summary = "Añade puntos a una prenda identificada por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Puntos añadidos", content = @Content(schema = @Schema(implementation = Prenda.class))),
            @ApiResponse(responseCode = "404", description = "La prenda no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/prendas/{prendaId}/addPts/{pts}", produces = "application/json")
    public ResponseEntity<Prenda> calificaPrenda(@PathVariable long prendaId, @PathVariable int pts) {
        logger.info("inicio calificaPrenda");
        Prenda prenda = prendaService.addPtsPrenda(prendaId, pts);
        logger.info("fin calificaPrenda");
        return new ResponseEntity<>(prenda, HttpStatus.OK);
    }

    /**
     * Control de error 404 usuarios
     */
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(NOT_FOUND, unfe.getMessage());
        logger.error("error 404 usuario: " + unfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Control de error 404 prendas
     */
    @ExceptionHandler(PrendaNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(PrendaNotFoundException pnfe) {
        Response response = Response.errorResponse(NOT_FOUND, pnfe.getMessage());
        logger.error("error 404 prenda: " + pnfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Control de error 400
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleException(MethodArgumentTypeMismatchException matme, MissingServletRequestParameterException msrpe) {
        Response response = Response.errorResponse(400, "Revise los parámetros de la URL");
        logger.error("error 400 bad URL");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}