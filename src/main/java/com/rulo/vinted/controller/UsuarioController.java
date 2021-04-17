package com.rulo.vinted.controller;

import com.rulo.vinted.domain.Usuario;
import com.rulo.vinted.exception.UserNotFoundException;
import com.rulo.vinted.reponse.Response;
import com.rulo.vinted.service.contract.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.rulo.vinted.reponse.Response.NOT_FOUND;

import java.time.LocalDate;
import java.util.Set;

@RestController
@Tag(name = "Usuarios", description = "Gestión de los usuarios")
public class UsuarioController {

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    UsuarioService usuarioService;

    @GetMapping(value = "/usuarios", produces = "application/json")
    public ResponseEntity<Set<Usuario>> getUsuario() {
        Set<Usuario> usuarios = usuarioService.findAll();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Usuario> addUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("Nombre usuario")
                .altura(1.78f)
                .edad(32)
                .email("usuario@spring.com")
                .fechaAlta(LocalDate.now())
                .mayorEdad(true)
                .password("12345")
                .build();
        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @PutMapping(value = "/usuarios")
    public ResponseEntity<Usuario> editUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("Nombre usuario")
                .altura(1.78f)
                .edad(32)
                .email("usuario@spring.com")
                .fechaAlta(LocalDate.now())
                .mayorEdad(true)
                .password("12345")
                .build();
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @DeleteMapping(value = "/usuarios")
    public ResponseEntity<Usuario> deleteUsuario() {
        Usuario usuario = Usuario.builder()
                .nombre("Nombre usuario")
                .altura(1.78f)
                .edad(32)
                .email("usuario@spring.com")
                .fechaAlta(LocalDate.now())
                .mayorEdad(true)
                .password("12345")
                .build();
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @PatchMapping(value = "/usuarios")
    public ResponseEntity<Usuario> changeUserMail() {
        Usuario usuario = Usuario.builder()
                .nombre("Nombre usuario")
                .altura(1.78f)
                .edad(32)
                .email("usuario@spring.com")
                .fechaAlta(LocalDate.now())
                .mayorEdad(true)
                .password("12345")
                .build();
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Response> handleException(UserNotFoundException unfe) {
        Response response = Response.errorResponse(NOT_FOUND, unfe.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}