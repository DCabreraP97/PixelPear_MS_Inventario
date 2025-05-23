package com.pixelpear.perfulandia.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/stockInventario")
    public List<Producto> obtenerStock() {
        return productoService.mostrarStock();
    }

    @GetMapping("/producto/{idProducto}")
    public Producto obtenerProductoPorId(@PathVariable Long idProducto) {
        return productoService.mostrarProductoPorId(idProducto);
    }
    
    @PutMapping("/reponerProducto")
    public Producto reponerStock(@RequestParam Long idProducto, @RequestParam int cantidad) {
        
        return productoService.reponerStock(idProducto, cantidad);
    }

    /*@PostMapping("/nuevoProducto")
    public void agregaProducto(@RequestParam Long idProducto, String nombre, double precio, Integer stock) {
        productoService.agregarProducto(new Producto(idProducto, nombre, precio, stock));
    }

    @PostMapping("/nuevoProducto")
    public Producto agregarProducto(@RequestBody Producto producto) {
        return productoService.crearProducto(producto.getNombre(), producto.getPrecio(), producto.getStock());
    }*/
    
    @DeleteMapping("/{idProducto}/borrarProducto")
    public void eliminarProducto(@PathVariable Long idProducto){
        productoService.eliminarProducto(idProducto);
    }
}