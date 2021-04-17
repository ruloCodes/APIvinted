package com.rulo.vinted.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Pedido")
public class Pedido {

    @Schema(description = "Identificador del pedido", example = "1", required = true)
    @NotBlank
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Schema(description = "Título del pedido", example = "Pedido del usuario Felipe")
    @Column
    private String cabecera;

    @Schema(description = "Indica, si lo hubiera, el descuento en % a aplicar en el pedido", example = "5", required = true)
    @NotBlank
    @Column
    private int descuento;

    @Schema(description = "Precio total del pedido", example = "58.95", required = true)
    @NotBlank
    @Column
    private float total;

    @Schema(description = "Indica si el pedido está pagado", example = "true", required = true)
    @NotBlank
    @Column
    private boolean pagado;

    @Schema(description = "Fecha en la que se realiza el pedido", example = "2021-04-01", required = true)
    @NotBlank
    @Column
    private LocalDate fechaCompra;

    @Schema(description = "Detalles del pedido", example = "1 Pantalón vaquero 12.75", required = true)
    @NotBlank
    @OneToMany(mappedBy = "pedido")
    private List<Detalle> lineas;

}
