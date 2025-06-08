package com.pixelpear.perfulandia.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pixelpear.perfulandia.model.Producto;
import com.pixelpear.perfulandia.service.ProductoService;

import lombok.RequiredArgsConstructor;





@RestController
@RequestMapping("/inventario")
@RequiredArgsConstructor

public class ProductoController {

    private final ProductoService productoService;

    /* Obtener todo el inventario*/
    @GetMapping("/stockInventario")
    public List<Producto> obtenerStock() {
        return productoService.mostrarStock();
    }

    /* Obtener producto por id */
    @GetMapping("/producto/{idProducto}")
    public Producto obtenerProductoPorId(@PathVariable Long idProducto) {
        return productoService.mostrarProductoPorId(idProducto);
    }
    
    /* Reponer stock */
    @PutMapping("/reponerProducto")
    public Producto reponerStock(@RequestParam Long idProducto, @RequestParam int cantidad) {
        
        return productoService.reponerStock(idProducto, cantidad);
    }

    /* Agregar nuevo producto */
    @PostMapping("/nuevoProducto")
    public ResponseEntity<String> agregarProducto(@RequestParam (required = false) String nombre, @RequestParam (required = false) Double precio, @RequestParam (required = false) Integer stock) {
        Producto nuevoProducto = Producto.builder()
        .nombre(nombre)
        .precio(precio)
        .stock(stock)
        .build();
        if (nombre.trim().isEmpty() || precio == null || stock == null) {
            return ResponseEntity.badRequest().body("El producto no fue agregado al inventario. Debe ingresar todos los datos.");
        } else {
            productoService.agregarProducto(nuevoProducto);
            return ResponseEntity.ok("El producto " + nuevoProducto.getNombre() + " ha sido agregado al inventario.");
        }
    }
    
    /* Eliminar producto de inventario */
    @DeleteMapping("/borrarProducto")
    public ResponseEntity<String> eliminarProducto(@RequestParam Long idProducto){
        productoService.eliminarProducto(idProducto);
        return ResponseEntity.ok("El producto con id: " + idProducto + " ha sido eliminado del inventario.");
    }
}