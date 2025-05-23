package com.pixelpear.perfulandia.controller;

import java.util.List;

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

    @GetMapping("/status")
    public String mostrarStatus() {
        return "El ms esta activo";
    }

    /* Obtener todo el inventario */
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

    @PostMapping("/nuevoProducto")
    public void agregarProducto(@RequestParam String nombre, @RequestParam double precio, @RequestParam Integer stock) {
        Producto nuevoProducto = Producto.builder()
        .nombre(nombre)
        .precio(precio)
        .stock(stock)
        .build();
        productoService.agregarProducto(nuevoProducto);
    }

    /*@PostMapping("/nuevoProducto2")
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.crearProducto(producto.getNombre(), producto.getPrecio(), producto.getStock());
    }*/
    
    @DeleteMapping("/{idProducto}/borrarProducto")
    public void eliminarProducto(@PathVariable Long idProducto){
        productoService.eliminarProducto(idProducto);
    }
}