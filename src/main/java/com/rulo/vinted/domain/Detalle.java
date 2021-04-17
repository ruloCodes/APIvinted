package com.rulo.vinted.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Entity(name = "Detalle")
public class Detalle {

    @Schema(description = "Identificador del detalle", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Detalle de la línea", example = "Pantalón vaquero", required = true)
    @NotBlank
    @Column
    private String detalle;

    @Schema(description = "Cantidad de artículos en esta línea", example = "1", required = true)
    @NotBlank
    @Column
    private int cantidad;

    @Schema(description = "Precio de los artículos de la línea", example = "12.75", required = true)
    @NotBlank
    @Column
    private float subtotal;

    @Schema(description = "Indica si la línea ha sido añadida a un pedido", example = "true")
    @Column
    private boolean realizado;

    @Schema(description = "Fecha en la que se crea la línea", example = "2021-04-01", required = true)
    @NotBlank
    @Column
    private LocalDate fechaPedido;

    @Schema(description = "Identificador del pedido al que se añade la línea", example = "1")
    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;

    @Schema(description = "Identificador de la prenda de la línea", example = "1", required = true)
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "prenda_id")
    private Prenda prenda;

}
