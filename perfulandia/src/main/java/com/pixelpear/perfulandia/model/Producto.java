package com.pixelpear.perfulandia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
    /*@GeneratedValue(strategy = GenerationType.IDENTITY) no resultó*/
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaProductos")
    @SequenceGenerator(name = "secuenciaProductos", sequenceName = "PRODUCTO_SEQ", allocationSize = 1)
    @Column(name = "IDPRODUCTO")    
    private Long idProducto;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "PRECIO")
    private double precio;

    @Column(name = "STOCK")
    private Integer stock;
}