package com.rulo.vinted.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Usuario")
public class Usuario {

    @Schema(description = "Identificador del usuario", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre del usuario", example = "María", required = true)
    @NotBlank
    @Column
    private String nombre;

    @Schema(description = "Email del usuario", example = "example@example.com", required = true)
    @NotBlank
    @Column
    private String email;

    @Schema(description = "Password del usuario", example = "1234qwer", required = true)
    @NotBlank
    @Column
    private String password;

    @Schema(description = "Edad del usuario", example = "35")
    @Column
    private int edad;

    @Schema(description = "Altura del usuario", example = "1.75")
    @Column
    private float altura;

    @Schema(description = "Indica si el usuario es mayor de edad", example = "true")
    @Column
    private boolean mayorEdad;

    @Schema(description = "Fecha en la que se registra el usuario", example = "2021-04-01", required = true)
    @NotBlank
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAlta;

}
