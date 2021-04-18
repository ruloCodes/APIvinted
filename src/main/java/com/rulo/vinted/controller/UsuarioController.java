package com.rulo.vinted.controller;

import com.rulo.vinted.domain.Usuario;
import com.rulo.vinted.exception.UnauthorizedException;
import com.rulo.vinted.exception.UserDuplicateException;
import com.rulo.vinted.exception.UserMissingDataException;
import com.rulo.vinted.exception.UserNotFoundException;
import com.rulo.vinted.reponse.Response;
import com.rulo.vinted.service.contract.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static com.rulo.vinted.reponse.Response.*;

@RestController
@Tag(name = "Usuarios", description = "Gestión de los usuarios")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    UsuarioService usuarioService;

    @Operation(summary = "Devuelve las lista completa de los usuarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios.", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping(value = "/usuarios", produces = "application/json")
    public ResponseEntity<Set<Usuario>> getUsuarios() {
        logger.info("inicio getUsuarios");
        Set<Usuario> usuarios = usuarioService.findAll();
        logger.info("fin getUsuarios");
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve un usuario identificado por su email y su password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario correcto.", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping(value = "/usuarios/{email}/{password}", produces = "application/json")
    public ResponseEntity<Usuario> getLoginUsuario(@PathVariable String email, @PathVariable String password) {
        logger.info("inicio getLoginUsuario");
        Usuario usuario = usuarioService.findByEmailAndPassword(email, password);
        logger.info("fin getLoginUsuario");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve una lista de pares (usuario: nºVentas) con los 10 usuarios con mejores ventas.")
    @ApiResponses(value =
    @ApiResponse(responseCode = "200", description = "Lista top vendedores.", content = @Content(schema = @Schema(implementation = Usuario.class)))
    )
    @GetMapping(value = "/usuarios/top", produces = "application/json")
    public ResponseEntity<Map<String, Integer>> findTopVendedores() {
        logger.info("inicio findTopVendedores");
        Map<String, Integer> topVendedores = usuarioService.findTopVentas();
        logger.info("fin findTopVendedores");
        return new ResponseEntity<>(topVendedores, HttpStatus.OK);
    }

    @Operation(summary = "Devuelve una lista con los vendedores que se ajustan a los 3 parámetros.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada usuarios", content = @Content(schema = @Schema(implementation = Usuario.class)))
    })
    @GetMapping(value = "/usuarios/filtros", produces = "application/json")
    public ResponseEntity<Set<Usuario>> findByQuerys(@RequestParam(name = "mayorEdad", defaultValue = "true") boolean mayorEdad,
                                                    @RequestParam(name = "esVendedor", defaultValue = "true") boolean esVendedor,
                                                    @RequestParam(name = "fechaRegistro") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaRegistro ) {
        logger.info("inicio findByQuerys");
        Set<Usuario> usuarios = usuarioService.findByQuerys(mayorEdad, esVendedor, fechaRegistro);
        logger.info("fin findByQuerys");
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @Operation(summary = "Registra un nuevo usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario registrado con éxito", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "406", description = "Ya existe un usuario con ese email", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "No se han completado los campos obligatorios", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping(value = "/usuarios", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Usuario> registrarUsuario(@RequestBody Usuario nuevoUsuario) {
        logger.info("inicio registrarUsuario");
        Usuario usuario = usuarioService.addUsuario(nuevoUsuario);
        logger.info("fin registrarUsuario");
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @Operation(summary = "Modifica los atributos de un usuario identificado por su email y password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario modificado con éxito", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "La identificación es incorrecta", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "400", description = "No se han completado los campos obligatorios", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping(value = "/usuarios/{email}/{password}", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Usuario> modificarUsuario(@PathVariable String email, @PathVariable String password, @RequestBody Usuario usuarioEditado) {
        logger.info("inicio modificarUsuario");
        Usuario usuario = usuarioService.editUsuario(email, password, usuarioEditado);
        logger.info("fin modificarUsuario");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Realiza un cambio de password a un usuario identificado por su id y su password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password cambiada", content = @Content(schema = @Schema(implementation = Usuario.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "La identificación es incorrecta", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PatchMapping(value = "/usuarios/{id}/{oldPassword}/{newPassword}", produces = "application/json")
    public ResponseEntity<Usuario> editaPassword(@PathVariable long id, @PathVariable String oldPassword, @PathVariable String newPassword) {
        logger.info("inicio editaPassword");
        Usuario usuario = usuarioService.changePass(id, oldPassword, newPassword);
        logger.info("fin editaPassword");
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @Operation(summary = "Elimina un usuario identificado por su id y su password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimina el usuario con éxito", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "El usuario no existe", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "403", description = "La identificación es incorrecta", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping(value = "/usarios/{id}/{password}", produces = "application/json")
    public ResponseEntity<Response> eliminaUsuario(@PathVariable long id, @PathVariable String password) {
        logger.info("inicio eliminaUsuario");
        usuarioService.deleteUsuario(id, password);
        logger.info("fin eliminaUsuario");
        return new ResponseEntity<>(Response.noErrorResponse(), HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(NOT_FOUND, unfe.getMessage());
        logger.error(unfe.getMessage(), unfe);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDuplicateException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseEntity<Response> handleException(UserDuplicateException ude) {
        Response response = Response.errorResponse(NOT_ACCEPTABLE, ude.getMessage());
        logger.error(ude.getMessage(), ude);
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(UserMissingDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Response> handleException(UserMissingDataException umde) {
        Response response = Response.errorResponse(BAD_REQUEST, umde.getMessage());
        logger.error(umde.getMessage(), umde);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<Response> handleException(UnauthorizedException ue) {
        Response response = Response.errorResponse(FORBIDDEN, ue.getMessage());
        logger.error(ue.getMessage(), ue);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}