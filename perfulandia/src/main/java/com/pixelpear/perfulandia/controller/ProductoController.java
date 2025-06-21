package com.pixelpear.perfulandia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    /* Obtener producto por id LISTOOOOOOOO*/
    @GetMapping("/obtenerProducto")
    public ResponseEntity<String> obtenerProductoPorId(@RequestParam (required = false) Long idProducto) {
        if (idProducto == null || idProducto <= 0) {
            return ResponseEntity.badRequest().body("Debe ingresar un Id de producto mayor a 0.");
        } else {
            Optional<Producto> producto = productoService.mostrarProductoPorId(idProducto);
            if (producto.isPresent()) {
                Producto p = producto.get();
                String mensaje = "Producto encontrado: Id: " + p.getIdProducto() +
                                 ", Nombre: " + p.getNombre() +
                                 ", Precio: " + p.getPrecio() +
                                 ", Stock: " + p.getStock();
                return ResponseEntity.ok(mensaje);
            } else {
                return ResponseEntity.status(404).body("Producto no encontrado.");
            }
        }
    }

    /* Actualizar stock LISTOOOO*/
    @PutMapping("/actualizarStock")
    public ResponseEntity<String> actualizarStock(@RequestParam (required = false) Long idProducto, @RequestParam (required = false) int cantidad) {
        if (idProducto == null || idProducto <= 0) {
            return ResponseEntity.badRequest().body("No se ha actualizado el stock del producto, debe ingresar la Id y debe ser mayor a 0.");
        } else if (cantidad == 0){
            return ResponseEntity.badRequest().body("No se ha actualizado el stock del producto, debe ingresar una cantidad mayor a 0.");
        }
        Producto productoActualizado = productoService.actualizarStock(idProducto, cantidad);
        return ResponseEntity.ok("El stock del producto " + productoActualizado.getNombre() + " ha sido actualizado a " + productoActualizado.getStock() + " unidades.");
    }
    

    /* Agregar nuevo producto LISTOOOOOOOO*/
    @PostMapping("/nuevoProducto")
    public ResponseEntity<String> agregarProducto(@RequestParam (required = false) String nombre, @RequestParam (required = false) Double precio, @RequestParam (required = false) Integer stock) {
        Producto nuevoProducto = Producto.builder()
        .nombre(nombre)
        .precio(precio)
        .stock(stock)
        .build();
        if (nombre.trim().isEmpty() || precio == null || stock == null) {
            return ResponseEntity.badRequest().body("El producto no fue agregado al inventario. Debe ingresar todos los datos.");
        } else if (precio <= 0 || stock <= 0) {
            return ResponseEntity.badRequest().body("El producto no fue agregado al inventario. El precio y el stock deben ser mayor a 0.");
            } else {
                productoService.agregarProducto(nuevoProducto);
                return ResponseEntity.ok("El producto " + nuevoProducto.getNombre() + " ha sido agregado al inventario.");  
            }            
        }
    
    
    /* Eliminar producto de inventario LISTOOO*/
    @DeleteMapping("/borrarProducto")
    public ResponseEntity<String> eliminarProducto(@RequestParam(required = false) Long idProducto){
        if (idProducto == null) {
            return ResponseEntity.badRequest().body("El producto no ha sido eliminado, debe ingresar el Id.");
        } else if (idProducto <= 0) {
            return ResponseEntity.badRequest().body("El producto no ha sido eliminado ya que el Id debe ser mayor a 0.");
            } else {
                productoService.eliminarProducto(idProducto);
                return ResponseEntity.ok("El producto con id: " + idProducto + " ha sido eliminado del inventario.");
            }
    }
}
