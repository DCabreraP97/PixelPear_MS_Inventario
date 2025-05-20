package com.pixelpear.perfulandia.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.pixelpear.perfulandia.model.Producto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Repository
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRepository {

        public List<Producto> obtenerProducto = new ArrayList<>(List.of(
        new Producto(1L, "Rosa Mística", 4990L, 10),
        new Producto(2L, "Luz de Luna", 5990L, 15),
        new Producto(3L, "Esencia del Alba", 6990L, 20),
        new Producto(4L, "Brisa Marina", 7990L, 25),
        new Producto(5L, "Jardín Secreto", 8990L, 30),
        new Producto(6L, "Fuego Nocturno", 9990L, 12),
        new Producto(7L, "Cielo Estrellado", 10990L, 18),
        new Producto(8L, "Aurora Boreal", 11990L, 22),
        new Producto(9L, "Oasis Tropical", 12990L, 16),
        new Producto(10L, "Sueño Dorado", 13990L, 14)
    ));



}
