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



}