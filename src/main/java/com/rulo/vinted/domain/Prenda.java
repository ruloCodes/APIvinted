package com.rulo.vinted.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Prenda")
public class Prenda {

    @Schema(description = "Identificador de la prenda", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Nombre de la prenda", example = "Vaqueros azules", required = true)
    @NotBlank
    @Column
    private String nombre;

    @Schema(description = "Descripción de la prenda", example = "Vaqueros largos de la marca Lee, azul gastado", required = true)
    @NotBlank
    @Column
    private String descripcion;

    @Schema(description = "Categoría de la prenda", example = "Pantalones", required = true)
    @NotBlank
    @Column
    private String categoria;

    @Schema(description = "Talla de la prenda", example = "40", required = true)
    @NotBlank
    @Column
    private int talla;

    @Schema(description = "Calificación de la prenda", example = "7")
    @Min(0)
    @Max(10)
    @Column
    private int puntos;

    @Schema(description = "Precio de la prenda", example = "15.75", required = true)
    @NotBlank
    @Column
    private float precio;

    @Schema(description = "Indica si la prenda es nueva", example = "true", required = true)
    @NotBlank
    @Column
    private boolean nueva;

    @Schema(description = "Fecha en la que se sube la prenda", example = "2021-04-01", required = true)
    @NotBlank
    @Column
    private LocalDate fechaSubida;

    @Schema(description = "Indica el identificador del usuario que sube la prenda", example = "1", required = true)
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario vendedor;

}
