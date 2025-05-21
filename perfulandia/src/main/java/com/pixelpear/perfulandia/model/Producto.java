package com.pixelpear.perfulandia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @Column(name = "ID_PRODUCTO")
    private Long idProducto;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "STOCK")
    private Integer stock;
}